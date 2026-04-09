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

import com.palantir.javapoet.*;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import javax.lang.model.element.Modifier;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.util.*;

/**
 * Code Generator for module containers. For internal use only.
 */
public final class KModuleContainerBrewer {

    private static final ClassName CONTAINER_CLASS_NAME = ClassName.get(
        "io.github.darthakiranihil.konna.core.di",
        "KContainer2"
    );

    private static final ClassName OBJECT_SUPPLIER_CLASS_NAME = ClassName.get(
        "io.github.darthakiranihil.konna.core.di",
        "KObjectSupplier"
    );

    private static final ClassName APPLICATION_FEATURES_CLASS_NAME = ClassName.get(
        "io.github.darthakiranihil.konna.core.app",
        "KApplicationFeatures"
    );

    private static final ClassName SYSTEM_FEATURES_CLASS_NAME = ClassName.get(
        "io.github.darthakiranihil.konna.core.app",
        "KSystemFeatures"
    );

    /**
     * Generates Java files for provided modules.
     * @param modules Queue of modules to generate code for
     * @return Queue of containers metadata to use to generate app container
     */
    public Queue<KModuleContainerMetadata> brewJava(final Queue<KModuleMetadata> modules) {

        Map<String, KModuleContainerMetadata> generatedContainers = new HashMap<>(modules.size());
        Queue<KModuleContainerMetadata> containerQueue = new ArrayDeque<>(modules.size());
        while (!modules.isEmpty()) {
            KModuleMetadata current = modules.poll();
            TypeSpec container = this.brewModuleContainer(current, generatedContainers);
            KModuleContainerMetadata metadata = new KModuleContainerMetadata(
                current,
                container.name(),
                container
            );

            containerQueue.add(metadata);
            generatedContainers.put(current.className(), metadata);
        }

        return containerQueue;

    }

    private TypeSpec brewModuleContainer(
        final KModuleMetadata module,
        final Map<String, KModuleContainerMetadata> generatedContainers
    ) {
        var builder = TypeSpec
            .classBuilder(String.format("%s$$ModuleContainer", module.className()))
            .addAnnotation(
                AnnotationSpec
                    .builder(SuppressWarnings.class)
                    .addMember("value", "$S", "unchecked")
                    .build()
            )
            .addSuperinterface(CONTAINER_CLASS_NAME)
            .addModifiers(Modifier.FINAL)
            .addField(
                FieldSpec
                    .builder(
                        TypeName.get(module.type()),
                        "module",
                        Modifier.FINAL,
                        Modifier.PRIVATE
                    )
                    .build()
            )
            .addField(
                FieldSpec
                    .builder(
                        ParameterizedTypeName.get(
                            ClassName.get(Map.class),
                            ParameterizedTypeName.get(
                                ClassName.get(Class.class),
                                WildcardTypeName.subtypeOf(Object.class)
                            ),
                            OBJECT_SUPPLIER_CLASS_NAME
                        ),
                        "mapping",
                        Modifier.FINAL,
                        Modifier.PRIVATE
                    )
                    .build()
            );

        for (var provider: module.providers()) {
            String mainSimpleName = ((DeclaredType) provider.mainProvidedClass())
                .asElement()
                .getSimpleName()
                .toString();

            builder.addMethod(this.brewProviderMethod(provider, mainSimpleName));

            if (!provider.isSingleton()) {
                continue;
            }

            builder.addField(
                FieldSpec
                    .builder(
                        TypeName.get(provider.mainProvidedClass()),
                        String.format("singleton%s", mainSimpleName)
                    )
                    .addAnnotation(Nullable.class)
                    .addModifiers(Modifier.PRIVATE)
                    .build()
            );
        }

        builder
            .addMethod(this.brewContainerConstructor(module, generatedContainers))
            .addMethod(this.brewSwitch());

        return builder.build();
    }

