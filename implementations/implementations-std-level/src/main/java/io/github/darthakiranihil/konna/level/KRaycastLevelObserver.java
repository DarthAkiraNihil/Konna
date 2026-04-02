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

package io.github.darthakiranihil.konna.level;

import io.github.darthakiranihil.konna.core.struct.KPair;
import io.github.darthakiranihil.konna.core.struct.KTriplet;
import io.github.darthakiranihil.konna.core.struct.KVector2f;
import io.github.darthakiranihil.konna.core.struct.KVector2i;

import java.util.*;

/**
 * Implementation of {@link KLevelObserver} that uses raycasting to calculate
 * a field of view.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public final class KRaycastLevelObserver implements KLevelObserver {

    private static final int FOV_CAST_RAYS_COUNT = 360;

    @Override
    public KFov observePoint(
        final KLevel location,
        final String sector,
        int x,
        int y,
        int visionRange
    ) {

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
                KLevelSector observedSector = location.getSector(data.first());
                int vision = data.second();

                Integer previousHeight = null;

                float observedX = data.third().x();
                float observedY = data.third().y();

                while (vision > 0) {
                    KLevelSectorSlice slice = observedSector.getSliceAndVisit(
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
                    return location
                        .getSector(o.first())
                        .getSlice(position.x(), position.y());
                })
                .filter(s -> s.tile() != null)
                .sorted(Comparator.comparing(KLevelSectorSlice::sectorName))
                .toList()
        );
    }
}
