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

package io.github.darthakiranihil.konna.graphics.type;

import io.github.darthakiranihil.konna.core.io.KAssetDefinitionRule;
import io.github.darthakiranihil.konna.core.io.KAssetTypedef;
import io.github.darthakiranihil.konna.core.io.KCompositeAssetDefinitionRuleBuilder;
import io.github.darthakiranihil.konna.core.io.except.KAssetDefinitionError;

public final class KShaderProgramTypeDefinition implements KAssetTypedef {

    @Override
    public String getName() {
        return "Graphics.shaderProgram";
    }

    @Override
    public KAssetDefinitionRule getRule() {
        return KCompositeAssetDefinitionRuleBuilder
            .create()
            .withString("vertex")
            .withString("fragment")
            .withRule((value) -> {
                String vertex = value.getString("vertex");
                String fragment = value.getString("fragment");

                if (vertex == null && fragment == null) {
                    throw new KAssetDefinitionError(
                        "vertex and fragment shaders cannot be both null!"
                    );
                }
            })
            .build();
    }
}
