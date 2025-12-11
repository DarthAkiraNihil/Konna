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

package io.github.darthakiranihil.konna.core.object.impl;

import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KOnPoolableObjectObtain;
import io.github.darthakiranihil.konna.core.object.KOnPoolableObjectRelease;
import io.github.darthakiranihil.konna.core.object.KPoolable;

@KPoolable(initialPoolSize = 2)
public class TestPoolable extends KObject {
    private int field;

    @KOnPoolableObjectObtain
    private void create(int fieldValue) {
        this.field = fieldValue;
    }

    @KOnPoolableObjectRelease
    private void delete() {
        this.field = -1;
    }

    public int getField() {
        return this.field;
    }
}