    private MethodSpec brewContainerConstructor(
        final KModuleMetadata module,
        final Map<String, KModuleContainerMetadata> generatedContainers
    ) {

        var builder = MethodSpec.constructorBuilder();
        if (module.hasApplicationFeatures()) {
            builder.addParameter(
                APPLICATION_FEATURES_CLASS_NAME,
                "applicationFeatures",
                Modifier.FINAL
            );
        }

        if (module.hasSystemFeatures()) {
            builder.addParameter(
                SYSTEM_FEATURES_CLASS_NAME,
                "systemFeatures",
                Modifier.FINAL
            );
        }

        int depModuleNumber = 0;
        for (var dep: module.moduleDependencies()) {
            String depSimpleName = ((DeclaredType) dep.module())
                .asElement()
                .getSimpleName()
                .toString();

            KModuleContainerMetadata depContainer = generatedContainers.get(depSimpleName);

            if (depContainer == null) {
                throw new IllegalStateException(
                    String.format(
                            "Module %s has requested module %s, which has not generated yet. "
                        +   "Maybe a circular dependency?",
                        module.className(),
                            depSimpleName
                    )
                );
            }

            builder.addParameter(
                ClassName.get(
                    "konna.generated.core.modules",
                    depContainer.containerClassName()
                ),
                String.format("c%d", depModuleNumber),
                Modifier.FINAL
            );
            depModuleNumber++;
        }

        var moduleInstantiationStatement = CodeBlock.builder();
        moduleInstantiationStatement.add("this.module = new $T(", module.type()).indent();
        if (module.hasApplicationFeatures()) {
            moduleInstantiationStatement.add("applicationFeatures,");
        }

        if (module.hasSystemFeatures()) {
            moduleInstantiationStatement.add("systemFeatures");
        }

        if (!module.moduleDependencies().isEmpty()) {
            if (module.hasApplicationFeatures() || module.hasSystemFeatures()) {
                moduleInstantiationStatement.add(",");
            }
            var deps = module.moduleDependencies();
            for (int i = 0; i < deps.size(); i++) {
                KModuleMetadata.ModuleDependency depDesc = deps.get(i);

                TypeName requiredTypeName = TypeName.get(depDesc.requiredType());
                if (requiredTypeName instanceof ParameterizedTypeName) {
                    moduleInstantiationStatement.add(
                        String.format("($T) c%d.getInstance($T.class)", i),
                        ((ParameterizedTypeName) requiredTypeName).rawType(),
                        ((ParameterizedTypeName) requiredTypeName).rawType()
                    );
                } else {
                    moduleInstantiationStatement.add(
                        String.format("($T) c%d.getInstance($T.class)", i),
                        requiredTypeName,
                        requiredTypeName
                    );
                }

                if (i < deps.size() - 1) {
                    moduleInstantiationStatement.add(",");
                }
            }
        }

        moduleInstantiationStatement
            .unindent()
            .add(")");

        builder
            .addStatement(moduleInstantiationStatement.build())
            .addStatement("this.mapping = new $T<>()", HashMap.class);

        for (KModuleMetadata.ProviderDescription provider: module.providers()) {
            for (TypeMirror providedType: provider.providedClasses()) {
                builder.addStatement(
                    String.format("this.mapping.put($T.class, this::%s)", provider.methodName()),
                    providedType
                );
            }
        }

        return builder.build();
    }

    private MethodSpec brewProviderMethod(
        final KModuleMetadata.ProviderDescription providerDescription,
        final String simpleProvidedClassName
    ) {
        var builder = MethodSpec
            .methodBuilder(providerDescription.methodName())
            .returns(TypeName.get(providerDescription.mainProvidedClass()))
            .addModifiers(Modifier.PRIVATE);

        if (providerDescription.isSingleton()) {
            String singletonField = String.format("this.singleton%s", simpleProvidedClassName);
            builder
                .beginControlFlow(String.format("if (%s == null)", singletonField))
                .addStatement(
                    String.format(
                        "%s = this.module.%s()",
                        singletonField,
                        providerDescription.methodName()
                    )
                )
                .endControlFlow()
                .addStatement(String.format("return %s", singletonField));
        } else {
            builder.addStatement(
                String.format("return this.module.%s()", providerDescription.methodName())
            );
        }

        return builder.build();

    }

    private MethodSpec brewSwitch() {

        return MethodSpec
            .methodBuilder("getInstance")
            .returns(Object.class)
            .addModifiers(Modifier.PUBLIC)
            .addAnnotation(Override.class)
            .addAnnotation(Nullable.class)
            .addParameter(
                ParameterSpec
                    .builder(
                        ParameterizedTypeName.get(
                            ClassName.get(Class.class),
                            WildcardTypeName.subtypeOf(Object.class)
                        ),
                        "clazz",
                        Modifier.FINAL
                    )
                    .addAnnotation(NonNull.class)
                    .build()
            )
            .addStatement("var supplier = this.mapping.get(clazz)")
            .addStatement("return supplier == null ? null : supplier.get()")
            .build();

    }
}
