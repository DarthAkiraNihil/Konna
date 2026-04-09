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

package io.github.darthakiranihil.konna.compiler.core.util;

import io.github.darthakiranihil.konna.core.di.KAlsoProvides;
import io.github.darthakiranihil.konna.core.di.KQualifier;
import io.github.darthakiranihil.konna.core.di.KSingleton;
import io.github.darthakiranihil.konna.core.di.KTakeFrom;
import io.github.darthakiranihil.konna.core.util.KAnnotationUtils;
import org.jspecify.annotations.Nullable;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.LinkedList;
import java.util.List;

public final class KModuleMetadataReader {

    private final Types typeUtils;
    private final Elements elementUtils;
    private final Messager messager;

    private final TypeMirror appFeaturesType;
    private final TypeMirror systemFeaturesType;

    public KModuleMetadataReader(
        final Types typeUtils,
        final Elements elementUtils,
        final Messager messager
    ) {
        this.typeUtils = typeUtils;
        this.elementUtils = elementUtils;
        this.messager = messager;

        this.appFeaturesType = this.elementUtils
            .getTypeElement("io.github.darthakiranihil.konna.core.app.KApplicationFeatures")
            .asType();

        this.systemFeaturesType = this.elementUtils
            .getTypeElement("io.github.darthakiranihil.konna.core.app.KSystemFeatures")
            .asType();
    }

    public @Nullable KModuleMetadata read(final TypeElement clazz) {

        var builder = new KModuleMetadata.Builder(
            clazz.asType(),
            clazz.getSimpleName().toString()
        );
        boolean constructorAcquired  = false;

        for (Element enclosed: clazz.getEnclosedElements()) {
            switch (enclosed.getKind()) {
                case ElementKind.CONSTRUCTOR -> {
                    if (constructorAcquired) {
                        this.messager.printError(
                            "A module must have at most one constructor",
                            clazz
                        );
                        return null;
                    }

                    ExecutableElement constructor = (ExecutableElement) enclosed;
                    boolean ok = this.readConstructor(constructor, builder, clazz);
                    if (!ok) {
                        return null;
                    }

                    constructorAcquired = true;
                }
                case METHOD -> {
                    ExecutableElement providerMethod = (ExecutableElement) enclosed;
                    boolean ok = this.readProvider(providerMethod, builder, clazz);
                    if (!ok) {
                        return null;
                    }
                }
            }
        }

        return builder.build();

    }

    private boolean readConstructor(
        final ExecutableElement constructorElement,
        final KModuleMetadata.Builder builder,
        final TypeElement moduleClassElement
    ) {

        boolean gotAppFeatures = false;
        boolean gotSystemFeatures = false;

        var params = constructorElement.getParameters();
        for (var param: params) {
            TypeMirror paramType = param.asType();
            if (this.typeUtils.isSameType(paramType, this.appFeaturesType)) {
                if (gotAppFeatures) {
                    this.messager.printWarning(
                            "This constructor contains more that 2 args"
                            +   "with KApplicationFeatures type. Only the first of them will"
                            +   "be taken, while others will be ignored",
                        constructorElement
                    );
                    continue;
                }

                builder.setThatHasApplicationFeatures();
                gotAppFeatures = true;

            } else if (this.typeUtils.isSameType(paramType, this.systemFeaturesType)) {
                if (gotSystemFeatures) {
                    this.messager.printWarning(
                        "This constructor contains more that 2 args"
                        +   "with KSystemFeatures type. Only the first of them will"
                        +   "be taken, while others will be ignored",
                        constructorElement
                    );
                    continue;
                }

                builder.setThatHasSystemFeatures();
                gotSystemFeatures = true;
            } else {
                KTakeFrom src = param.getAnnotation(KTakeFrom.class);
                if (src == null) {
                    this.messager.printError(
                        String.format("Cannot provide parameter %s", param),
                        moduleClassElement
                    );
                    return false;
                }

                TypeMirror moduleDepClass = KAnnotationUtils.getClassValueFromAnnotation(
                    param,
                    KTakeFrom.class,
                    "module"
                );
                builder.addModuleDependency(moduleDepClass, paramType, src.qualifier());
            }
        }

        return true;
    }

    private boolean readProvider(
        final ExecutableElement providerMethod,
        final KModuleMetadata.Builder builder,
        final TypeElement moduleClassElement
    ) {

        var params = providerMethod.getParameters();
        if (!params.isEmpty()) {
            this.messager.printError(
                String.format(
                    "%s: Provider method cannot have arguments",
                    providerMethod.getSimpleName()
                ),
                moduleClassElement
            );
            return false;
        }

        boolean isSingleton = providerMethod.getAnnotation(KSingleton.class) != null;

        String qualifier = null;
        KQualifier qualifierAnnotation = providerMethod.getAnnotation(KQualifier.class);
        if (qualifierAnnotation != null) {
            qualifier = qualifierAnnotation.value();
        }

        List<TypeMirror> providedClasses = new LinkedList<>();
        providedClasses.add(providerMethod.getReturnType());
        if (providerMethod.getAnnotation(KAlsoProvides.class) != null) {
            providedClasses.addAll(
                List.of(
                    KAnnotationUtils.getAnnotationArrayValue(
                        providerMethod,
                        KAlsoProvides.class,
                        "value"
                    )
                )
            );
        }

        builder.addProvider(
            providerMethod.getSimpleName().toString(),
            providedClasses,
            isSingleton,
            qualifier
        );

        return true;
    }

}
