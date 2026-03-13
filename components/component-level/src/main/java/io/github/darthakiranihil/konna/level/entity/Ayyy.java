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

package io.github.darthakiranihil.konna.level.entity;

import io.github.darthakiranihil.konna.core.io.KAssetDefinition;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.map.KLocation;

public class Ayyy extends KAutonomousEntityController {

    @KAutonomousEntityControllerParam(name = "p1", type = KAutonomousEntityControllerParam.Metatype.INT)
    @KAutonomousEntityControllerParam(name = "p2", type = KAutonomousEntityControllerParam.Metatype.FLOAT)
    @KAutonomousEntityControllerParam(name = "p3", type = KAutonomousEntityControllerParam.Metatype.BOOLEAN)
    @KAutonomousEntityControllerParam(name = "p4", type = KAutonomousEntityControllerParam.Metatype.STRING)
    @KAutonomousEntityControllerParam(name = "p5", type = KAutonomousEntityControllerParam.Metatype.SUBDEF)
    @KAutonomousEntityControllerParam(name = "p6", type = KAutonomousEntityControllerParam.Metatype.ENUM, qualifier = "io.github.darthakiranihil.konna.core.object.KObjectInstantiationType")
    @KAutonomousEntityControllerParam(name = "p7", type = KAutonomousEntityControllerParam.Metatype.CLASS)
    @KAutonomousEntityControllerParam(name = "p8", type = KAutonomousEntityControllerParam.Metatype.CLASS_THAT_EXTENDS, qualifier = "io.github.darthakiranihil.konna.level.entity.KAutonomousEntityController")
    @KAutonomousEntityControllerParam(name = "p9", type = KAutonomousEntityControllerParam.Metatype.INT_ARRAY)
    @KAutonomousEntityControllerParam(name = "p10", type = KAutonomousEntityControllerParam.Metatype.FLOAT_ARRAY)
    @KAutonomousEntityControllerParam(name = "p11", type = KAutonomousEntityControllerParam.Metatype.BOOLEAN_ARRAY)
    @KAutonomousEntityControllerParam(name = "p12", type = KAutonomousEntityControllerParam.Metatype.STRING_ARRAY)
    @KAutonomousEntityControllerParam(name = "p13", type = KAutonomousEntityControllerParam.Metatype.SUBDEF_ARRAY)
    @KAutonomousEntityControllerParam(name = "p14", type = KAutonomousEntityControllerParam.Metatype.CLASS_ARRAY)
    @KAutonomousEntityControllerParam(name = "p15", type = KAutonomousEntityControllerParam.Metatype.CLASS_THAT_EXTENDS_ARRAY, qualifier = "io.github.darthakiranihil.konna.level.entity.KAutonomousEntityController")
    public Ayyy(KMapEntity assignedEntity, KLocation location, KAssetDefinition params) {
        super(assignedEntity, location, params);
    }

    @Override
    protected void applyParams(KAssetDefinition params) {

    }

    @Override
    public KVector2i getNextMoveDirection() {
        return null;
    }
}
