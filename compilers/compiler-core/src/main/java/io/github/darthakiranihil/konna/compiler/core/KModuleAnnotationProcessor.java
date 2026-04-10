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
import com.palantir.javapoet.*;
import io.github.darthakiranihil.konna.compiler.core.util.*;
import io.github.darthakiranihil.konna.core.di.KModule;
import io.github.darthakiranihil.konna.core.util.KBaseAnnotationProcessor;
import io.github.darthakiranihil.konna.core.util.KGenerated;
import org.jspecify.annotations.NonNull;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 *     Annotation processor that handles {@link KModule} annotation to generate module containers
 *     to be used in the new DI system.
 * </p>
 * <p>
 *     There are some limitations applied to module classes (annotated with {@link KModule}):
 *     <ul>
 *         <li>
 *             A module class can have at most one of these constructors
 *             <ul>
 *                 <li>
 *                     A zero-arg constructor (or default)
 *                 </li>
 *                 <li>
 *                     A constructor with the only KApplicationFeatures parameter
 *                 </li>
 *                 <li>
 *                     A constructor with KApplicationFeatures and KSystemFeatures
 *                     parameters (in exact given order)
 *                 </li>
 *                 <li>
 *                     A constructor with the only KSystemFeatures parameter
 *                 </li>
 *                 <li>
 *                     A constructor with KApplicationFeatures, KSystemFeatures
 *                     and other parameters (in exact given order). Other parameters
 *                     must be annotated with
 *                     {@link io.github.darthakiranihil.konna.core.di.KTakeFrom}
 *                 </li>
 *             </ul>
 *         </li>
 *         <li>
 *             There have to be no repeating providers that return objects with same types.
 *         </li>
 *         <li>
 *             Module's methods must not have any parameters
 *         </li>
 *     </ul>
 * </p>
 *
 * @since 0.6.0
 * @author Darth Akira Nihil
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes({
    "io.github.darthakiranihil.konna.core.di.KModule"
})
@SupportedSourceVersion(SourceVersion.RELEASE_21)
@SuppressWarnings("unused")
public final class KModuleAnnotationProcessor extends KBaseAnnotationProcessor {

    private static final ClassName APP_CONTAINER_CLASS_NAME = ClassName.get(
        "io.github.darthakiranihil.konna.core.di",
        "KAppContainer"
    );

    private static final ClassName APPLICATION_FEATURES_CLASS_NAME = ClassName.get(
        "io.github.darthakiranihil.konna.core.app",
        "KApplicationFeatures"
    );

    private static final ClassName SYSTEM_FEATURES_CLASS_NAME = ClassName.get(
        "io.github.darthakiranihil.konna.core.app",
        "KSystemFeatures"
    );

    private static final ClassName CONTAINER_CLASS_NAME = ClassName.get(
        "io.github.darthakiranihil.konna.core.di",
        "KContainer2"
    );

    private static final ClassName DEPENDENCY_RESOLVE_EXCEPTION_CLASS_NAME = ClassName.get(
        "io.github.darthakiranihil.konna.core.di.except",
        "KDependencyResolveException"
    );

    private KModuleMetadataReader moduleMetadataReader;
    private KModuleContainerBrewer brewer;

    @Override
    public synchronized void init(final ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        this.moduleMetadataReader = new KModuleMetadataReader(
            this.typeUtils,
            this.elementUtils,
            this.messager
        );

        this.brewer = new KModuleContainerBrewer();
    }

    @Override
    public boolean process(
        final Set<? extends TypeElement> annotations,
        final RoundEnvironment roundEnv
    ) {

        List<KModuleMetadata> modules = new LinkedList<>();
        Set<TypeMirror> allProvidedTypes = new HashSet<>();

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

            boolean hasConflict = false;
            TypeElement classElement = (TypeElement) element;
            KModuleMetadata moduleMetadata = this.moduleMetadataReader.read(classElement);
            if (moduleMetadata == null) {
                continue;
            }

            for (KModuleMetadata.ProviderDescription provider: moduleMetadata.providers()) {
                for (TypeMirror providedType: provider.providedClasses()) {
                    if (allProvidedTypes.contains(providedType)) {
                        this.messager.printError(
                            String.format(
                                "Conflict: type %s has been already by other modules",
                                providedType
                            )
                        );
                        hasConflict = true;
                    }

                    allProvidedTypes.add(providedType);
                }
            }

            if (hasConflict) {
                return true;
            }

            modules.add(moduleMetadata);
        }

