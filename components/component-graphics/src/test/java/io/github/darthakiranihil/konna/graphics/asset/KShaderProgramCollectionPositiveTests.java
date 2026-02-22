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

package io.github.darthakiranihil.konna.graphics.asset;

import io.github.darthakiranihil.konna.test.KStandardTestClass;
import io.github.darthakiranihil.konna.graphics.impl.TestShaderCompiler;
import io.github.darthakiranihil.konna.graphics.shader.KShaderProgram;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KShaderProgramCollectionPositiveTests extends KAssetCollectionTestClass {

    @Test
    public void testGetShaderProgramSuccess() {

        KShaderProgramCollection spc = new KShaderProgramCollection(
            this.assetLoader,
            new KShaderCollection(
                this.assetLoader,
                KStandardTestClass.context,
                new TestShaderCompiler()
            ),
            new TestShaderCompiler()
        );

        KShaderProgram sp1 = spc.getAsset("sp1");
        KShaderProgram sp12 = spc.getAsset("sp1");

        Assertions.assertEquals(0, sp1.id());
        Assertions.assertEquals(sp12, sp1);

    }

}
