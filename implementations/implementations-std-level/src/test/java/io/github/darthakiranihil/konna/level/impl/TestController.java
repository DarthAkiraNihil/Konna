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

package io.github.darthakiranihil.konna.level.impl;

import io.github.darthakiranihil.konna.core.io.KAssetDefinition;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.entity.KAutonomousEntityController;
import io.github.darthakiranihil.konna.level.entity.KAutonomousEntityControllerParam;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class TestController extends KAutonomousEntityController {

    private int test;

    @KAutonomousEntityControllerParam(name = "test", type = KAutonomousEntityControllerParam.Metatype.INT)
    public TestController(String qualifier, KAssetDefinition params) {
        super(qualifier, params);
    }

    @Override
    protected void applyParams(KAssetDefinition params) {
        this.test = params.getInt("test");
    }

    @Override
    public KVector2i getNextMoveDirection() {
        return new KVector2i(1, 0);
    }

    public int getTest() {
        return this.test;
    }
}
