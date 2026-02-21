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
import io.github.darthakiranihil.konna.core.object.KSingleton;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

/**
 * Annotation processor that checks correctness of a singleton class.
 * A singleton cannot be weak and immortal at the same time.
 *
 * @since 0.4.0
 * @author Darth Akira Nihil
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes({
    "io.github.darthakiranihil.konna.core.object.KSingleton"
})
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public final class KSingletonAnnotationProcessor extends AbstractProcessor {

    private Messager messager;

    @Override
    public synchronized void init(final ProcessingEnvironment processingEnv) {

        super.init(processingEnv);
        this.messager = processingEnv.getMessager();

    }

    @Override
    public boolean process(
        final Set<? extends TypeElement> annotations,
        final RoundEnvironment roundEnv
    ) {
        for (Element element : roundEnv.getElementsAnnotatedWith(
            KSingleton.class
        )) {

            if (element.getKind() != ElementKind.CLASS) {
                continue;
            }

            TypeElement classElement = (TypeElement) element;
            KSingleton meta = classElement.getAnnotation(KSingleton.class);
            if (meta == null) {
                continue;
            }

            if (meta.immortal() && meta.weak()) {
                this.messager.printMessage(
                    Diagnostic.Kind.ERROR,
                    String.format(
                        "%s: %s",
                        classElement.getQualifiedName(),
                        "A singleton cannot be weak and immortal at the same time"
                    )
                );
            }
        }

        return true;
    }
}
