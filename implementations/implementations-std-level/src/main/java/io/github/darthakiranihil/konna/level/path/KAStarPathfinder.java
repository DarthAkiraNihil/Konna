/*
 * Copyright 2025-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.darthakiranihil.konna.level.path;

import io.github.darthakiranihil.konna.core.struct.KPair;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KTriplet;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.core.struct.graph.KIntWeightedGraph;
import io.github.darthakiranihil.konna.level.KTileInfo;
import io.github.darthakiranihil.konna.level.KLevel;
import io.github.darthakiranihil.konna.level.KLevelSector;
import io.github.darthakiranihil.konna.level.KLevelSectorSlice;
import io.github.darthakiranihil.konna.level.layer.tool.KReachabilityAreaLayerTool;
import io.github.darthakiranihil.konna.level.layer.tool.KSectorLinkLayerTool;
import org.jspecify.annotations.Nullable;

import java.util.*;

/**
 * <p>
 *     Implementation of {@link KPathfinder} that uses A* (AStar) pathfinding algorithm
 *     to find paths inside same sectors.
 * </p>
 * <p>
 *     When it is required to find path between different
 *     sectors, it firstly tries to find a sector path from source to destination sector,
 *     and if it is present then calculates list of checkpoints. The list includes source
 *     position, destination position and required sector links that lead to sectors,
 *     presented in the sector path.
 * </p>
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public class KAStarPathfinder implements KPathfinder {

    /**
     * Enumeration consisting of possible heuristics used in A* algorithm to
     * calculate path cost to the goal.
     *
     * @since 0.5.0
     * @author Darth Akira Nihil
     */
    public enum Heuristic {

        /**
         * Uses Euclidean distance as heuristic.
         */
        EUCLIDEAN {
            @Override
            double costDelta(int srcX, int srcY, int dstX, int dstY) {
                return this.distance(srcX, srcY, dstX, dstY);
            }

            @Override
            double distance(int srcX, int srcY, int dstX, int dstY) {
                return Math.sqrt(
                    Math.pow(srcX - dstX, 2) + Math.pow(srcY - dstY, 2)
                );
            }

        },
        /**
         * Uses Manhattan distance as heuristic.
         */
        MANHATTAN {
            @Override
            double costDelta(int srcX, int srcY, int dstX, int dstY) {
                return 1;
            }

            @Override
            double distance(int srcX, int srcY, int dstX, int dstY) {
                return Math.abs(srcX - dstX) + Math.abs(dstX - dstY);
            }

        },
        /**
         * Uses Chebyshev distance as heuristic.
         */
        CHEBYSHEV {
            @Override
            double costDelta(
                int srcX,
                int srcY,
                int dstX,
                int dstY
            ) {
                return 1;
            }

            @Override
            double distance(int srcX, int srcY, int dstX, int dstY) {
                return Math.max(Math.abs(srcX - dstX), Math.abs(srcY - dstY));
            }
        },
        /**
         * Uses Chebyshev distance as heuristic, but costs for diagonal and straight paths
         * are different (1 for diagonal, 1 / sqrt(2) for straight).
         */
        NATURALIZED_CHEBYSHEV {
            @Override
            double costDelta(
                int srcX,
                int srcY,
                int dstX,
                int dstY
            ) {
                if (Math.abs(srcX - dstX) == 1 && Math.abs(srcY - dstY) == 1) {
                    return 1 / Math.sqrt(2);
                }

                return 1;
            }

            @Override
            double distance(int srcX, int srcY, int dstX, int dstY) {
                return Math.max(Math.abs(srcX - dstX), Math.abs(srcY - dstY));
            }
        };

        abstract double costDelta(
            int srcX,
            int srcY,
            int dstX,
            int dstY
        );
        abstract double distance(
            int srcX,
            int srcY,
            int dstX,
            int dstY
        );

    }

    private static final class AStarNode {

        private double w;
        private final KVector2i position;
        private @Nullable AStarNode previous;

        AStarNode(final KVector2i position) {
            this.position = position;
            this.w = Double.MAX_VALUE;
        }

    }

    private final Heuristic heuristic;

    public KAStarPathfinder() {
        this(Heuristic.NATURALIZED_CHEBYSHEV);
    }

    public KAStarPathfinder(final Heuristic heuristic) {
        this.heuristic = heuristic;
    }

    @Override
    public KPath findPath(
        final KLevel location,
        final String srcSector,
        int srcX,
        int srcY,
        final String dstSector,
        int dstX,
        int dstY
    ) {

        KIntWeightedGraph<String> connectivityGraph = location.getSectorConnectivityGraph();
        if (!connectivityGraph.has(srcSector) || !connectivityGraph.has(dstSector)) {
            return KPath.EMPTY;
        }

        List<String> sectorPath = this.getSectorPath(
            location,
            srcSector,
            srcX,
            srcY,
            dstSector,
            dstX,
            dstY,
            connectivityGraph
        );

        // cases: 1 - we are in the right place,
        // more than one - we need to get checkpoint, 0 - no path
        if (sectorPath.isEmpty()) {
            return KPath.EMPTY;
        }

        if (sectorPath.size() == 1 && sectorPath.getFirst().equals(dstSector)) {
            // on this stage source and destination are guaranteed to be connected
            KLevelSector dst = location.getSector(dstSector);
            Queue<KVector2i> path = this.getPath(dst, srcX, srcY, dstX, dstY);
            return new KPath(path);
        }

        var checkpoints = this.getCheckpoints(location, sectorPath, srcX, srcY, dstX, dstY);
        Queue<KVector2i> finalPath = new LinkedList<>();

        for (int i = 0; i < checkpoints.size(); i++) {
            KLevelSector dst = location.getSector(sectorPath.get(i));
            var checkpoint = checkpoints.get(i);
            var checkpointPath = this.getPath(
                dst,
                checkpoint.first().x(),
                checkpoint.first().y(),
                checkpoint.second().x(),
                checkpoint.second().y()
            );

            if (checkpointPath.isEmpty()) {
                return KPath.EMPTY;
            }

            finalPath.addAll(checkpointPath);
        }

        return new KPath(finalPath);

    }

    private List<String> getSectorPath(
        final KLevel location,
        final String srcSector,
        int srcX,
        int srcY,
        final String dstSector,
        int dstX,
        int dstY,
        final KIntWeightedGraph<String> sectorConnectivityGraph
    ) {

        if (srcSector.equals(dstSector)) {
            KLevelSector dst = location.getSector(dstSector);
            var tool = dst.getTool(KReachabilityAreaLayerTool.class);
            if (tool.isReachable(srcX, srcY, dstX, dstY)) {
                return List.of(dstSector);
            }

            return sectorConnectivityGraph.getPath(srcSector, dstSector, true);
        }

        return sectorConnectivityGraph.getPath(srcSector, dstSector);
    }



    private Queue<KVector2i> getPath(
        final KLevelSector sector,
        int srcX,
        int srcY,
        int dstX,
        int dstY
    ) {

        KSize sectorSize = sector.getSize();
        AStarNode[][] pathGraph = new AStarNode[sectorSize.height()][sectorSize.width()];
        for (int i = 0; i < sectorSize.height(); i++) {
            for (int j = 0; j < sectorSize.width(); j++) {
                pathGraph[i][j] = new AStarNode(new KVector2i(j, i));
            }
        }
        pathGraph[srcY][srcX].w = 0;

        Queue<KVector2i> reachable = new LinkedList<>();
        Set<KVector2i> explored = new HashSet<>();

        reachable.add(new KVector2i(srcX, srcY));

        while (!reachable.isEmpty()) {
            KVector2i node = this.chooseNode(reachable, pathGraph, dstX, dstY);

            if (node.x() == dstX & node.y() == dstY) {
                return this.buildPath(pathGraph, dstX, dstY);
            }

            reachable.remove(node);
            explored.add(node);

            Set<KVector2i> newReachable = this.getAdjacentNodes(
                sector,
                node.x(),
                node.y(),
                explored
            );

            for (KVector2i adjacent: newReachable) {

                if (!reachable.contains(adjacent)) {
                    reachable.add(adjacent);
                }

                AStarNode adjAStarNode = pathGraph[adjacent.y()][adjacent.x()];
                AStarNode aStarNode = pathGraph[node.y()][node.x()];
                double costDelta = this.heuristic.costDelta(
                    node.x(),
                    node.y(),
                    adjacent.x(),
                    adjacent.y()
                );
                if (aStarNode.w + costDelta < adjAStarNode.w) {
                    adjAStarNode.previous = aStarNode;
                    adjAStarNode.w = aStarNode.w + costDelta;
                }
            }

        }

        return new LinkedList<>();

    }

    private KVector2i chooseNode(
        final Queue<KVector2i> reachable,
        final AStarNode[][] pathGraph,
        int dstX,
        int dstY
    ) {

        double minCost = Double.MAX_VALUE;
        KVector2i bestNode = null;
        for (var node: reachable) {
            int nodeX = node.x();
            int nodeY = node.y();

            double costStartToNode = pathGraph[node.y()][node.x()].w;
            double costToDestination = this.heuristic.distance(nodeX, nodeY, dstX, dstY);
            double totalCost = costStartToNode + costToDestination;
            if (minCost > totalCost) {
                minCost = totalCost;
                bestNode = node;
            }
        }

        return Objects.requireNonNull(bestNode);

    }

    private Queue<KVector2i> buildPath(
        final AStarNode[][] pathGraph,
        int dstX,
        int dstY
    ) {

        List<KVector2i> path = new LinkedList<>();
        AStarNode goal = pathGraph[dstY][dstX];
        while (goal.previous != null) {
            KVector2i prevPosition = goal.previous.position;
            path.addFirst(
                new KVector2i(
                    goal.position.x() - prevPosition.x(),
                    goal.position.y() - prevPosition.y()
                )
            );
            goal = goal.previous;
        }

        return new ArrayDeque<>(path);
    }

    private Set<KVector2i> getAdjacentNodes(
        final KLevelSector sector,
        int x,
        int y,
        final Set<KVector2i> explored
    ) {

        Set<KVector2i> adjacent = new HashSet<>();

        KLevelSectorSlice srcSlice = sector.getSlice(x, y);

        this.testAdjacentNode(srcSlice, sector, x + 1, y - 1, adjacent, explored);
        this.testAdjacentNode(srcSlice, sector, x + 1, y + 1, adjacent, explored);
        this.testAdjacentNode(srcSlice, sector, x + 1, y, adjacent, explored);

        this.testAdjacentNode(srcSlice, sector, x, y + 1, adjacent, explored);
        this.testAdjacentNode(srcSlice, sector, x, y - 1, adjacent, explored);

        this.testAdjacentNode(srcSlice, sector, x - 1, y, adjacent, explored);
        this.testAdjacentNode(srcSlice, sector, x - 1, y - 1, adjacent, explored);
        this.testAdjacentNode(srcSlice, sector, x - 1, y + 1, adjacent, explored);

        return adjacent;

    }

    private void testAdjacentNode(
        final KLevelSectorSlice slice,
        final KLevelSector sector,
        int adjacentX,
        int adjacentY,
        final Set<KVector2i> adjacent,
        final Set<KVector2i> explored
    ) {

        KLevelSectorSlice adjacentSlice = sector.getSlice(adjacentX, adjacentY);
        KTileInfo adjacentTileInfo = adjacentSlice.tile();

        if (
                adjacentTileInfo != null
            &&  adjacentTileInfo.isPassable()
            &&  Math.abs(slice.height() - adjacentSlice.height()) <= 1
            &&  !explored.contains(adjacentSlice.position())) {

            adjacent.add(adjacentSlice.position());

        }

    }

    private List<KPair<KVector2i, KVector2i>> getCheckpoints(
        final KLevel location,
        final List<String> sectorPath,
        int srcX,
        int srcY,
        int dstX,
        int dstY
    ) {

        Random rnd = new Random();
        List<KTriplet<KVector2i, KVector2i, KVector2i>> checkpoints = new LinkedList<>();

        for (int i = 0; i < sectorPath.size() - 2; i++) {

            String firstSectorName = sectorPath.get(i);
            String secondSectorName = sectorPath.get(i + 1);
            String thirdSectorName = sectorPath.get(i + 2);

            KLevelSector first = location.getSector(firstSectorName);
            KLevelSector second = location.getSector(secondSectorName);

            var firstLinkTool = first.getTool(KSectorLinkLayerTool.class);
            var secondLinkTool = second.getTool(KSectorLinkLayerTool.class);

            KVector2i source = i == 0 ? new KVector2i(srcX, srcY) : checkpoints.getLast().third();

            var linksFromFirstToSecond = firstLinkTool.getToSector(secondSectorName);
            var linksFromSecondToThird = secondLinkTool.getToSector(thirdSectorName);

            List<KVector2i> goodLinks = new ArrayList<>(linksFromFirstToSecond.size());

            for (var linkFromFirstToSecond: linksFromFirstToSecond.entrySet()) {
                if (
                    linksFromSecondToThird
                        .keySet()
                        .stream()
                        .noneMatch(
                            x -> second
                                .getTool(KReachabilityAreaLayerTool.class)
                                .isReachable(
                                linkFromFirstToSecond.getValue().destination(),
                                x
                            )
                        )
                ) {
                    continue;
                }

                goodLinks.add(linkFromFirstToSecond.getKey());
            }

            if (goodLinks.isEmpty()) {
                return List.of();
            }

            var chosenLink = goodLinks.get(rnd.nextInt(goodLinks.size()));
            checkpoints.add(
                new KTriplet<>(
                    source,
                    chosenLink,
                    linksFromFirstToSecond.get(chosenLink).destination()
                )
            );

            // edge case n-1 - n - dst, n - dst

        }


        String penultimateSectorName = sectorPath.get(sectorPath.size() - 2);
        String lastSectorName = sectorPath.getLast();

        KLevelSector penultimate = location.getSector(penultimateSectorName);
        KLevelSector last = location.getSector(lastSectorName);

        KVector2i source = checkpoints.isEmpty()
            ? new KVector2i(srcX, srcY)
            : checkpoints.getLast().third();

        var penultimateLinkTool = penultimate.getTool(KSectorLinkLayerTool.class);
        var linksFromPenultimateToLast = penultimateLinkTool.getToSector(lastSectorName);

        List<KVector2i> goodLinks = new ArrayList<>(linksFromPenultimateToLast.size());
        KVector2i destination = new KVector2i(dstX, dstY);

        for (var linkFromPenultimateToLast: linksFromPenultimateToLast.entrySet()) {
            var lastReachabilityTool = last.getTool(KReachabilityAreaLayerTool.class);
            if (
                !lastReachabilityTool.isReachable(
                    linkFromPenultimateToLast.getValue().destination(),
                    destination
                )
            ) {
                continue;
            }

            goodLinks.add(linkFromPenultimateToLast.getKey());
        }

        if (goodLinks.isEmpty()) {
            return List.of();
        }

        var chosenLink = goodLinks.get(rnd.nextInt(goodLinks.size()));
        checkpoints.add(
            new KTriplet<>(
                source,
                chosenLink,
                linksFromPenultimateToLast.get(chosenLink).destination()
            )
        );

        checkpoints.add(
            new KTriplet<>(
                checkpoints.getLast().third(),
                destination,
                KVector2i.ZERO
            )
        );

        return checkpoints
            .stream()
            .map(x -> new KPair<>(x.first(), x.second()))
            .toList();

    }

}
