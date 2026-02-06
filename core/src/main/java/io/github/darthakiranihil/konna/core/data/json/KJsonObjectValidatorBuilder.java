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

import io.github.darthakiranihil.konna.core.data.json.except.KJsonValidationError;
import org.jspecify.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;

/**
 * Convenience class to create {@link KJsonObjectValidator} using fluent syntax
 * instead of manual construction call (including {@link KJsonFieldValidationInfo}).
 *
 * @since 0.4.0
 * @author Darth Akira Nihil
 */
public final class KJsonObjectValidatorBuilder {

    /**
     * Interface that describes build syntax of {@link KJsonFieldValidationInfo}
     * in order to add it to the building object validator.
     *
     * @since 0.4.0
     * @author Darth Akira Nihil
     */
    public sealed interface KJsonFieldValidationInfoBuildSyntax {

        /**
         * Sets required status of validated field.
         * @param flag Required status of validated field
         * @return This syntax
         */
        KJsonFieldValidationInfoBuildSyntax withRequired(boolean flag);
        /**
         * Sets nullability status of validated field.
         * @param flag Nullability status of validated field
         * @return This syntax
         */
        KJsonFieldValidationInfoBuildSyntax withNullable(boolean flag);
        /**
         * Sets default value of validated field.
         * @param value Default value of validated field
         * @return This syntax
         */
        KJsonFieldValidationInfoBuildSyntax withDefaultValue(Object value);
        /**
         * Add a validator to validated field. This method does not check if
         * passed validator as been already added.
         * @param validator Added validator
         * @return This syntax
         */
        KJsonFieldValidationInfoBuildSyntax withValidator(KJsonValidator validator);
        /**
         * Builds a new json field validation info according to previously
         * set values and adds it to the parent builder.
         * It won't allow to build info if name of the field is empty.
         * If the field is not required, not nullable and no default value is set, the builder
         * also won't allow creating validation info.
         *
         * @return Parent object validator builder
         */
        KJsonObjectValidatorBuilder finishField();

    }

    private static final class FieldValidationInfoBuilder
        implements KJsonFieldValidationInfoBuildSyntax {

        private final String name;
        private final KJsonValueType expectedType;
        private final List<KJsonValidator> validators;

        private boolean required;
        private boolean nullable;
        private @Nullable Object defaultValue;

        private final KJsonObjectValidatorBuilder parent;

        private FieldValidationInfoBuilder(
            final KJsonObjectValidatorBuilder parent,
            final String name,
            final KJsonValueType expectedType
        ) {

            this.parent = parent;

            this.name = name;
            this.expectedType = expectedType;
            this.validators = new LinkedList<>();

            this.required = true;
            this.nullable = false;
            this.defaultValue = null;

        }

        @Override
        public KJsonFieldValidationInfoBuildSyntax withRequired(boolean flag) {
            this.required = flag;
            return this;
        }

        @Override
        public KJsonFieldValidationInfoBuildSyntax withNullable(boolean flag) {
            this.nullable = flag;
            return this;
        }

        @Override
        public KJsonFieldValidationInfoBuildSyntax withDefaultValue(final Object value) {
            this.defaultValue = value;
            return this;
        }

        @Override
        public KJsonFieldValidationInfoBuildSyntax withValidator(final KJsonValidator validator) {
            this.validators.add(validator);
            return this;
        }

        @Override
        public KJsonObjectValidatorBuilder finishField() {

            if (this.name.isEmpty()) {
                throw new KJsonValidationError(
                    "Property name cannot be empty"
                );
            }

            if (!this.required && !this.nullable && this.defaultValue == null) {
                throw new KJsonValidationError(
                    "Cannot set property required without provided default value"
                );
            }

            List<KJsonValidator> copied = new LinkedList<>(this.validators);
            var built = new KJsonFieldValidationInfo(
                this.name,
                this.required,
                this.nullable,
                this.expectedType,
                this.defaultValue,
                copied
            );

            return this.parent.addInfo(built);
        }

    }

    private final List<KJsonFieldValidationInfo> fieldInfos;

    /**
     * @return A new builder.
     */
    public static KJsonObjectValidatorBuilder create() {
        return new KJsonObjectValidatorBuilder();
    }

    private KJsonObjectValidatorBuilder() {
        this.fieldInfos = new LinkedList<>();
    }

    /**
     * Adds a new field to the object validator.
     * @param name Field name
     * @param expectedType Expected field type
     * @return Field validation info builder for added field
     */
    public KJsonFieldValidationInfoBuildSyntax withField(
        final String name,
        final KJsonValueType expectedType
    ) {
        return new KJsonObjectValidatorBuilder.FieldValidationInfoBuilder(
            this, name, expectedType
        );
    }

    /**
     * Adds a new field to the object validator, but immediately finished it
     * (so other options cannot be set).
     * It may be useful if other validation options are required to be default
     * and no extra validators are needed.
     * @param name Field name
     * @param expectedType Expected field type
     * @return This builder
     */
    public KJsonObjectValidatorBuilder withSimpleField(
        final String name,
        final KJsonValueType expectedType
    ) {
        return this.withField(name, expectedType).finishField();
    }

    /**
     * Builds a new {@link KJsonObjectValidator}.
     * @return A new object validator
     */
    public KJsonValidator build() {
        return new KJsonObjectValidator(
            this.fieldInfos.toArray(new KJsonFieldValidationInfo[0])
        );
    }

    private KJsonObjectValidatorBuilder addInfo(
        final KJsonFieldValidationInfo fieldValidationInfo
    ) {
        this.fieldInfos.add(fieldValidationInfo);
        return this;
    }

}
