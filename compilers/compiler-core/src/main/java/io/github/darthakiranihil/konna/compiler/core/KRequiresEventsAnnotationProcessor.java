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
import io.github.darthakiranihil.konna.core.message.KRequiresEvent;
import io.github.darthakiranihil.konna.core.message.KRequiresEvents;
import io.github.darthakiranihil.konna.core.util.KBaseAnnotationProcessor;
import io.github.darthakiranihil.konna.core.util.KGenerated;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Annotation processor that generates event registers for all classes
 * that provided {@link KRequiresEvents}. Pay attention to the fact that
 * it won't be succeeded if there is an event conflict (same name but different arg type).
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes({
    "io.github.darthakiranihil.konna.core.message.KRequiresEvents"
})
@SupportedSourceVersion(SourceVersion.RELEASE_21)
@SuppressWarnings("unused")
public final class KRequiresEventsAnnotationProcessor extends KBaseAnnotationProcessor {

    private static int generatedRegisterers = 0;

    private record RequiresEventData(
        String name,
        boolean simple,
        TypeMirror type
    ) {

    }

    private static final ClassName EVENT_SYSTEM_CLASS = ClassName.get(
        "io.github.darthakiranihil.konna.core.message",
        "KEventSystem"
    );

    @Override
    public boolean process(
        final Set<? extends TypeElement> annotations,
        final RoundEnvironment roundEnv
    ) {

        Map<String, RequiresEventData> events = new HashMap<>();
        boolean hasError = false;

        for (Element element : roundEnv.getElementsAnnotatedWith(
            KRequiresEvents.class
        )) {

            if (element.getKind() != ElementKind.CLASS) {
                continue;
            }

            TypeElement classElement = (TypeElement) element;
            KRequiresEvents requirements = Objects.requireNonNull(
                classElement.getAnnotation(KRequiresEvents.class)
            );

            for (var requirement: requirements.value()) {

                String name = requirement.name();
                RequiresEventData d = new RequiresEventData(
                    requirement.name(),
                    requirement.simple(),
                    Objects.requireNonNull(this.getType(requirement))
                );
                if (!events.containsKey(name)) {
                    if (d.simple() && !Objects.equals(
                        d.type().toString(),
                        KRequiresEvent.class.getCanonicalName()
                    )) {
                        this.messager.printMessage(
                            Diagnostic.Kind.WARNING,
                            String.format(
                                "%s: event is marked as simple so type argument will be ignored",
                                name
                            )
                        );
                    }
                    events.put(name, d);
                    continue;
                }

                RequiresEventData req1 = events.get(name);
                if (
                        req1.simple() != requirement.simple()
                    ||  !Objects.equals(req1.type(), d.type())) {
                    this.messager.printMessage(
                        Diagnostic.Kind.ERROR,
                        String.format(
                                "Event conflict for %s: it requires event %s,"
                            +   "which is registered, but it does not match with argument type",
                            classElement.getQualifiedName(),
                            requirement.name()
                        )
                    );
                    hasError = true;
                    break;
                }

            }

            if (!hasError) {
                this.brewJava(events);
            }

        }

        return false;
    }

    private void brewJava(final Map<String, RequiresEventData> events) {

        TypeSpec converter = TypeSpec
            .classBuilder(
                String.format(
                    "kGeneratedEventRegisterer_%d",
                    generatedRegisterers
                )
            )
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            .addSuperinterface(
                ClassName.get(
                    "io.github.darthakiranihil.konna.core.message",
                    "KEventRegisterer"
                )
            )
            .addAnnotation(KGenerated.class)
            .addMethod(
                this.brewRegisterMethod(events)
            )
            .build();

        generatedRegisterers++;

        JavaFile javaFile = JavaFile.builder(
                "konna.generated",
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
                    "Could not generate registerer class: %s",
                    e
                )
            );
        }

    }

    private MethodSpec brewRegisterMethod(final Map<String, RequiresEventData> events) {

        var builder = MethodSpec
            .methodBuilder("registerEvents")
            .addModifiers(Modifier.PUBLIC)
            .addAnnotation(Override.class)
            .returns(void.class)
            .addParameter(
                ParameterSpec
                    .builder(EVENT_SYSTEM_CLASS, "eventSystem")
                    .addModifiers(Modifier.FINAL)
                    .addAnnotation(NonNull.class)
                    .build()
            );

        for (var event: events.values()) {
            if (event.simple()) {
                builder.addStatement(
                    "eventSystem.registerSimpleEvent(new $T($S))",
                    ClassName.get(
                        "io.github.darthakiranihil.konna.core.message",
                        "KSimpleEvent"
                    ),
                    event.name()
                );
            } else {
                builder.addStatement(
                    "eventSystem.registerEvent(new $T<$T>($S))",
                    ClassName.get(
                        "io.github.darthakiranihil.konna.core.message",
                        "KEvent"
                    ),
                    ClassName.get(
                        event.type
                    ),
                    event.name()
                );
            }
        }

        return builder.build();

    }

    private @Nullable TypeMirror getType(KRequiresEvent event) {
        try {
            event.type();
            return null;
        } catch (MirroredTypeException e) {
            return e.getTypeMirror();
        }
    }

}
