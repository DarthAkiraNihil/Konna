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

package io.github.darthakiranihil.konna.compiler.core;

import com.google.auto.service.AutoService;
import io.github.darthakiranihil.konna.compiler.core.util.KModuleMetadata;
import io.github.darthakiranihil.konna.compiler.core.util.KModuleMetadataReader;
import io.github.darthakiranihil.konna.core.di.KModule;
import io.github.darthakiranihil.konna.core.util.KBaseAnnotationProcessor;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.util.Set;

@AutoService(Processor.class)
@SupportedAnnotationTypes({
    "io.github.darthakiranihil.konna.core.di.KModule"
})
@SupportedSourceVersion(SourceVersion.RELEASE_21)
@SuppressWarnings("unused")
public final class KModuleAnnotationProcessor extends KBaseAnnotationProcessor {

    private KModuleMetadataReader moduleMetadataReader;

    @Override
    public synchronized void init(final ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        this.moduleMetadataReader = new KModuleMetadataReader(
            this.typeUtils,
            this.elementUtils,
            this.messager
        );

    }

    @Override
    public boolean process(
        final Set<? extends TypeElement> annotations,
        final RoundEnvironment roundEnv
    ) {
        for (Element element : roundEnv.getElementsAnnotatedWith(
            KModule.class
        )) {

            if (element.getKind() != ElementKind.CLASS) {
                this.messager.printWarning(
                    "KModule only works for standard classes. Class is skipped",
                    element
                );
                continue;
            }

            TypeElement classElement = (TypeElement) element;
            KModuleMetadata moduleMetadata = this.moduleMetadataReader.read(classElement);

        }

        return true;
    }

}
