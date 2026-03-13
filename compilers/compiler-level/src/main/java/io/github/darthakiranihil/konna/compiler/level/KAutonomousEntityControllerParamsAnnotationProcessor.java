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
import io.github.darthakiranihil.konna.level.entity.KAutonomousEntityControllerParam;
import io.github.darthakiranihil.konna.level.entity.KAutonomousEntityControllerParams;
import org.jspecify.annotations.NonNull;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@AutoService(Processor.class)
@SupportedAnnotationTypes({
    "io.github.darthakiranihil.konna.level.entity.KAutonomousEntityControllerParam",
    "io.github.darthakiranihil.konna.level.entity.KAutonomousEntityControllerParams"
})
@SupportedSourceVersion(SourceVersion.RELEASE_21)
@SuppressWarnings("unused")
public final class KAutonomousEntityControllerParamsAnnotationProcessor
    extends KBaseAnnotationProcessor {

    private static final ClassName ASSET_DEFINITION_RULE_CLASS_NAME = ClassName.get(
    "io.github.darthakiranihil.konna.core.io",
    "KAssetDefinitionRule"
    );

    private static final ClassName ASSET_DEFINITION_CLASS_NAME = ClassName.get(
        "io.github.darthakiranihil.konna.core.io",
        "KAssetDefinition"
    );

    private static final ClassName COMPOSITE_RULE_BUILDER_CLASS_NAME = ClassName.get(
        "io.github.darthakiranihil.konna.core.io",
        "KCompositeAssetDefinitionRuleBuilder"
    );

    @Override
    public boolean process(
        final Set<? extends TypeElement> annotations,
        final RoundEnvironment roundEnv
    ) {

        for (Element element : roundEnv.getElementsAnnotatedWith(
            KAutonomousEntityControllerParams.class
        )) {

            if (element.getKind() != ElementKind.CONSTRUCTOR) {
                continue;
            }

            ExecutableElement constructorElement = (ExecutableElement) element;
            KAutonomousEntityControllerParams params = Objects.requireNonNull(
                constructorElement.getAnnotation(KAutonomousEntityControllerParams.class)
            );

            Element enclosing = constructorElement.getEnclosingElement();
            TypeElement enclosingClass = (TypeElement) enclosing;
            Map<String, KAutonomousEntityControllerParam> paramsForThis = new HashMap<>();

            boolean hasError = false;
            for (var param: params.value()) {

                String paramName = param.name();
                if (paramsForThis.containsKey(paramName)) {

                    var putParam = paramsForThis.get(paramName);
                    if (putParam.type() != param.type()) {
                        this.messager.printMessage(
                            Diagnostic.Kind.ERROR,
                            String.format(
                                    "Param conflict for controller %s: it has param %s,"
                                +   "which is already defined,"
                                +   "but it does not match with argument type",
                                enclosingClass.getQualifiedName(),
                                paramName
                            )
                        );
                        hasError = true;
                        continue;
                    }

                }

                boolean doesTypeRequiresQualifier =
                        param.type() == KAutonomousEntityControllerParam.Metatype
                            .CLASS_THAT_EXTENDS
                    ||  param.type() == KAutonomousEntityControllerParam.Metatype
                            .CLASS_THAT_EXTENDS_ARRAY
                    ||  param.type() == KAutonomousEntityControllerParam.Metatype
                            .ENUM;

                if (doesTypeRequiresQualifier &&  param.qualifier().isBlank()) {

                    this.messager.printMessage(
                        Diagnostic.Kind.ERROR,
                        String.format(
                            "%s: param type %s requires a non-blank qualifier, but blank provided",
                            paramName,
                            param.type()
                        )
                    );

                    hasError = true;
                    continue;

                }

                paramsForThis.put(paramName, param);

            }

            if (!hasError && !paramsForThis.isEmpty()) {
                this.brewJava(
                    enclosingClass.getSimpleName().toString(),
                    paramsForThis
                );
            }

        }

        return true;
    }

    private void brewJava(
        final String enclosingClass,
        final Map<String, KAutonomousEntityControllerParam> params
    ) {

        TypeSpec converter = TypeSpec
            .classBuilder(
                String.format(
                    "%s$$ParamValidator",
                    enclosingClass
                )
            )
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            .addSuperinterface(ASSET_DEFINITION_RULE_CLASS_NAME)
            .addAnnotation(KGenerated.class)
            .addMethod(
                this.brewValidateMethod(params)
            )
            .build();

        JavaFile javaFile = JavaFile.builder(
                "konna.generated.level.entity",
                converter
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
                    "Could not generate controller params validator class: %s",
                    e
                )
            );
        }

    }

    private MethodSpec brewValidateMethod(
        final Map<String, KAutonomousEntityControllerParam> params
    ) {

        var builder = MethodSpec
            .methodBuilder("validate")
            .addModifiers(Modifier.PUBLIC)
            .addAnnotation(Override.class)
            .returns(TypeName.VOID)
            .addParameter(
                ParameterSpec
                    .builder(ASSET_DEFINITION_CLASS_NAME, "value")
                    .addModifiers(Modifier.FINAL)
                    .addAnnotation(NonNull.class)
                    .build()
            );

        builder.addStatement(
            "var builder = $T.create()",
            COMPOSITE_RULE_BUILDER_CLASS_NAME
        );



        for (var param: params.values()) {
            switch (param.type()) {
                case INT -> builder.addStatement("builder.withInt($S)", param.name());
                case FLOAT -> builder.addStatement("builder.withFloat($S)", param.name());
                case STRING -> builder.addStatement("builder.withString($S)", param.name());
                case BOOLEAN -> builder.addStatement("builder.withBoolean($S)", param.name());
                case CLASS -> builder.addStatement("builder.withClassObject($S)", param.name());
                case CLASS_THAT_EXTENDS -> builder.addStatement(
                    "builder.withClassObject($S, $T.class)",
                    param.name(),
                    ClassName.get(this.elementUtils.getTypeElement(param.qualifier()))
                );
                case ENUM -> builder.addStatement("builder.withEnum($S, $T.class)", param.name(), ClassName.get(this.elementUtils.getTypeElement(param.qualifier())));
                case SUBDEF -> builder.addStatement("builder.withSubdefinition($S)", param.name());
                case INT_ARRAY -> builder.addStatement("builder.withIntArray($S)", param.name());
                case FLOAT_ARRAY -> builder.addStatement("builder.withFloatArray($S)", param.name());
                case STRING_ARRAY -> builder.addStatement("builder.withStringArray($S)", param.name());
                case BOOLEAN_ARRAY -> builder.addStatement("builder.withBooleanArray($S)", param.name());
                case CLASS_ARRAY -> builder.addStatement("builder.withClassObjectArray($S)", param.name());
                case CLASS_THAT_EXTENDS_ARRAY -> builder.addStatement("builder.withClassObjectArray($S, $T.class)", param.name(), ClassName.get(this.elementUtils.getTypeElement(param.qualifier())));
                case SUBDEF_ARRAY -> builder.addStatement("builder.withSubdefinitionArray($S)", param.name());
            }
        }

        builder.addStatement("builder.build().validate(value)");

        return builder.build();

    }


}
