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

package io.github.darthakiranihil.konna.core.io;

import io.github.darthakiranihil.konna.core.io.except.KAssetDefinitionError;
import io.github.darthakiranihil.konna.core.util.KValidator;

/**
 * Simple functional abstraction over asset definition validator.
 *
 * @since 0.4.0
 * @author Darth Akira Nihil
 */
@FunctionalInterface
public interface KAssetDefinitionRule extends KValidator<KAssetDefinition> {

    /**
     * Creates a rule that checks whether a definition has an int property.
     * @param property Property to be checked
     * @return Created asset definition rule
     */
    static KAssetDefinitionRule hasInt(final String property) {
        return (v) -> {
            if (v.hasInt(property)) {
                return;
            }

            throw KAssetDefinitionError.propertyNotFound(property);
        };
    }

    /**
     * Creates a rule that checks whether a definition has a float property.
     * @param property Property to be checked
     * @return Created asset definition rule
     */
    static KAssetDefinitionRule hasFloat(final String property) {
        return (v) -> {
            if (v.hasFloat(property)) {
                return;
            }

            throw KAssetDefinitionError.propertyNotFound(property);
        };
    }

    /**
     * Creates a rule that checks whether a definition has a boolean property.
     * @param property Property to be checked
     * @return Created asset definition rule
     */
    static KAssetDefinitionRule hasBoolean(final String property) {
        return (v) -> {
            if (v.hasBoolean(property)) {
                return;
            }

            throw KAssetDefinitionError.propertyNotFound(property);
        };
    }

    /**
     * Creates a rule that checks whether a definition has a string property.
     * Created rule will ignore string nullability (i.e. if definition has
     * a string that is null, check will be passed)
     * @param property Property to be checked
     * @return Created asset definition rule
     */
    static KAssetDefinitionRule hasString(final String property) {
        return (v) -> {
            if (v.hasString(property)) {
                return;
            }

            throw KAssetDefinitionError.propertyNotFound(property);
        };
    }

    /**
     * Creates a rule that checks whether a definition has a non-null string property.
     * @param property Property to be checked
     * @return Created asset definition rule
     */
    static KAssetDefinitionRule hasNonNullString(final String property) {
        return (v) -> {
            if (!v.hasString(property)) {
                throw KAssetDefinitionError.propertyNotFound(property);
            }

            String prop = v.getString(property);
            if (prop != null) {
                return;
            }

            throw new KAssetDefinitionError(
                String.format(
                    "String property %s is expected to be non-null, but got null",
                    property
                )
            );
        };
    }

    /**
     * Creates a rule that checks whether a definition has an enum property.
     * @param property Property to be checked
     * @param enumClass Class of enum that checked property must have
     * @param <T> Enum class type parameter
     * @return Created asset definition rule
     */
    static <T extends Enum<T>> KAssetDefinitionRule hasEnum(
        final String property,
        final Class<T> enumClass
    ) {
        return (v) -> {
            if (v.hasEnum(property, enumClass)) {
                return;
            }

            throw KAssetDefinitionError.propertyNotFound(property);
        };
    }

    /**
     * Creates a rule that checks whether a definition has a subdefinition property.
     * @param property Property to be checked
     * @return Created asset definition rule
     */
    static KAssetDefinitionRule hasSubdefinition(final String property) {
        return (v) -> {
            if (v.hasSubdefinition(property)) {
                return;
            }

            throw KAssetDefinitionError.propertyNotFound(property);
        };
    }

    /**
     * Creates a rule that checks whether a definition has a subdefinition
     * property with its extra validation.
     * @param property Property to be checked
     * @param validator Subdefinition validator
     * @return Created asset definition rule
     */
    static KAssetDefinitionRule hasSubdefinition(
        final String property,
        final KAssetDefinitionRule validator
    ) {
        return (v) -> {
            if (!v.hasSubdefinition(property)) {
                throw KAssetDefinitionError.propertyNotFound(property);
            }

            KAssetDefinition sub = v.getSubdefinition(property);
            validator.validate(sub);
        };
    }

    /**
     * Creates a rule that checks whether a definition has an int array property.
     * @param property Property to be checked
     * @return Created asset definition rule
     */
    static KAssetDefinitionRule hasIntArray(final String property) {
        return (v) -> {
            if (v.hasIntArray(property)) {
                return;
            }

            throw KAssetDefinitionError.propertyNotFound(property);
        };
    }

