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

package io.github.darthakiranihil.konna.level.layer;

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
public final class KLevel extends KObject {

    private final Map<String, KLevelSector> sectors;
    private final String[] sectorNames;
    private final KIntWeightedGraph<String> sectorConnectivityGraph;

    /**
     * Standard constructor.
     * @param name Name of the level
     * @param sectors Sectors that are parts of the level
     */
    public KLevel(
        final String name,
        final List<KLevelSector> sectors
    ) {
        super(name, KStructUtils.setOfTags(KLevelComponentTags.LEVEL));
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
     * @return Array of names of sectors, included in this level
     */
    public String[] getSectorNames() {
        return this.sectorNames;
    }

    /**
     * @param sectorName Name of the sector
     * @return The sector with specified name
     * @throws KInvalidArgumentException if the sector does not exist in this level
     */
    public KLevelSector getSector(final String sectorName) {
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
     * @param sectorName Sector name to check containment for
     * @return Whether the sector with specific name is presented in this level
     */
    public boolean hasSector(final String sectorName) {
        return this.sectors.containsKey(sectorName);
    }

    /**
     * Unloads this level.
     */
    public void unload() {
        this.sectors.values().forEach(KLevelSector::unload);
    }

    /**
     * @return Sector connectivity graph for this level
     */
    public KIntWeightedGraph<String> getSectorConnectivityGraph() {
        return this.sectorConnectivityGraph;
    }

    private KIntWeightedGraph<String> buildSectorConnectivityGraph() {

        KIntWeightedGraph<String> graph = new KHashMapIntWeightedGraph<>();

        for (var entry: this.sectors.entrySet()) {

            String sourceSectorName = entry.getKey();
            graph.add(sourceSectorName);
            KLevelSector sector = entry.getValue();

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
