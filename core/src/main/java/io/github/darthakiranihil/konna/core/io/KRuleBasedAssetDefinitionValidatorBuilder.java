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

import java.util.LinkedList;
import java.util.List;

public final class KRuleBasedAssetDefinitionValidatorBuilder {

    private final List<KAssetDefinitionRule> rules;

    public static KRuleBasedAssetDefinitionValidatorBuilder create() {
        return new KRuleBasedAssetDefinitionValidatorBuilder();
    }

    private KRuleBasedAssetDefinitionValidatorBuilder() {
        this.rules = new LinkedList<>();
    }

    public KRuleBasedAssetDefinitionValidatorBuilder withInt(final String property) {
        this.rules.add(KAssetDefinitionRule.hasInt(property));
        return this;
    }

    public KRuleBasedAssetDefinitionValidatorBuilder withFloat(final String property) {
        this.rules.add(KAssetDefinitionRule.hasFloat(property));
        return this;
    }

    public KRuleBasedAssetDefinitionValidatorBuilder withBoolean(final String property) {
        this.rules.add(KAssetDefinitionRule.hasBoolean(property));
        return this;
    }

    public KRuleBasedAssetDefinitionValidatorBuilder withString(final String property) {
        this.rules.add(KAssetDefinitionRule.hasString(property));
        return this;
    }

    public KRuleBasedAssetDefinitionValidatorBuilder withNotNullString(final String property) {
        this.rules.add(KAssetDefinitionRule.hasNonNullString(property));
        return this;
    }

    public <T extends Enum<T>> KRuleBasedAssetDefinitionValidatorBuilder withEnum(final String property, final Class<T> enumClass) {
        this.rules.add(KAssetDefinitionRule.hasEnum(property, enumClass));
        return this;
    }

    public KRuleBasedAssetDefinitionValidatorBuilder withSubdefinition(final String property) {
        this.rules.add(KAssetDefinitionRule.hasSubdefinition(property));
        return this;
    }

    public KRuleBasedAssetDefinitionValidatorBuilder withValidatedSubdefinition(final String property, final KAssetDefinitionValidator validator) {
        this.rules.add(KAssetDefinitionRule.hasSubdefinition(property, validator));
        return this;
    }

    public KRuleBasedAssetDefinitionValidatorBuilder withIntArray(final String property) {
        this.rules.add(KAssetDefinitionRule.hasIntArray(property));
        return this;
    }

    public KRuleBasedAssetDefinitionValidatorBuilder withFloatArray(final String property) {
        this.rules.add(KAssetDefinitionRule.hasFloatArray(property));
        return this;
    }

    public KRuleBasedAssetDefinitionValidatorBuilder withBooleanArray(final String property) {
        this.rules.add(KAssetDefinitionRule.hasBooleanArray(property));
        return this;
    }

    public KRuleBasedAssetDefinitionValidatorBuilder withStringArray(final String property) {
        this.rules.add(KAssetDefinitionRule.hasStringArray(property));
        return this;
    }

    public KRuleBasedAssetDefinitionValidatorBuilder withSubdefinitionArray(final String property) {
        this.rules.add(KAssetDefinitionRule.hasSubdefinitionArray(property));
        return this;
    }

    public KRuleBasedAssetDefinitionValidatorBuilder withValidatedSubdefinitionArray(final String property, final KAssetDefinitionValidator elementValidator) {
        this.rules.add(KAssetDefinitionRule.hasSubdefinitionArray(property, elementValidator));
        return this;
    }

    public KRuleBasedAssetDefinitionValidatorBuilder withRule(final KAssetDefinitionRule rule) {
        this.rules.add(rule);
        return this;
    }

    public KAssetDefinitionValidator build() {
        return new KRuleBasedAssetDefinitionValidator(
            this.rules
        );
    }
}