    /**
     * Creates a rule that checks whether a definition has a float array property.
     * @param property Property to be checked
     * @return Created asset definition rule
     */
    static KAssetDefinitionRule hasFloatArray(final String property) {
        return (v) -> {
            if (v.hasFloatArray(property)) {
                return;
            }

            throw KAssetDefinitionError.propertyNotFound(property);
        };
    }

    /**
     * Creates a rule that checks whether a definition has a boolean array property.
     * @param property Property to be checked
     * @return Created asset definition rule
     */
    static KAssetDefinitionRule hasBooleanArray(final String property) {
        return (v) -> {
            if (v.hasBooleanArray(property)) {
                return;
            }

            throw KAssetDefinitionError.propertyNotFound(property);
        };
    }

    /**
     * Creates a rule that checks whether a definition has a string array property.
     * @param property Property to be checked
     * @return Created asset definition rule
     */
    static KAssetDefinitionRule hasStringArray(final String property) {
        return (v) -> {
            if (v.hasStringArray(property)) {
                return;
            }

            throw KAssetDefinitionError.propertyNotFound(property);
        };
    }

    /**
     * Creates a rule that checks whether a definition has a subdefinition array property.
     * @param property Property to be checked
     * @return Created asset definition rule
     */
    static KAssetDefinitionRule hasSubdefinitionArray(final String property) {
        return (v) -> {
            if (v.hasSubdefinitionArray(property)) {
                return;
            }

            throw KAssetDefinitionError.propertyNotFound(property);
        };
    }

    /**
     * Creates a rule that checks whether a definition has a subdefinition array property
     * with validating its elements.
     * @param property Property to be checked
     * @param elementValidator Validator for array elements
     * @return Created asset definition rule
     */
    static KAssetDefinitionRule hasSubdefinitionArray(
        final String property,
        final KAssetDefinitionRule elementValidator
    ) {
        return (v) -> {
            if (!v.hasSubdefinitionArray(property)) {
                throw KAssetDefinitionError.propertyNotFound(property);
            }

            KAssetDefinition[] subs = v.getSubdefinitionArray(property);
            for (KAssetDefinition sub: subs) {
                elementValidator.validate(sub);
            }
        };
    }

    /**
     * Creates a rule that checks whether a definition has a class property.
     * @param property Property to be checked
     * @return Created asset definition rule
     */
    static KAssetDefinitionRule hasClassObject(
        final String property
    ) {
        return (v) -> {
            if (v.hasClassObject(property)) {
                return;
            }

            throw KAssetDefinitionError.propertyNotFound(property);
        };
    }

    /**
     * Creates a rule that checks whether a definition has a class property
     * with assignability check it is in the definition.
     * @param property Property to be checked
     * @param targetClass What class must be assignable to
     * @param <T> Type parameter of target class
     * @return Created asset definition rule
     */
    static <T> KAssetDefinitionRule hasClassObject(
        final String property,
        final Class<T> targetClass
    ) {
        return (v) -> {
            if (v.hasClassObject(property, targetClass)) {
                return;
            }

            throw KAssetDefinitionError.propertyNotFound(property);
        };
    }

    /**
     * Creates a rule that checks whether a definition has a class array property.
     * @param property Property to be checked
     * @return Created asset definition rule
     */
    static KAssetDefinitionRule hasClassObjectArray(
        final String property
    ) {
        return (v) -> {
            if (v.hasClassObjectArray(property)) {
                return;
            }

            throw KAssetDefinitionError.propertyNotFound(property);
        };
    }

    /**
     * Creates a rule that checks whether a definition has a class array property
     * with assignability check it is in the definition.
     * @param property Property to be checked
     * @param targetClass What class must be assignable to
     * @param <T> Type parameter of target class
     * @return Created asset definition rule
     */
    static <T> KAssetDefinitionRule hasClassObjectArray(
        final String property,
        final Class<T> targetClass
    ) {
        return (v) -> {
            if (v.hasClassObjectArray(property, targetClass)) {
                return;
            }

            throw KAssetDefinitionError.propertyNotFound(property);
        };
    }

}
