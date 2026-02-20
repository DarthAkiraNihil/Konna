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

package io.github.darthakiranihil.konna.core.app;

import io.github.darthakiranihil.konna.core.data.json.KJsonObjectValidatorBuilder;
import io.github.darthakiranihil.konna.core.data.json.KJsonSerialized;
import io.github.darthakiranihil.konna.core.data.json.KJsonValidator;
import io.github.darthakiranihil.konna.core.data.json.KJsonValueType;
import io.github.darthakiranihil.konna.core.struct.KSize;

/**
 * Container for frame spawn options that are supposed to be used on its loading.
 * @param size Frame size
 * @param title Frame title (that is usually window's title)
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
public record KFrameSpawnOptions(
    @KJsonSerialized
    KSize size,
    @KJsonSerialized
    String title
) {

    private static final String SIZE_KEY = "size";
    private static final String TITLE_KEY = "title";

    /**
     * @return JSON schema of frame spawn options.
     */
    public static KJsonValidator getSchema() {
        return KJsonObjectValidatorBuilder
            .create()
            .withSimpleField(SIZE_KEY, KJsonValueType.OBJECT)
            .withSimpleField(TITLE_KEY, KJsonValueType.STRING)
            .build();
    }
}
