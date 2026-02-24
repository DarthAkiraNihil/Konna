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
import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.JavaFile;
import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.TypeSpec;
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.engine.Kse2;
import io.github.darthakiranihil.konna.core.message.KBodyValue;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@AutoService(Processor.class)
@SupportedAnnotationTypes({
    "io.github.darthakiranihil.konna.core.engine.Kse2"
})
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public final class KServiceEndpointAnnotationProcessor extends AbstractProcessor {

    private Messager messager;

    private static final ClassName MESSAGE_CLASS = ClassName.get(
        "io.github.darthakiranihil.konna.core.message",
        "KMessage"
    );

    private static final ClassName INVALID_MESSAGE_EXCEPTION_CLASS = ClassName.get(
        "io.github.darthakiranihil.konna.core.message.except",
        "KInvalidMessageException"
    );

    @Override
    public synchronized void init(final ProcessingEnvironment processingEnv) {

        super.init(processingEnv);
        this.messager = processingEnv.getMessager();

    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for (Element element : roundEnv.getElementsAnnotatedWith(
            Kse2.class
        )) {

            if (element.getKind() != ElementKind.METHOD) {
                continue;
            }

            ExecutableElement endpoint = (ExecutableElement) element;
            TypeElement enclosedType = (TypeElement) endpoint.getEnclosingElement();

            var parameters = endpoint.getParameters();
            long injected = parameters
                .stream()
                .filter(p -> p.getAnnotation(KInject.class) != null)
                .count();

            var bodyParams = parameters
                .stream()
                .filter(p -> p.getAnnotation(KBodyValue.class) != null)
                .map(p -> p.getAnnotation(KBodyValue.class))
                .toList();

            if (injected + bodyParams.size() < parameters.size()) {
                this.messager.printMessage(
                    Diagnostic.Kind.ERROR,
                    String.format(
                        "%s: %s",
                        endpoint,
                        "Some of endpoint parameters cannot be resolved (no KBodyParam nor KInject is provided)"
                    )
                );
                continue;
            }

            this.generateConverter(
                enclosedType.getQualifiedName().toString(),
                endpoint.getSimpleName().toString(),
                bodyParams
            );

        }

        return true;
    }

    private void generateConverter(
        final String service,
        final String route,
        final List<KBodyValue> params
    ) {

        var builder = MethodSpec
            .methodBuilder("convert")
            .addAnnotation(Override.class)
            .returns(Object[].class)
            .addParameter(MESSAGE_CLASS, "message", Modifier.FINAL);

        builder.addStatement("var body = message.body()");
        builder.addStatement("Object[] args = new Object[$L]", params.size());

        for (int i = 0; i < params.size(); i++) {
            var param = params.get(i);
            builder
                .beginControlFlow(
                    "if (!body.containsKey($S))",
                    param.value()
                )
                .addStatement(
                    "throw new $T($S)",
                    INVALID_MESSAGE_EXCEPTION_CLASS,
                    String.format(
                        "Could not get %s from the message",
                        param.value()
                    )
                )
                .endControlFlow()
                .addStatement("args[$L] = body.get($S)", i, param.value());
        }

        builder.addStatement("return args");

        TypeSpec converter = TypeSpec
            .classBuilder(
                String.format(
                    "%s.generated.kMessageToEndpointConverted$$%s$$%s",
                    service,
                    service,
                    route
                )
            )
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            .addSuperinterface(
                ClassName.get(
                    "io.github.darthakiranihil.konna.core.engine",
                    "KMessageToEndpointConverter"
                )
            )
            .addMethod(builder.build())
            .build();

        JavaFile javaFile = JavaFile.builder(
            String.format(
                "%s.generated",
                service
            ),
            converter
        )
            .build();

        try {
            javaFile.writeTo(System.out);
        } catch (IOException e) {
            this.messager.printMessage(Diagnostic.Kind.ERROR, "fuck");
        }


    }

}
