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
import io.github.darthakiranihil.konna.level.generator.KGeneratorInputParam;
import io.github.darthakiranihil.konna.level.generator.KGeneratorInputParams;
import io.github.darthakiranihil.konna.level.generator.KGeneratorOutputParam;
import io.github.darthakiranihil.konna.level.generator.KGeneratorOutputParams;
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
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.*;

@AutoService(Processor.class)
@SupportedAnnotationTypes({
    "io.github.darthakiranihil.konna.level.generator.KGeneratorInputParam",
    "io.github.darthakiranihil.konna.level.generator.KGeneratorInputParams",
    "io.github.darthakiranihil.konna.level.generator.KGeneratorOutputParam",
    "io.github.darthakiranihil.konna.level.generator.KGeneratorOutputParams",
})
@SupportedSourceVersion(SourceVersion.RELEASE_21)
@SuppressWarnings("unused")
public final class KGeneratorNodeParamsAnnotationProcessor extends KBaseAnnotationProcessor {

    private static final ClassName UNIVERSAL_MAP_CLASS_NAME = ClassName.get(
        "io.github.darthakiranihil.konna.core.data",
        "KUniversalMap"
    );

    private static final ClassName VALIDATOR_CLASS_NAME = ClassName.get(
        "io.github.darthakiranihil.konna.core.util",
        "KValidator"
    );

    private static final ClassName INVALID_ARG_EXCEPTION_CLASS_NAME = ClassName.get(
        "io.github.darthakiranihil.konna.core.except",
        "KInvalidArgumentException"
    );

    private record Param(
        String name,
        TypeMirror type
    ) {

    }

    @Override
    public boolean process(
        final Set<? extends TypeElement> annotations,
        final RoundEnvironment roundEnv
    ) {

        Map<String, Set<Param>> inputs = this.collectInputs(roundEnv);
        Map<String, Set<Param>> outputs = this.collectOutputs(roundEnv);

        if (!inputs.isEmpty()) {
            this.brewJava(inputs, "Input");
        }

        if (!outputs.isEmpty()) {
            this.brewJava(outputs, "Output");
        }

        return true;
    }

    private Map<String, Set<Param>> collectInputs(
        final RoundEnvironment roundEnv
    ) {

        Map<String, Set<Param>> collected = new HashMap<>();

        for (var element: roundEnv.getElementsAnnotatedWith(KGeneratorInputParams.class)) {

            Element enclosing = element.getEnclosingElement();
            if (enclosing.getKind() != ElementKind.CLASS) {
                continue;
            }

            TypeElement enclosingType = (TypeElement) enclosing;
            String enclosingSimpleName = enclosingType.getSimpleName().toString();
            if (!collected.containsKey(enclosingSimpleName)) {
                collected.put(enclosingSimpleName, new HashSet<>());
            }

            Set<Param> collectedForClass = collected.get(enclosingSimpleName);

            KGeneratorInputParams params = Objects.requireNonNull(
                element.getAnnotation(KGeneratorInputParams.class)
            );
            for (var param: params.value()) {
                // todo: more proper way to get class value
                TypeMirror paramType = null;
                try {
                    var clazz = param.type();
                } catch (MirroredTypeException e) {
                    paramType = e.getTypeMirror();
                }
                Objects.requireNonNull(paramType);

                collectedForClass.add(new Param(param.name(), paramType));

                collectedForClass.add(new Param(param.name(), paramType));
            }

        }

        for (var element: roundEnv.getElementsAnnotatedWith(KGeneratorInputParam.class)) {

            Element enclosing = element.getEnclosingElement();
            if (enclosing.getKind() != ElementKind.CLASS) {
                continue;
            }

            if (element.getAnnotation(KGeneratorInputParams.class) != null) {
                continue;
            }

            TypeElement enclosingType = (TypeElement) enclosing;
            String enclosingSimpleName = enclosingType.getSimpleName().toString();
            if (!collected.containsKey(enclosingSimpleName)) {
                collected.put(enclosingSimpleName, new HashSet<>());
            }

            Set<Param> collectedForClass = collected.get(enclosingSimpleName);

            KGeneratorInputParam param = Objects.requireNonNull(
                element.getAnnotation(KGeneratorInputParam.class)
            );
            TypeMirror paramType = this.getClassValueFromAnnotation(
                element,
                KGeneratorInputParam.class,
                "type"
            );

            collectedForClass.add(new Param(param.name(), paramType));

        }

        return collected;

    }

