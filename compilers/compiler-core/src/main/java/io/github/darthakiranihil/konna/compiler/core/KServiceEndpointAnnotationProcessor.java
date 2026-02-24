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
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.engine.KComponentServiceMetaInfo;
import io.github.darthakiranihil.konna.core.engine.KServiceEndpoint;
import io.github.darthakiranihil.konna.core.message.KBodyValue;
import io.github.darthakiranihil.konna.core.message.KMessageItself;
import io.github.darthakiranihil.konna.core.util.KBaseAnnotationProcessor;
import io.github.darthakiranihil.konna.core.util.KGenerated;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * <p>
 * Annotation processor that handles {@link KServiceEndpoint}s and generate corresponding
 * message-to-endpoint converters (message body to array of object args).
 * </p>
 * <p>
 * Additionally, it validates occurrance of the endpoint method. The processor will fail
 * if the endpoint is located in a non-service class
 * (not annotated with {@link KComponentServiceMetaInfo}) or its parameters are not annotated
 * with {@link KBodyValue} or {@link KInject}.
 * </p>
 * <p>
 * After endpoint validation it generates the converter to
 * {@code <package of service>.generated.<service name>$$EndpointConverter_<endpointRoute>}.
 * </p>
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes({
    "io.github.darthakiranihil.konna.core.engine.KServiceEndpoint"
})
@SupportedSourceVersion(SourceVersion.RELEASE_21)
@SuppressWarnings("unused")
public final class KServiceEndpointAnnotationProcessor extends KBaseAnnotationProcessor {

    private record EndpointValidationData(
        List<KBodyValue> params,
        boolean isItself
    ) {

    }

    private static final ClassName MESSAGE_CLASS = ClassName.get(
        "io.github.darthakiranihil.konna.core.message",
        "KMessage"
    );

    private static final ClassName INVALID_MESSAGE_EXCEPTION_CLASS = ClassName.get(
        "io.github.darthakiranihil.konna.core.message.except",
        "KInvalidMessageException"
    );

    @Override
    public boolean process(
        final Set<? extends TypeElement> annotations,
        final RoundEnvironment roundEnv
    ) {

        for (Element element : roundEnv.getElementsAnnotatedWith(
            KServiceEndpoint.class
        )) {

            if (element.getKind() != ElementKind.METHOD) {
                continue;
            }

            ExecutableElement endpoint = (ExecutableElement) element;
            String route = Objects.requireNonNull(
                endpoint.getAnnotation(KServiceEndpoint.class)
            ).route();

            String[] serviceData = this.validateEndpointEnclosingClass(endpoint);
            if (serviceData == null) {
                continue;
            }

            String service = serviceData[0];
            String servicePackage = serviceData[1];

            EndpointValidationData data = this.validateEndpointParams(endpoint);
            if (data == null) {
                continue;
            }


            this.brewJava(
                servicePackage,
                service,
                route,
                data
            );

        }

        return true;
    }

    private @Nullable String[] validateEndpointEnclosingClass(
        final ExecutableElement endpoint
    ) {

        TypeElement enclosedType = (TypeElement) endpoint.getEnclosingElement();
        KComponentServiceMetaInfo metaInfo = enclosedType
            .getAnnotation(KComponentServiceMetaInfo.class);
        if (metaInfo == null) {
            this.messager.printMessage(
                Diagnostic.Kind.ERROR,
                String.format(
                    "%s.%s: %s",
                    enclosedType.getQualifiedName(),
                    endpoint.getSimpleName(),
                        "endpoint method does not belong to a component service"
                    +   "(that must be annotated with KComponentServiceMetaInfo)."
                )
            );
            return null;
        }

        return new String[] {
            metaInfo.name(),
            this
                .elementUtils
                .getPackageOf(enclosedType)
                .getQualifiedName()
                .toString()
        };

    }

    private @Nullable EndpointValidationData validateEndpointParams(
        final ExecutableElement endpoint
    ) {

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

        var itselves = parameters
            .stream()
            .filter(p -> p.getAnnotation(KMessageItself.class) != null)
            .count();

        if (itselves > 1) {
            this.messager.printMessage(
                Diagnostic.Kind.ERROR,
                String.format(
                    "%s: %s",
                    endpoint,
                    "There can be only one endpoint argument, that is the message itself"
                )
            );
            return null;
        }

        if (injected + bodyParams.size() + itselves < parameters.size()) {
            this.messager.printMessage(
                Diagnostic.Kind.ERROR,
                String.format(
                    "%s: %s",
                    endpoint,
                        "Some of endpoint parameters cannot be resolved"
                    +   "(no KBodyParam nor KInject is provided)"
                )
            );
            return null;
        }

        return new EndpointValidationData(bodyParams, itselves > 0);
    }

    private void brewJava(
        final String toPackage,
        final String service,
        final String route,
        final EndpointValidationData data
    ) {

        TypeSpec converter = TypeSpec
            .classBuilder(
                String.format(
                    "%s$$EndpointConverter_%s",
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
            .addAnnotation(KGenerated.class)
            .addMethod(
                this.brewConvertMethod(data)
            )
            .build();

        JavaFile javaFile = JavaFile.builder(
                String.format(
                    "%s.generated",
                    toPackage
                ),
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
                    "Could not generate converter class for %s: %s",
                    route,
                    e
                )
            );
        }


    }

    private MethodSpec brewConvertMethod(
        final EndpointValidationData data
    ) {

        var builder = MethodSpec
            .methodBuilder("convert")
            .addModifiers(Modifier.PUBLIC)
            .addAnnotation(Override.class)
            .returns(Object[].class)
            .addParameter(
                ParameterSpec
                    .builder(MESSAGE_CLASS, "message")
                    .addModifiers(Modifier.FINAL)
                    .addAnnotation(NonNull.class)
                    .build()
            );

        if (data.isItself) {
            return builder
                .addStatement("return new $T[] { message }", Object.class)
                .build();
        }

        List<KBodyValue> params = data.params;
        builder.addStatement("var body = message.body()");
        builder.addStatement("Object[] args = new Object[$L]", params.size());

        for (int i = 0; i < params.size(); i++) {
            var param = params.get(i);
            String paramValue = param.value();

            builder
                .beginControlFlow(
                    "if (!body.containsKey($S))",
                    paramValue
                )
                .addStatement(
                    "throw new $T($S)",
                    INVALID_MESSAGE_EXCEPTION_CLASS,
                    String.format(
                        "Could not get %s from the message",
                        paramValue
                    )
                )
                .endControlFlow()
                .addStatement("args[$L] = body.get($S)", i, paramValue);
        }

        builder.addStatement("return args");

        return builder.build();

    }

}
