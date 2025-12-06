package io.github.darthakiranihil.konna.core.data.json;

import io.github.darthakiranihil.konna.core.data.json.except.KJsonValidationError;

import java.util.LinkedList;
import java.util.List;

/**
 * Container for a json object's property validation info
 * that is used in {@link io.github.darthakiranihil.konna.core.data.json.std.KJsonObjectValidator}.
 * It is recommended to create objects of this class with {@link Builder}.
 *
 * @param name Name of validated property
 * @param required Required status of validated property
 * @param nullable Nullability of validated property
 * @param expectedType Expected type of validated property
 * @param defaultValue Default value of validated property.
 *                     It is set when property is not presented
 *                     in the object. However, it is ignored
 *                     if property is required
 * @param validators Validators of validated property
 *
 * @see Builder
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 *
 */
public record KJsonPropertyValidationInfo(
    String name,
    boolean required,
    boolean nullable,
    KJsonValueType expectedType,
    Object defaultValue,
    List<KJsonValidator> validators
) {

    /**
     * Convenience class for creating {@link KJsonPropertyValidationInfo}
     * with additional checks that prevent creating invalid validation info
     * (e.g. field is not required and not nullable and no default value is set,
     * or field is unnamed)
     */
    public static final class Builder {

        private String name;
        private boolean required;
        private boolean nullable;
        private KJsonValueType expectedType;
        private Object defaultValue;
        private final List<KJsonValidator> validators;

        public Builder() {
            this.validators = new LinkedList<>();
            this.setDefaults();
        }

        private void setDefaults() {
            this.required = true;
            this.nullable = false;
            this.expectedType = null;
            this.defaultValue = null;
            this.validators.clear();
        }

        /**
         * Sets name of validated property. This method is required to be called.
         * @param propertyName Name of validated property
         * @return This builder
         */
        public Builder withName(final String propertyName) {
            this.name = propertyName;
            return this;
        }

        /**
         * Sets expected type of validated property. This method is required to be called.
         * @param type Expected type of validated property
         * @return This builder
         */
        public Builder withExpectedType(final KJsonValueType type) {
            this.expectedType = type;
            return this;
        }

        /**
         * Sets required status of validated property.
         * @param flag Required status of validated property
         * @return This builder
         */
        public Builder withRequired(boolean flag) {
            this.required = flag;
            return this;
        }

        /**
         * Sets nullability status of validated property.
         * @param flag Nullability status of validated property
         * @return This builder
         */
        public Builder withNullable(boolean flag) {
            this.nullable = flag;
            return this;
        }

        /**
         * Sets default value of validated property.
         * @param value Default value of validated property
         * @return This builder
         */
        public Builder withDefaultValue(final Object value) {
            this.defaultValue = value;
            return this;
        }

        /**
         * Add a validator to validated property. This method does not check if
         * passed validator as been already added.
         * @param validator Added validator
         * @return This builder
         */
        public Builder withValidator(final KJsonValidator validator) {
            this.validators.add(validator);
            return this;
        }

        /**
         * Creates a new builder. Might be useful when you set validation
         * info for nested objects but want to keep method chaining without
         * creating a new builder explicitly.
         *
         * @return A new builder, detached from this
         */
        public Builder createSeparated() {
            return new Builder();
        }

        /**
         * Builds a new json property validation info according to previously
         * set values.
         * It won't allow to build info if there is no name set for the property (or it is empty),
         * nor expected type.
         * If the property is not required, not nullable and no default value is set, the builder
         * also won't allow creating validation info.
         *
         * @return Built {@link KJsonPropertyValidationInfo}
         */
        public KJsonPropertyValidationInfo build() {

            if (this.name == null || this.name.isEmpty()) {
                throw new KJsonValidationError(
                    "Property name cannot be null or empty"
                );
            }

            if (this.expectedType == null) {
                throw new KJsonValidationError(
                    "Property type cannot be null"
                );
            }

            if (!this.required && !this.nullable && this.defaultValue == null) {
                throw new KJsonValidationError(
                    "Cannot set property required without provided default value"
                );
            }

            List<KJsonValidator> copied = new LinkedList<>(this.validators);
            var built = new KJsonPropertyValidationInfo(
                this.name,
                this.required,
                this.nullable,
                this.expectedType,
                this.defaultValue,
                copied
            );
            this.setDefaults();
            return built;
        }

    }

}