    private Map<String, Set<Param>> collectOutputs(
        final RoundEnvironment roundEnv
    ) {

        Map<String, Set<Param>> collected = new HashMap<>();

        for (var element: roundEnv.getElementsAnnotatedWith(KGeneratorOutputParams.class)) {

            Element enclosing = element.getEnclosingElement();
            if (enclosing.getKind() != ElementKind.CLASS) {
                continue;
            }

            TypeElement enclosingType = (TypeElement) enclosing;
            String enclosingSimpleName = enclosingType.getSimpleName().toString();
            if (!collected.containsKey(enclosingSimpleName)) {
                collected.put(enclosingSimpleName, new HashSet<>());
            }

            Set<Param> collectedForClass = collected.get(enclosingSimpleName);

            KGeneratorOutputParam[] params = Objects.requireNonNull(
                element.getAnnotationsByType(KGeneratorOutputParam.class)
            );
            for (var param: params) {
                TypeMirror paramType = null;
                try {
                    var clazz = param.type();
                } catch (MirroredTypeException e) {
                    paramType = e.getTypeMirror();
                }
                Objects.requireNonNull(paramType);

                collectedForClass.add(new Param(param.name(), paramType));
            }

        }

        for (var element: roundEnv.getElementsAnnotatedWith(KGeneratorOutputParam.class)) {

            Element enclosing = element.getEnclosingElement();
            if (enclosing.getKind() != ElementKind.CLASS) {
                continue;
            }

            if (element.getAnnotation(KGeneratorOutputParams.class) != null) {
                continue;
            }

            TypeElement enclosingType = (TypeElement) enclosing;
            String enclosingSimpleName = enclosingType.getSimpleName().toString();
            if (!collected.containsKey(enclosingSimpleName)) {
                collected.put(enclosingSimpleName, new HashSet<>());
            }

            Set<Param> collectedForClass = collected.get(enclosingSimpleName);

            KGeneratorOutputParam param = Objects.requireNonNull(
                element.getAnnotation(KGeneratorOutputParam.class)
            );
            TypeMirror paramType = this.getClassValueFromAnnotation(
                element,
                KGeneratorOutputParam.class,
                "type"
            );

            collectedForClass.add(new Param(param.name(), paramType));

        }

        return collected;

    }

    private void brewJava(
        final Map<String, Set<Param>> params,
        final String paramTypeQualifier
    ) {

        ParameterizedTypeName validatorInterface = ParameterizedTypeName.get(
            VALIDATOR_CLASS_NAME,
            UNIVERSAL_MAP_CLASS_NAME
        );

        for (var entry: params.entrySet()) {
            String nodeTypeName = entry.getKey();
            Set<Param> nodeParams = entry.getValue();

            TypeSpec validator = TypeSpec
                .classBuilder(
                    String.format(
                        "%s$$%sValidator",
                        nodeTypeName,
                        paramTypeQualifier
                    )
                )
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addSuperinterface(validatorInterface)
                .addAnnotation(KGenerated.class)
                .addMethod(this.brewValidateMethod(nodeParams, paramTypeQualifier))
                .build();

            JavaFile javaFile = JavaFile.builder(
                "konna.generated.level.generator",
                validator
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
                        "Could not generate factory class: %s",
                        e
                    )
                );
            }
        }

    }

    private MethodSpec brewValidateMethod(
        final Set<Param> params,
        final String paramTypeQualifier
    ) {
        var builder = MethodSpec
            .methodBuilder("validate")
            .addModifiers(Modifier.PUBLIC)
            .addAnnotation(Override.class)
            .addParameter(
                ParameterSpec
                    .builder(UNIVERSAL_MAP_CLASS_NAME, "value")
                    .addAnnotation(NonNull.class)
                    .build()
            )
            .returns(TypeName.VOID);

        int i = 0;
        for (Param param: params) {

            builder
                .beginControlFlow("if (!value.containsKey($S))", param.name)
                .addStatement(
                    "throw new $T($S)",
                    INVALID_ARG_EXCEPTION_CLASS_NAME,
                    String.format(
                        "Parameter %s is not presented in %s parameters!",
                        param.name,
                        paramTypeQualifier.toLowerCase()
                    )
                )
                .endControlFlow()
                .addStatement(
                    String.format("var p%d = value.getSafe($S, $T.class)", i),
                    param.name,
                    param.type
                )
                .beginControlFlow(String.format("if (p%d == null)", i))
                .addStatement("var real = value.get($S)", param.name)
                .addStatement(
                    "throw new $T(String.format($S, real.getClass()))",
                    INVALID_ARG_EXCEPTION_CLASS_NAME,
                    String.format(
                        "%s parameter type mismatch for %s. Expected %s but got",
                        paramTypeQualifier,
                        param.name,
                        param.type
                    ) + " %s"
                )
                .endControlFlow();
            i++;
        }

        return builder.build();
    }


}
