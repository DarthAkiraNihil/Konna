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
import io.github.darthakiranihil.konna.core.object.KOnPoolableObjectObtain;
import io.github.darthakiranihil.konna.core.object.KOnPoolableObjectRelease;
import io.github.darthakiranihil.konna.core.object.KPoolable;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import java.util.Set;

@AutoService(Processor.class)
@SupportedAnnotationTypes({
    "io.github.darthakiranihil.konna.core.object.KPoolable"
})
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public final class KPoolableAnnotationProcessor extends AbstractProcessor {

    private Messager messager;

    @Override
    public synchronized void init(final ProcessingEnvironment processingEnv) {

        super.init(processingEnv);
        this.messager = processingEnv.getMessager();

    }

    @Override
    public boolean process(
        final Set<? extends TypeElement> annotations,
        final RoundEnvironment roundEnv) {

        for (Element element : roundEnv.getElementsAnnotatedWith(
            KPoolable.class
        )) {

            if (element.getKind() != ElementKind.CLASS) {
                continue;
            }

            TypeElement classElement = (TypeElement) element;
            Name canonicalName = classElement.getQualifiedName();

            int onObtain = 0;
            int onRelease = 0;
            for (Element enclosed: classElement.getEnclosedElements()) {

                if (enclosed.getKind() != ElementKind.METHOD) {
                    continue;
                }

                ExecutableElement method = (ExecutableElement) enclosed;
                var onObtainAnnotation = method.getAnnotation(KOnPoolableObjectObtain.class);
                var onReleaseAnnotation = method.getAnnotation(KOnPoolableObjectRelease.class);

                if (onObtainAnnotation != null && onReleaseAnnotation != null) {
                    this.messager.printMessage(
                        Diagnostic.Kind.ERROR,
                        String.format(
                            "%s: Cannot have both %s and %s on a single method at the time",
                            canonicalName,
                            KOnPoolableObjectObtain.class.getSimpleName(),
                            KOnPoolableObjectRelease.class.getSimpleName()
                        )
                    );
                }

                if (onObtainAnnotation != null) {
                    onObtain++;
                }

                if (onReleaseAnnotation != null) {
                    onRelease++;
                }

            }

            if (onObtain > 1) {
                this.messager.printMessage(
                    Diagnostic.Kind.ERROR,
                    String.format(
                        "%s: %s",
                        canonicalName,
                        "Cannot have more that one onObtain method on a poolable object"
                    )
                );
            }

            if (onRelease > 1) {
                this.messager.printMessage(
                    Diagnostic.Kind.ERROR,
                    String.format(
                        "%s: %s",
                        canonicalName,
                        "Cannot have more that one onRelease method on a poolable object"
                    )
                );
            }

            if (onRelease != onObtain) {
                this.messager.printMessage(
                    Diagnostic.Kind.ERROR,
                    String.format(
                        "%s: %s",
                        canonicalName,
                            "A poolable class must have either none "
                        +   "or both of onRelease and onObtain methods"
                    )
                );
            }
        }

        return true;
    }
}
