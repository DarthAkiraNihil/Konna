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

package io.github.darthakiranihil.konna.core.data.json;

import java.util.LinkedList;
import java.util.List;

/**
 * Convenience class to create {@link KJsonArrayValidator} using fluent
 * syntax instead of manual constructor call.
 *
 * @since 0.4.0
 * @author Darth Akira Nihil
 */
public final class KJsonArrayValidatorBuilder {

    private final List<KJsonValidator> validators;
    private final KJsonValueType elementType;

    /**
     * Creates a json array validator builder with desired element type.
     * @param elementType Expected type of array elements
     * @return A new builder
     */
    public static KJsonArrayValidatorBuilder create(final KJsonValueType elementType) {
        return new KJsonArrayValidatorBuilder(elementType);
    }

    /**
     * Creates a json array validator with desired element type.
     * May be useful when validation is only about expected type without extra steps.
     * @param elementType Expected type of array elements
     * @return A new array validator
     */
    public static KJsonValidator createAndBuild(final KJsonValueType elementType) {
        return KJsonArrayValidatorBuilder.create(elementType).build();
    }

    private KJsonArrayValidatorBuilder(final KJsonValueType elementType) {
        this.validators = new LinkedList<>();
        this.elementType = elementType;
    }

    /**
     * Adds an element validator to building json array validator.
     * @param validator Element validator to add
     * @return This builder
     */
    public KJsonArrayValidatorBuilder withValidator(final KJsonValidator validator) {
        this.validators.add(validator);
        return this;
    }

    /**
     * Builds the validator.
     * @return The built validator.
     */
    public KJsonValidator build() {
        return new KJsonArrayValidator(
            elementType,
            validators.toArray(new KJsonValidator[0])
        );
    }

}
