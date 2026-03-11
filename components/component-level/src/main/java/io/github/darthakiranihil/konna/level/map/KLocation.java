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

package io.github.darthakiranihil.konna.level.map;

import io.github.darthakiranihil.konna.core.except.KInvalidArgumentException;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.struct.*;
import io.github.darthakiranihil.konna.core.struct.graph.KHashMapIntWeightedGraph;
import io.github.darthakiranihil.konna.core.struct.graph.KIntWeightedGraph;
import io.github.darthakiranihil.konna.level.KLevelComponentTags;

import java.util.*;

/**
 * Representation of a game level, consisting of map sectors,
 * whose list is immutable.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public final class KLocation extends KObject {

    private static final int FOV_CAST_RAYS_COUNT = 360;


    private final Map<String, KMapSector> sectors;
    private final String[] sectorNames;
    private final KIntWeightedGraph<String> sectorConnectivityGraph;

    /**
     * Standard constructor.
     * @param name Name of the location
     * @param sectors Sectors that are parts of the location
     */
    public KLocation(
        final String name,
        final List<KMapSector> sectors
    ) {
        super(name, KStructUtils.setOfTags(KLevelComponentTags.LOCATION));
        this.sectors = new HashMap<>(sectors.size());

        sectors.forEach(
            s -> {
                this.sectors.put(s.name(), s);
            }
        );

        this.sectorNames = this.sectors.keySet().toArray(new String[0]);
        this.sectorConnectivityGraph = this.buildSectorConnectivityGraph();
    }

    /**
     * @return Array of names of sectors, included in this location
     */
    public String[] getSectorNames() {
        return this.sectorNames;
    }

    /**
     * @param sectorName Name of the sector
     * @return The sector with specified name
     * @throws KInvalidArgumentException if the sector does not exist in this location
     */
    public KMapSector getSector(final String sectorName) {
        if (!this.sectors.containsKey(sectorName)) {
            throw new KInvalidArgumentException(
                String.format(
                    "Sector not found: %s",
                    sectorName
                )
            );
        }

        return this.sectors.get(sectorName);
    }

    /**
     * Unloads this location.
     */
    public void unload() {
        this.sectors.values().forEach(KMapSector::unload);
    }

    /**
     * Observes a point in this location.
     * @param sector Sector to begin observing in
     * @param point Position to begin observing on
     * @param visionRange How far does it need to observe
     * @return Field of view for this position
     */
    public KFov observePoint(final String sector, final KVector2i point, int visionRange) {
        return this.observePoint(sector, point.x(), point.y(), visionRange);
    }

    /**
     * Observes a point in this location.
     * @param sector Sector to begin observing in
     * @param x X coordinate of position to begin observing on
     * @param y Y coordinate of position to begin observing on
     * @param visionRange How far does it need to observe
     * @return Field of view for this position
     */
    public KFov observePoint(final String sector, int x, int y, int visionRange) {

        Set<KPair<String, KVector2i>> observed = new HashSet<>();

        for (int i = 0; i < FOV_CAST_RAYS_COUNT; i++) {

            float xSh = (float) Math.cos(Math.toRadians(i));
            float ySh = (float) Math.sin(Math.toRadians(i));

            Deque<KTriplet<String, Integer, KVector2f>> observingQueue = new LinkedList<>();
            observingQueue.add(
                new KTriplet<>(
                    sector,
                    visionRange * 2,
                    new KVector2f(x + 0.5f, y + 0.5f)
                )
            );

            Set<String> seenSectors = new HashSet<>();
            while (!observingQueue.isEmpty()) {

                var data = observingQueue.pop();
                KMapSector observedSector = this.getSector(data.first());
                int vision = data.second();

                Integer previousHeight = null;

                float observedX = data.third().x();
                float observedY = data.third().y();

                while (vision > 0) {
                    KMapSectorSlice slice = observedSector.getSliceAndVisit(
                        (int) observedX, (int) observedY
                    );

                    if (slice.tile() != null) {
                        vision -= slice.tile().getOpaqueness() * 2;
                    }

                    int currentHeight = slice.height();
                    if (previousHeight != null) {
                        if (currentHeight > previousHeight) {
                            vision -= (currentHeight - previousHeight);
                        } else if (currentHeight < previousHeight) {
                            vision++;
                        }
                    }
                    previousHeight = currentHeight;

                    observed.add(new KPair<>(observedSector.name(), slice.position()));
                    if (
                            slice.sectorLink() != null
                        &&  !seenSectors.contains(slice.sectorLink().linkedSector().name())
                    ) {
                        var linkData = slice.sectorLink();
                        observingQueue.add(
                            new KTriplet<>(
                                linkData.linkedSector().name(),
                                vision,
                                new KVector2f(
                                    linkData.destination().x(),
                                    linkData.destination().y()
                                )
                            )
                        );
                    }

                    vision -= 2;
                    observedX += xSh;
                    observedY += ySh;
                }

                seenSectors.add(data.first());

            }
        }

        return new KFov(
            observed
                .stream()
                .map(o -> {
                    var position = o.second();
                    return this
                        .getSector(o.first())
                        .getSlice(position.x(), position.y());
                })
                .filter(s -> s.tile() != null)
                .sorted(Comparator.comparing(KMapSectorSlice::sectorName))
                .toList()
        );
    }

    private KIntWeightedGraph<String> buildSectorConnectivityGraph() {

        KIntWeightedGraph<String> graph = new KHashMapIntWeightedGraph<>();

        for (var entry: this.sectors.entrySet()) {

            String sourceSectorName = entry.getKey();
            graph.add(sourceSectorName);
            KMapSector sector = entry.getValue();

            KSize sectorSize = sector.getSize();
            for (int i = 0; i < sectorSize.width(); i++) {
                for (int j = 0; j < sectorSize.height(); j++) {

                    KMapSectorSlice slice = sector.getSlice(i, j);
                    if (slice.sectorLink() == null) {
                        continue;
                    }

                    String linkedSectorName = slice.sectorLink().linkedSector().name();
                    if (!graph.has(linkedSectorName)) {
                        graph.add(linkedSectorName);
                    }

                    graph.connect(sourceSectorName, linkedSectorName, 1);

                }
            }

        }

        return graph;

    }


}
