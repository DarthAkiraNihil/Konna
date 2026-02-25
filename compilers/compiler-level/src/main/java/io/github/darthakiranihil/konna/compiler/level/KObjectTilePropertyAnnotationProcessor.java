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

package io.github.darthakiranihil.konna.compiler.level;

import com.google.auto.service.AutoService;
import com.palantir.javapoet.*;
import io.github.darthakiranihil.konna.core.util.KBaseAnnotationProcessor;
import io.github.darthakiranihil.konna.core.util.KGenerated;
import io.github.darthakiranihil.konna.level.KObjectTilePropertyType;
import org.jspecify.annotations.NonNull;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;

@AutoService(Processor.class)
@SupportedAnnotationTypes({
    "io.github.darthakiranihil.konna.level.KObjectTilePropertyType"
})
@SupportedSourceVersion(SourceVersion.RELEASE_21)
@SuppressWarnings("unused")
public class KObjectTilePropertyAnnotationProcessor extends KBaseAnnotationProcessor {

    private final ClassName factoryType;
    private final ClassName objectTilePropertyType;

    public KObjectTilePropertyAnnotationProcessor() {

        this.factoryType = ClassName.get(
            "io.github.darthakiranihil.konna.level",
            "KObjectTilePropertyFactory"
        );

        this.objectTilePropertyType = ClassName.get(
            "io.github.darthakiranihil.konna.level",
            "KObjectTileProperty"
        );

    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(
            KObjectTilePropertyType.class
        )) {

            if (element.getKind() != ElementKind.CLASS) {
                continue;
            }

            TypeElement type = (TypeElement) element;
            KObjectTilePropertyType meta = Objects.requireNonNull(
                type.getAnnotation(KObjectTilePropertyType.class)
            );

            TypeElement mapper = this.elementUtils.getTypeElement(meta.mapper());
            if (mapper == null) {
                this.messager.printMessage(
                    Diagnostic.Kind.ERROR,
                    String.format(
                        "%s: unknown mapper class - %s",
                        type.getQualifiedName(),
                        meta.mapper()
                    )
                );
                continue;
            }

            this.brewJava(
                meta.alias(),
                type,
                mapper
            );

        }

        return true;

    }

    private void brewJava(
        final String alias,
        final TypeElement propertyClass,
        final TypeElement mapperClass
    ) {

        ParameterizedTypeName propertyTypeInterface = ParameterizedTypeName.get(
            this.factoryType,
            ClassName.get(propertyClass)
        );

        TypeSpec factory = TypeSpec
            .classBuilder(
                String.format(
                    "%s$$Factory",
                    alias
                )
            )
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            .addSuperinterface(propertyTypeInterface)
            .addAnnotation(KGenerated.class)
            .addMethod(
                this.brewCreateMethod(
                    propertyClass,
                    mapperClass
                )
            )
            .build();


        JavaFile javaFile = JavaFile.builder(
                "konna.generated.level.props",
                factory
            )
            .indent("    ")
            .addFileComment("Generated with Konna annotation processor. Do not edit!")
            .build();

        try {
            javaFile.writeTo(this.filer);
        } catch (IOException e) {
            this.messager.printMessage(
                Diagnostic.Kind.ERROR,
                String.format(
                    "Could not generate registerer class: %s",
                    e
                )
            );
        }

    }

    private MethodSpec brewCreateMethod(
        final TypeElement propertyClass,
        final TypeElement mapperClass
    ) {

        ParameterizedTypeName propType = ParameterizedTypeName
            .get(
                this.objectTilePropertyType,
                ClassName.get(propertyClass)
            );

        return MethodSpec
            .methodBuilder("create")
            .addModifiers(Modifier.PUBLIC)
            .addAnnotation(Override.class)
            .addParameter(
                ParameterSpec
                    .builder(Object.class, "value")
                    .addAnnotation(NonNull.class)
                    .build()
            )
            .returns(propType)
            .beginControlFlow(
                "if (!$T.class.isAssignableFrom(value.getClass()))",
                ClassName.get(
                    "io.github.darthakiranihil.konna.core.io",
                    "KAssetDefinition"
                )
            )
            .addStatement(
                "throw new $T($S)",
                ClassName.get(
                    "io.github.darthakiranihil.konna.core.except",
                    "KInvalidArgumentException"
                ),
                "Value is not an asset definition"
            )
            .endControlFlow()
            .addStatement(
                "var mapped = new $T().map((KAssetDefinition) value)",
                mapperClass
            )
            .addStatement(
                "return $L",
                TypeSpec
                    .anonymousClassBuilder("")
                    .addSuperinterface(propType)
                    .addMethod(
                        MethodSpec
                            .methodBuilder("getValue")
                            .addAnnotation(Override.class)
                            .addModifiers(Modifier.PUBLIC)
                            .returns(ClassName.get(propertyClass))
                            .addStatement("return mapped")
                            .build()
                    )
                    .build()
            )
            .build();


    }

}
