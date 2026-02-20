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

import io.github.darthakiranihil.konna.core.object.KPoolable;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

@SupportedAnnotationTypes({
    "io.github.darthakiranihil.konna.core.object.KPoolable"
})
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public class KPoolableAnnotationProcessor extends AbstractProcessor {

    private Messager messager;
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.messager = processingEnv.getMessager();
        this.filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            // Get all elements annotated with @CustomAnnotation
            for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                // Example: print a warning message for each annotated element
                messager.printMessage(Diagnostic.Kind.WARNING, "Found @CustomAnnotation on " + element.getSimpleName());

                // Here you would add logic to generate code using Filer
            }
        }
        return true; // Claim responsibility for the annotations
    }
}
