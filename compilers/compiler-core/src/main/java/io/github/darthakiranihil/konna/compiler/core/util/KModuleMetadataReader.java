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

import org.jspecify.annotations.Nullable;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

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

        KModuleMetadata.Builder builder = new KModuleMetadata.Builder();
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
                    this.readConstructor(constructor, builder);
                    constructorAcquired = true;
                    break;
                }
                default -> {
                    continue;
                }
            }
        }

        return builder.build();

    }

    private void readConstructor(
        final ExecutableElement constructorElement,
        final KModuleMetadata.Builder builder
    ) {

        boolean gotAppFeatures = false;
        boolean gotSystemFeatures = false;

        var params = constructorElement.getTypeParameters();
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
            }
        }

    }

}
