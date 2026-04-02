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
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeInputParam;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeInputParams;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeOutputParam;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeOutputParams;
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

/**
 * <p>
 *     Annotation processor that generates input and output params validators for generator nodes.
 * </p>
 * <p>
 *     Important note (todo): when there are parameter with same name but different types,
 *     their validation logic will be generated for <i>each unique combination</i>
 *     that may lead to an unexpected behavior (potentially causing level generation errors).
 *     <b>It will be so for some time but will be fixed sometime</b>
 * </p>
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes({
    "io.github.darthakiranihil.konna.level.generator.KGeneratorNodeInputParam",
    "io.github.darthakiranihil.konna.level.generator.KGeneratorNodeInputParams",
    "io.github.darthakiranihil.konna.level.generator.KGeneratorNodeOutputParam",
    "io.github.darthakiranihil.konna.level.generator.KGeneratorNodeOutputParams",
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

        for (var element: roundEnv.getElementsAnnotatedWith(KGeneratorNodeInputParams.class)) {

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

            KGeneratorNodeInputParams params = Objects.requireNonNull(
                element.getAnnotation(KGeneratorNodeInputParams.class)
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
            }

        }

        for (var element: roundEnv.getElementsAnnotatedWith(KGeneratorNodeInputParam.class)) {

            Element enclosing = element.getEnclosingElement();
            if (enclosing.getKind() != ElementKind.CLASS) {
                continue;
            }

            if (element.getAnnotation(KGeneratorNodeInputParams.class) != null) {
                continue;
            }

            TypeElement enclosingType = (TypeElement) enclosing;
            String enclosingSimpleName = enclosingType.getSimpleName().toString();
            if (!collected.containsKey(enclosingSimpleName)) {
                collected.put(enclosingSimpleName, new HashSet<>());
            }

            Set<Param> collectedForClass = collected.get(enclosingSimpleName);

            KGeneratorNodeInputParam param = Objects.requireNonNull(
                element.getAnnotation(KGeneratorNodeInputParam.class)
            );
            TypeMirror paramType = this.getClassValueFromAnnotation(
                element,
                KGeneratorNodeInputParam.class,
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

        for (var element: roundEnv.getElementsAnnotatedWith(KGeneratorNodeOutputParams.class)) {

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

            KGeneratorNodeOutputParam[] params = Objects.requireNonNull(
                element.getAnnotationsByType(KGeneratorNodeOutputParam.class)
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

        for (var element: roundEnv.getElementsAnnotatedWith(KGeneratorNodeOutputParam.class)) {

            Element enclosing = element.getEnclosingElement();
            if (enclosing.getKind() != ElementKind.CLASS) {
                continue;
            }

            if (element.getAnnotation(KGeneratorNodeOutputParams.class) != null) {
                continue;
            }

            TypeElement enclosingType = (TypeElement) enclosing;
            String enclosingSimpleName = enclosingType.getSimpleName().toString();
            if (!collected.containsKey(enclosingSimpleName)) {
                collected.put(enclosingSimpleName, new HashSet<>());
            }

            Set<Param> collectedForClass = collected.get(enclosingSimpleName);

            KGeneratorNodeOutputParam param = Objects.requireNonNull(
                element.getAnnotation(KGeneratorNodeOutputParam.class)
            );
            TypeMirror paramType = this.getClassValueFromAnnotation(
                element,
                KGeneratorNodeOutputParam.class,
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

        var paramsArrayBuilder = CodeBlock
            .builder()
            .add("var paramNames = new $T[] {", String.class);

        var paramsTypesArrayBuilder = CodeBlock
            .builder()
            .add("var paramTypes = new $T[] {", Class.class);

        int i = 0;
        for (Param param: params) {
            paramsArrayBuilder.add("$S", param.name);
            paramsTypesArrayBuilder.add("$T.class", param.type);
            if (i < params.size() - 1) {
                paramsArrayBuilder.add(", ");
                paramsTypesArrayBuilder.add(", ");
            }
            i++;
        }

        paramsArrayBuilder.add("}");
        paramsTypesArrayBuilder.add("}");

        builder
            .addStatement(paramsArrayBuilder.build())
            .addStatement(paramsTypesArrayBuilder.build())
            .beginControlFlow("for (int i = $L; i < $L; i++)", 0, params.size())
            .beginControlFlow("if (!value.containsKey(paramNames[i]))")
            .addStatement(
                CodeBlock
                    .builder()
                    .add("throw new $T(", INVALID_ARG_EXCEPTION_CLASS_NAME)
                    .add(
                        CodeBlock
                            .builder()
                            .add(
                                    "String.format("
                                +   "\"Parameter %s is not presented in %s parameters!\", "
                            )
                            .add("paramNames[i]")
                            .add(")")
                            .build()
                    )
                    .add(")")
                    .build()
            )
            .endControlFlow()
            .addStatement("var param = value.getSafe(paramNames[i], paramTypes[i])")
            .beginControlFlow("if (param == null)")
            .addStatement("var real = value.get(paramNames[i])")
            .addStatement(
                CodeBlock
                    .builder()
                    .add("throw new $T(", INVALID_ARG_EXCEPTION_CLASS_NAME)
                    .add("String.format(\"")
                    .add(paramTypeQualifier)
                    .add(" parameter type mismatch for %s. Expected %s but got %s\", ")
                    .add("paramNames[i], paramTypes[i], real.getClass()")
                    .add("))")
                    .build()
            )
            .endControlFlow()
            .endControlFlow();

        return builder.build();
    }


}
