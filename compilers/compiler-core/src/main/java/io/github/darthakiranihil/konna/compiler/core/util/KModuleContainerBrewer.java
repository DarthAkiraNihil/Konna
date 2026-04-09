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
import org.jspecify.annotations.Nullable;

import javax.lang.model.element.Modifier;
import javax.lang.model.type.DeclaredType;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public final class KModuleContainerBrewer {

    private static final ClassName CONTAINER_CLASS_NAME = ClassName.get(
        "io.github.darthakiranihil.konna.core.di",
        "KContainer2"
    );

    private static final ClassName APPLICATION_FEATURES_CLASS_NAME = ClassName.get(
        "io.github.darthakiranihil.konna.core.app",
        "KApplicationFeatures"
    );

    private static final ClassName SYSTEM_FEATURES_CLASS_NAME = ClassName.get(
        "io.github.darthakiranihil.konna.core.app",
        "KSystemFeatures"
    );

    public Queue<KModuleContainerMetadata> brewJava(final Queue<KModuleMetadata> modules) {

        Map<String, KModuleContainerMetadata> generatedContainers = new HashMap<>(modules.size());
        Queue<KModuleContainerMetadata> containerQueue = new ArrayDeque<>(modules.size());
        while (!modules.isEmpty()) {
            KModuleMetadata current = modules.poll();
            TypeSpec container = this.brewModuleContainer(current, generatedContainers);
            containerQueue.add(
                new KModuleContainerMetadata(
                    current,
                    container.name(),
                    container
                )
            );
        }

        return containerQueue;

    }

    private TypeSpec brewModuleContainer(
        final KModuleMetadata module,
        final Map<String, KModuleContainerMetadata> generatedContainers
    ) {
        var builder = TypeSpec
            .classBuilder(String.format("%s$$ModuleContainer", module.className()))
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

        if (
                module.hasSystemFeatures()
            ||  module.hasApplicationFeatures()
            ||  !module.moduleDependencies().isEmpty()
        ) {
            builder.addMethod(this.brewContainerConstructor(module, generatedContainers));
        }



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
        moduleInstantiationStatement.addStatement("this.module = new $T(").indent();
        if (module.hasApplicationFeatures()) {
            moduleInstantiationStatement.add("applicationFeatures,");
        }

        if (module.hasSystemFeatures()) {
            moduleInstantiationStatement.add("systemFeatures");
        }

        if (!module.moduleDependencies().isEmpty()) {
            moduleInstantiationStatement.add(",");
            var deps = module.moduleDependencies();
            for (int i = 0; i < deps.size(); i++) {
                KModuleMetadata.ModuleDependency depDesc = deps.get(i);
                moduleInstantiationStatement.add(
                    String.format("c%d.getInstance($T.class, $S)", i),
                    depDesc.requiredType(),
                    depDesc.qualifier()
                );

                if (i < deps.size() - 1) {
                    moduleInstantiationStatement.add(",");
                }
            }

            moduleInstantiationStatement
                .unindent()
                .add(")");
        }

        moduleInstantiationStatement
            .unindent()
            .add(")");

        builder.addStatement(moduleInstantiationStatement.build());

        return builder.build();
    }

    private MethodSpec brewProviderMethod(
        final KModuleMetadata.ProviderDescription providerDescription,
        final String simpleProvidedClassName
    ) {

        String methodName = String.format("get%s", simpleProvidedClassName);
        var builder = MethodSpec
            .methodBuilder(methodName)
            .addModifiers(Modifier.PRIVATE);

        if (providerDescription.isSingleton()) {
            String singletonField = String.format("this.singleton%s", simpleProvidedClassName);
            builder
                .beginControlFlow(String.format("if (%s == null)", singletonField))
                .addStatement(String.format("%s = this.%s", singletonField, methodName))
                .endControlFlow()
                .addStatement(String.format("return %s", singletonField));
        } else {
            builder.addStatement(String.format("return %s", methodName));
        }

        return builder.build();

    }

}
