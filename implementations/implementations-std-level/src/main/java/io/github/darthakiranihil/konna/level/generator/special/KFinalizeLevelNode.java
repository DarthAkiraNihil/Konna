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

package io.github.darthakiranihil.konna.level.generator.special;

import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.level.KLevel;
import io.github.darthakiranihil.konna.level.KLevelSector;
import io.github.darthakiranihil.konna.level.entity.KAutonomousEntity;
import io.github.darthakiranihil.konna.level.entity.KAutonomousEntityController;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNode;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeInputParam;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeOutputParam;
import io.github.darthakiranihil.konna.level.layer.tool.KLevelEntityLayerTool;

import java.util.List;
import java.util.Random;

/**
 * <p>
 *     Generator node that finalizes passed level, making it valid for real use.
 *     Should be used in the end if there is no node that does the same job.
 * <h3>Inputs:</h3>
 * <ul>
 *     <li>{@code level} - {@link KLevel} - level to finalize</li>
 * </ul>
 * <h3>Outputs:</h3>
 * <ul>
 *     <li>{@code level} - {@link KLevel} - finalized level</li>
 * </ul>
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public final class KFinalizeLevelNode implements KGeneratorNode {

    @Override
    @KGeneratorNodeInputParam(name = "level", type = KLevel.class)
    @KGeneratorNodeOutputParam(name = "level", type = KLevel.class)
    public KUniversalMap process(final KUniversalMap params, final Random rnd) {

        KLevel level = params.get("level", KLevel.class);

        String[] sectorNames = level.getSectorNames();
        for (String sectorName: sectorNames) {
            KLevelSector sector = level.getSector(sectorName);


            KLevelEntityLayerTool tool = sector.getTool(KLevelEntityLayerTool.class);
            List<KAutonomousEntity> autos = tool
                .getAllContainedEntities()
                .stream()
                .filter(x -> x instanceof KAutonomousEntity)
                .map(x -> (KAutonomousEntity) x)
                .toList();

            for (var auto: autos) {
                KAutonomousEntityController controller = auto.getController();
                if (controller == null) {
                    continue;
                }

                controller.setLevel(level);
                controller.setAssignedEntity(auto);
            }
        }

        return params;
    }
}
