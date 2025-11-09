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

package io.github.darthakiranihil.konna.core.object;

import io.github.darthakiranihil.konna.core.struct.KStructUtils;

public abstract class KWrapper<O, W> extends KObject {

    private final W wrapped;

    public KWrapper(
        final O original,
        final Class<O> origialClass,
        final Class<W> wrappedClass
    ) {
        super(
            String.format(
                "wrapper_%s_to_%s",
                origialClass.getCanonicalName(),
                wrappedClass.getCanonicalName()
            ),
            KStructUtils.setOfTags(KTag.DefaultTags.WRAPPER)
        );
        if (original == null) {
            this.wrapped = null;
        } else {
            this.wrapped = this.wrap(original);
        }
    }

    protected abstract W wrap(final O original);

    public W wrapped() {
        return this.wrapped;
    }

}
