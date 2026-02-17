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

/**
 * Helper class to build {@link KCompositeAssetDefinitionRule} objects with
 * a fluent interface.
 *
 * @since 0.4.0
 * @author Darth Akira Nihil
 */
public final class KCompositeAssetDefinitionRuleBuilder {

    private final List<KAssetDefinitionRule> rules;

    /**
     * @return A new builder
     */
    public static KCompositeAssetDefinitionRuleBuilder create() {
        return new KCompositeAssetDefinitionRuleBuilder();
    }

    private KCompositeAssetDefinitionRuleBuilder() {
        this.rules = new LinkedList<>();
    }

    /**
     * Adds a {@link KAssetDefinitionRule#hasInt(String)} rule to the composite.
     * @param property Name of checked property
     * @return This builder
     */
    public KCompositeAssetDefinitionRuleBuilder withInt(final String property) {
        this.rules.add(KAssetDefinitionRule.hasInt(property));
        return this;
    }

    /**
     * Adds a {@link KAssetDefinitionRule#hasFloat(String)} (String)} rule to the composite.
     * @param property Name of checked property
     * @return This builder
     */
    public KCompositeAssetDefinitionRuleBuilder withFloat(final String property) {
        this.rules.add(KAssetDefinitionRule.hasFloat(property));
        return this;
    }

    /**
     * Adds a {@link KAssetDefinitionRule#hasBoolean(String)} rule to the composite.
     * @param property Name of checked property
     * @return This builder
     */
    public KCompositeAssetDefinitionRuleBuilder withBoolean(final String property) {
        this.rules.add(KAssetDefinitionRule.hasBoolean(property));
        return this;
    }

    /**
     * Adds a {@link KAssetDefinitionRule#hasString(String)} rule to the composite.
     * @param property Name of checked property
     * @return This builder
     */
    public KCompositeAssetDefinitionRuleBuilder withString(final String property) {
        this.rules.add(KAssetDefinitionRule.hasString(property));
        return this;
    }

    /**
     * Adds a {@link KAssetDefinitionRule#hasNonNullString(String)} rule to the composite.
     * @param property Name of checked property
     * @return This builder
     */
    public KCompositeAssetDefinitionRuleBuilder withNotNullString(final String property) {
        this.rules.add(KAssetDefinitionRule.hasNonNullString(property));
        return this;
    }

    /**
     * Adds a {@link KAssetDefinitionRule#hasEnum(String, Class)} rule to the composite.
     * @param property Name of checked property
     * @param enumClass Enum that should be contained by checked property
     * @param <T> Enum type parameter
     * @return This builder
     */
    public <T extends Enum<T>> KCompositeAssetDefinitionRuleBuilder withEnum(
        final String property,
        final Class<T> enumClass
    ) {
        this.rules.add(KAssetDefinitionRule.hasEnum(property, enumClass));
        return this;
    }

    /**
     * Adds a {@link KAssetDefinitionRule#hasSubdefinition(String)} rule to the composite.
     * @param property Name of checked property
     * @return This builder
     */
    public KCompositeAssetDefinitionRuleBuilder withSubdefinition(final String property) {
        this.rules.add(KAssetDefinitionRule.hasSubdefinition(property));
        return this;
    }

    /**
     * Adds a {@link KAssetDefinitionRule#hasSubdefinition(String, KAssetDefinitionRule)}
     * rule to the composite.
     * @param property Name of checked property
     * @param validator Validator for checked subdefinition
     * @return This builder
     */
    public KCompositeAssetDefinitionRuleBuilder withValidatedSubdefinition(
        final String property,
        final KAssetDefinitionRule validator
    ) {
        this.rules.add(KAssetDefinitionRule.hasSubdefinition(property, validator));
        return this;
    }

    /**
     * Adds a {@link KAssetDefinitionRule#hasIntArray(String)} rule to the composite.
     * @param property Name of checked property
     * @return This builder
     */
    public KCompositeAssetDefinitionRuleBuilder withIntArray(final String property) {
        this.rules.add(KAssetDefinitionRule.hasIntArray(property));
        return this;
    }

    /**
     * Adds a {@link KAssetDefinitionRule#hasFloatArray(String)} rule to the composite.
     * @param property Name of checked property
     * @return This builder
     */
    public KCompositeAssetDefinitionRuleBuilder withFloatArray(final String property) {
        this.rules.add(KAssetDefinitionRule.hasFloatArray(property));
        return this;
    }

    /**
     * Adds a {@link KAssetDefinitionRule#hasBooleanArray(String)} rule to the composite.
     * @param property Name of checked property
     * @return This builder
     */
    public KCompositeAssetDefinitionRuleBuilder withBooleanArray(final String property) {
        this.rules.add(KAssetDefinitionRule.hasBooleanArray(property));
        return this;
    }

    /**
     * Adds a {@link KAssetDefinitionRule#hasStringArray(String)} rule to the composite.
     * @param property Name of checked property
     * @return This builder
     */
    public KCompositeAssetDefinitionRuleBuilder withStringArray(final String property) {
        this.rules.add(KAssetDefinitionRule.hasStringArray(property));
        return this;
    }

    /**
     * Adds a {@link KAssetDefinitionRule#hasSubdefinitionArray(String)} rule to the composite.
     * @param property Name of checked property
     * @return This builder
     */
    public KCompositeAssetDefinitionRuleBuilder withSubdefinitionArray(final String property) {
        this.rules.add(KAssetDefinitionRule.hasSubdefinitionArray(property));
        return this;
    }

    /**
     * Adds a {@link KAssetDefinitionRule#hasSubdefinitionArray(String, KAssetDefinitionRule)}
     * rule to the composite.
     * @param property Name of checked property
     * @param elementValidator Validator for array elements
     * @return This builder
     */
    public KCompositeAssetDefinitionRuleBuilder withValidatedSubdefinitionArray(
        final String property,
        final KAssetDefinitionRule elementValidator
    ) {
        this.rules.add(KAssetDefinitionRule.hasSubdefinitionArray(property, elementValidator));
        return this;
    }

    /**
     * Adds a custom rule to the composite.
     * @param rule Rule to add to the composite
     * @return This builder
     */
    public KCompositeAssetDefinitionRuleBuilder withRule(final KAssetDefinitionRule rule) {
        this.rules.add(rule);
        return this;
    }

    /**
     * @return Build {@link KCompositeAssetDefinitionRule} with all added rules
     */
    public KAssetDefinitionRule build() {
        return new KCompositeAssetDefinitionRule(
            this.rules
        );
    }
}
