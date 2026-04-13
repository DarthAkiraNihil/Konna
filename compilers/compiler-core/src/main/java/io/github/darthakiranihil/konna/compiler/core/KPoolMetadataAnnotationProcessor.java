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
import io.github.darthakiranihil.konna.core.object.KPoolMetadata;
import io.github.darthakiranihil.konna.core.util.KBaseAnnotationProcessor;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import java.util.Objects;
import java.util.Set;

/**
 * <p>
 *     Annotation processor, specializing on checking correctness of a pollable classes
 *     (that must be marked with {@link KPoolMetadata} annotation).
 * </p>
 * <p>
     * A valid poolable class must have either
     * one and only one method marked with {@link KOnPoolableObjectObtain} or none.
     * Multiple methods with such annotation, are not allowed and will cause a compilation error.
 * </p>
 * <p>
 *     Also, a poolable class must implement {@code KPoolable} interface and contain
 *     a non-public zero-arg constructor.
 * </p>
 *
 * @since 0.4.0
 * @author Darth Akira Nihil
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes({
    "io.github.darthakiranihil.konna.core.object.KPoolMetadata"
})
@SupportedSourceVersion(SourceVersion.RELEASE_21)
@SuppressWarnings("unused")
public final class KPoolMetadataAnnotationProcessor extends KBaseAnnotationProcessor {

    @Override
    public boolean process(
        final Set<? extends TypeElement> annotations,
        final RoundEnvironment roundEnv
    ) {
        TypeMirror poolableType = this.elementUtils.getTypeElement(
            "io.github.darthakiranihil.konna.core.object.KPoolable").asType();

        for (Element element : roundEnv.getElementsAnnotatedWith(
            KPoolMetadata.class
        )) {

            if (element.getKind() != ElementKind.CLASS) {
                continue;
            }

            TypeElement classElement = (TypeElement) element;
            Name canonicalName = classElement.getQualifiedName();

            if (this.typeUtils.isSubtype(classElement.asType(), poolableType)) {
                this.messager.printError("A poolable class must extend KPoolable");
                continue;
            }

            KPoolMetadata metadata = Objects.requireNonNull(
                classElement.getAnnotation(KPoolMetadata.class)
            );

            if (metadata.maxSize() < 0) {
                this.messager.printError(
                    "Initial pool size must be positive",
                    classElement
                );
            }

            if (metadata.extensible()) {
                if (metadata.maxSize() < metadata.initialSize()) {
                    this.messager.printError(
                        "Max pool size cannot be less than its initial size",
                        classElement
                    );
                }

                if (metadata.extensionFactor() < 1.0) {
                    this.messager.printError(
                        "Cannot assign an extension factor less than 1 for extensible. Are you nuts?",
                        classElement
                    );
                }
            }

            int onObtain = 0;
            boolean hasZeroArgConstructor = false;

            for (Element enclosed: classElement.getEnclosedElements()) {
                if (enclosed.getKind() == ElementKind.CONSTRUCTOR) {
                    ExecutableElement method = (ExecutableElement) enclosed;
                    if (method.getParameters().isEmpty()) {
                        hasZeroArgConstructor = true;
                    }

                    if (method.getModifiers().contains(Modifier.PUBLIC)) {
                        this.messager.printError(
                            "Constructor must not be public for poolable classes",
                            classElement
                        );
                    }
                    continue;
                }

                if (enclosed.getKind() != ElementKind.METHOD) {
                    continue;
                }

                ExecutableElement method = (ExecutableElement) enclosed;
                var onObtainAnnotation = method.getAnnotation(KOnPoolableObjectObtain.class);
                if (onObtainAnnotation != null) {
                    onObtain++;
                }
            }

            if (onObtain > 1) {
                this.messager.printError(
                    String.format(
                        "%s: %s",
                        canonicalName,
                        "Cannot have more that one onObtain method on a poolable object"
                    ),
                    classElement
                );
            }

            if (!hasZeroArgConstructor) {
                this.messager.printError(
                    String.format(
                        "%s: %s",
                        canonicalName,
                        "A poolable class must have a zero-arg constructor"
                    ),
                    classElement
                );
            }
        }

        return true;
    }
}