        if (modules.isEmpty()) {
            return true;
        }
        var containers = this.brewer.brewJava(modules);

        for (var container: containers) {
            JavaFile javaFile = JavaFile.builder(
                "konna.generated.core.modules",
                container.containerSpec()
            ).indent("    ").addFileComment(
                "Generated with Konna annotation processor. Do not edit!").build();

            try {
                javaFile.writeTo(this.filer);
            } catch (IOException e) {
                this.messager.printMessage(
                    Diagnostic.Kind.ERROR,
                    String.format("Could not generate registerer class: %s", e)
                );
            }
        }

        JavaFile javaFile = JavaFile.builder(
            "konna.generated.core.modules",
            this.brewAppContainer(containers)
        )
            .indent("    ")
            .addFileComment("Generated with Konna annotation processor. Do not edit!")
            .build();

        try {
            javaFile.writeTo(this.filer);
        } catch (IOException e) {
            this.messager.printMessage(
                Diagnostic.Kind.ERROR,
                String.format("Could not generate registerer class: %s", e)
            );
        }

        return true;
    }

    private TypeSpec brewAppContainer(
        final Queue<KModuleContainerMetadata> containerQueue
    ) {

        var builder = TypeSpec
            .classBuilder("kGeneratedAppContainer")
            .superclass(APP_CONTAINER_CLASS_NAME)
            .addModifiers(Modifier.FINAL)
            .addAnnotation(KGenerated.class)
            .addField(
                ParameterizedTypeName.get(
                    ClassName.get(List.class),
                    CONTAINER_CLASS_NAME
                ),
                "modules",
                Modifier.PRIVATE,
                Modifier.FINAL
            );

        var constructor = MethodSpec
            .constructorBuilder()
            .addParameter(
                ParameterSpec
                    .builder(
                        APPLICATION_FEATURES_CLASS_NAME,
                        "applicationFeatures",
                        Modifier.FINAL
                    )
                    .addAnnotation(NonNull.class)
                    .build()
            )
            .addParameter(
                ParameterSpec
                    .builder(
                        SYSTEM_FEATURES_CLASS_NAME,
                        "systemFeatures",
                        Modifier.FINAL
                    )
                    .addAnnotation(NonNull.class)
                    .build()
            )
            .addStatement("super(applicationFeatures, systemFeatures)")
            .addStatement("this.modules = new $T<>($L)", ArrayList.class, containerQueue.size());

        Map<String, KModuleContainerMetadata> processedContainers = new HashMap<>();

        int createdModules = 0;
        while (!containerQueue.isEmpty()) {
            KModuleContainerMetadata current = containerQueue.poll();
            constructor
                .addStatement(
                    CodeBlock
                        .builder()
                        .add(
                            String.format("var module%d = new $T(", createdModules),
                            ClassName.get(
                                "konna.generated.core.modules",
                                current.containerSpec().name()
                            )
                        )
                        .add("this.applicationFeatures, ")
                        .add("this.systemFeatures, ")
                        .add("this)")
                        .build()
                )
                .addStatement(String.format("this.modules.add(module%d)", createdModules));

            createdModules++;
        }

        return builder
            .addMethod(constructor.build())
            .addMethod(
                MethodSpec
                    .methodBuilder("getInstance")
                    .returns(Object.class)
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Override.class)
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
                        .beginControlFlow("for (var module: this.modules)")
                        .addStatement("var instance = module.getInstance(clazz)")
                        .beginControlFlow("if (instance != null)")
                        .addStatement("return instance")
                        .endControlFlow()
                        .endControlFlow()
                        .addStatement(
                            "throw new $T(clazz)",
                            DEPENDENCY_RESOLVE_EXCEPTION_CLASS_NAME
                        )
                        .build()
            )
            .build();
    }

}
