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
import io.github.darthakiranihil.konna.core.di.KProvided;
import io.github.darthakiranihil.konna.core.di.KSingleton;
import io.github.darthakiranihil.konna.core.util.KAnnotationUtils;
import org.jspecify.annotations.Nullable;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Class that reads metadata of a module. For internal use only.
 */
public final class KModuleMetadataReader {

    private final Types typeUtils;
    private final Messager messager;

    private final TypeMirror appFeaturesType;
    private final TypeMirror systemFeaturesType;
    private final TypeMirror appContainerType;
    private final TypeMirror abstractModuleType;

    public KModuleMetadataReader(
        final Types typeUtils,
        final Elements elementUtils,
        final Messager messager
    ) {
        this.typeUtils = typeUtils;
        this.messager = messager;

        this.appFeaturesType = elementUtils
            .getTypeElement("io.github.darthakiranihil.konna.core.app.KApplicationFeatures")
            .asType();

        this.systemFeaturesType = elementUtils
            .getTypeElement("io.github.darthakiranihil.konna.core.app.KSystemFeatures")
            .asType();

        this.appContainerType = elementUtils
            .getTypeElement("io.github.darthakiranihil.konna.core.di.KAppContainer")
            .asType();

        this.abstractModuleType = elementUtils
            .getTypeElement("io.github.darthakiranihil.konna.core.di.KAbstractModule")
            .asType();

    }

    /**
     * Reads module metadata for provided type element.
     * @param clazz Module's type element
     * @return Read module metadata or {@link null} if it failed
     */
    public @Nullable KModuleMetadata read(final TypeElement clazz) {

        if (!this.typeUtils.isSubtype(clazz.asType(), this.abstractModuleType)) {
            this.messager.printError(
                "Module does not extend KAbstractModule",
                clazz
            );
        }

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
                    boolean ok = this.validateConstructor(constructor, clazz);
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

        KModuleMetadata metadata = builder.build();
        return this.validate(metadata.className(), metadata) ? metadata : null;

    }

    private boolean validateConstructor(
        final ExecutableElement constructorElement,
        final TypeElement moduleClassElement
    ) {

        var params = constructorElement.getParameters();
        if (params.size() > 3) {
            this.messager.printError(
                "Module has to have exact 3 constructor parameters, but there is more",
                moduleClassElement
            );
        }

        var first = params.getFirst();
        var second = params.get(1);
        var third = params.get(2);

        boolean orderIsCorrect =
                this.typeUtils.isSameType(first.asType(), this.appFeaturesType)
            &&  this.typeUtils.isSameType(second.asType(), this.systemFeaturesType)
            &&  this.typeUtils.isSameType(third.asType(), this.appContainerType);

        if (!orderIsCorrect) {
            this.messager.printError(
                String.format(
                    "Incorrect module's constructor parameter ordering. Got: %s, %s, %s",
                    first.asType(),
                    second.asType(),
                    third.asType()
                ),
                moduleClassElement
            );
            return false;
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

        List<TypeMirror> providedClasses = new LinkedList<>();
        providedClasses.add(providerMethod.getReturnType());
        if (providerMethod.getAnnotation(KAlsoProvides.class) != null) {
            providedClasses.addAll(
                List.of(
                    KAnnotationUtils.getClassArrayValueFromAnnotation(
                        providerMethod,
                        KAlsoProvides.class,
                        "value"
                    )
                )
            );
        }

        builder.addProvider(
            providerMethod.getSimpleName().toString(),
            providerMethod.getReturnType(),
            providedClasses,
            isSingleton
        );

        return true;
    }

    private boolean validate(
        final String moduleClassName,
        final KModuleMetadata metadata
    ) {
        Set<TypeMirror> providedTypes = new HashSet<>(metadata.providers().size());
        boolean ok = true;
        for (KModuleMetadata.ProviderDescription provider: metadata.providers()) {
            for (TypeMirror providedType: provider.providedClasses()) {
                if (providedTypes.contains(providedType)) {
                    this.messager.printError(
                        String.format(
                            "%s: Conflict: type %s has been already provided by this module",
                            moduleClassName,
                            providedType
                        )
                    );
                    ok = false;
                }

                Element providedElement = ((DeclaredType) providedType).asElement();
                if (providedElement.getAnnotation(KProvided.class) != null)  {
                    this.messager.printError(
                        String.format(
                            "%s: Attempt to add provider for guaranteed provided type %s",
                            moduleClassName,
                            providedType
                        )
                    );
                    ok = false;
                }

                providedTypes.add(providedType);
            }

        }

        return ok;
    }

}
