package io.github.darthakiranihil.konna.core.data.json;

import io.github.darthakiranihil.konna.core.data.json.except.KJsonValidationError;

import java.util.LinkedList;
import java.util.List;

public record KJsonPropertyValidationInfo(
    String name,
    boolean required,
    boolean nullable,
    KJsonValueType expectedType,
    Object defaultValue,
    List<KJsonValidator> validators
) {

    public static class Builder {

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
            this.expectedType = KJsonValueType.OBJECT;
            this.defaultValue = null;
            this.validators.clear();
        }

        public Builder withName(final String propertyName) {
            this.name = propertyName;
            return this;
        }

        public Builder withRequired(boolean flag) {
            this.required = flag;
            return this;
        }

        public Builder withNullable(boolean flag) {
            this.nullable = flag;
            return this;
        }

        public Builder withExpectedType(final KJsonValueType type) {
            this.expectedType = type;
            return this;
        }

        public Builder withDefaultValue(final Object value) {
            this.defaultValue = value;
            return this;
        }

        public Builder withValidator(final KJsonValidator validator) {
            this.validators.add(validator);
            return this;
        }

        public Builder createSeparated() {
            return new Builder();
        }

        public KJsonPropertyValidationInfo build() {

            if (this.name == null || this.name.isEmpty()) {
                throw new KJsonValidationError(
                    "Property name cannot be null or empty"
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
