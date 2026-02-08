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

@FunctionalInterface
public interface KAssetDefinitionRule extends KAssetDefinitionValidator {

    static KAssetDefinitionRule hasInt(final String property) {
        return (v) -> {
            if (v.hasInt(property)) {
                return;
            }

            throw KAssetDefinitionError.propertyNotFound(property);
        };
    }

    static KAssetDefinitionRule hasFloat(final String property) {
        return (v) -> {
            if (v.hasFloat(property)) {
                return;
            }

            throw KAssetDefinitionError.propertyNotFound(property);
        };
    }

    static KAssetDefinitionRule hasBoolean(final String property) {
        return (v) -> {
            if (v.hasBoolean(property)) {
                return;
            }

            throw KAssetDefinitionError.propertyNotFound(property);
        };
    }

    static KAssetDefinitionRule hasString(final String property) {
        return (v) -> {
            if (v.hasString(property)) {
                return;
            }

            throw KAssetDefinitionError.propertyNotFound(property);
        };
    }

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

    static <T extends Enum<T>> KAssetDefinitionRule hasEnum(final String property, final Class<T> enumClass) {
        return (v) -> {
            if (v.hasEnum(property, enumClass)) {
                return;
            }

            throw KAssetDefinitionError.propertyNotFound(property);
        };
    }

    static KAssetDefinitionRule hasSubdefinition(final String property) {
        return (v) -> {
            if (v.hasSubdefinition(property)) {
                return;
            }

            throw KAssetDefinitionError.propertyNotFound(property);
        };
    }

    static KAssetDefinitionRule hasSubdefinition(final String property, final KAssetDefinitionValidator validator) {
        return (v) -> {
            if (!v.hasSubdefinition(property)) {
                throw KAssetDefinitionError.propertyNotFound(property);
            }

            KAssetDefinition sub = v.getSubdefinition(property);
            validator.validate(sub);
        };
    }

    static KAssetDefinitionRule hasIntArray(final String property) {
        return (v) -> {
            if (v.hasIntArray(property)) {
                return;
            }

            throw KAssetDefinitionError.propertyNotFound(property);
        };
    }

    static KAssetDefinitionRule hasFloatArray(final String property) {
        return (v) -> {
            if (v.hasFloatArray(property)) {
                return;
            }

            throw KAssetDefinitionError.propertyNotFound(property);
        };
    }

    static KAssetDefinitionRule hasBooleanArray(final String property) {
        return (v) -> {
            if (v.hasBooleanArray(property)) {
                return;
            }

            throw KAssetDefinitionError.propertyNotFound(property);
        };
    }

    static KAssetDefinitionRule hasStringArray(final String property) {
        return (v) -> {
            if (v.hasStringArray(property)) {
                return;
            }

            throw KAssetDefinitionError.propertyNotFound(property);
        };
    }

    static KAssetDefinitionRule hasSubdefinitionArray(final String property) {
        return (v) -> {
            if (v.hasSubdefinitionArray(property)) {
                return;
            }

            throw KAssetDefinitionError.propertyNotFound(property);
        };
    }

    static KAssetDefinitionRule hasSubdefinitionArray(final String property, final KAssetDefinitionValidator elementValidator) {
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

}
