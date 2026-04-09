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

package io.github.darthakiranihil.konna.core.util;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

public final class KAnnotationUtils {

    /**
     * Returns a type mirror for value of annotation, that returns {@link Class} instances.
     * @param element Element containing annotation
     * @param annotation Annotation to extract class field from
     * @param field Name of field that returns {@link Class}
     * @return Type mirror for annotation field returning {@link Class}
     */
    @SuppressWarnings("SameParameterValue")
    public static TypeMirror getClassValueFromAnnotation(
        final Element element,
        final Class<? extends Annotation> annotation,
        final String field
    ) {
        for (AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
            if (!annotationMirror
                .getAnnotationType()
                .asElement()
                .toString()
                .contentEquals(annotation.getName())
            ) {
                continue;
            }

            for (var entry : annotationMirror
                .getElementValues()
                .entrySet()
            ) {
                if (!entry.getKey().getSimpleName().contentEquals(field)) {
                    continue;
                }

                return (TypeMirror) entry.getValue().getValue();
            }

        }

        throw new IllegalArgumentException(
            String.format(
                "Unknown annotation field: %s",
                field
            )
        );
    }

    @SuppressWarnings("unchecked")
    public static TypeMirror[] getAnnotationArrayValue(
        final Element element,
        final Class<? extends Annotation> annotation,
        final String field
    ) {
        AnnotationMirror mirror = element.getAnnotationMirrors().stream()
            .filter(m -> m
                .getAnnotationType()
                .toString()
                .equals(annotation.getName())
            )
            .findFirst()
            .orElseThrow();

        AnnotationValue arrayValue = mirror.getElementValues().entrySet().stream()
            .filter(entry -> entry
                .getKey()
                .getSimpleName()
                .toString()
                .equals(field)
            )
            .map(Map.Entry::getValue)
            .findFirst()
            .orElse(null);

        if (arrayValue == null) {
            throw new IllegalArgumentException(
                String.format("Unknown annotation field: %s", field)
            );
        }

        List<? extends AnnotationValue>
            classValues = (List<? extends AnnotationValue>) arrayValue.getValue();
        TypeMirror[] result = new TypeMirror[classValues.size()];

        for (int i = 0; i < result.length; i++) {
            result[i] = (TypeMirror) classValues.get(i).getValue();
        }

        return result;
    }

}
