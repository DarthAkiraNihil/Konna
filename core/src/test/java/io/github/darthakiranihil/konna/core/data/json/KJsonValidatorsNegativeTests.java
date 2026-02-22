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

import io.github.darthakiranihil.konna.core.data.json.except.KJsonParseException;
import io.github.darthakiranihil.konna.core.data.json.except.KJsonValidationError;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KJsonValidatorsNegativeTests extends KStandardTestClass {

    @Test
    public void testObjectValidatorIncorrectType() {

        String src = "[1, 2, 3]";
        KJsonValidator validator = KJsonObjectValidatorBuilder
            .create()
            .withSimpleField("aboba", KJsonValueType.NUMBER_INT)
            .withSimpleField("biba", KJsonValueType.NUMBER_INT)
            .build();

        try {
            KJsonValue parsed = this.jsonParser.parse(src);
            Assertions.assertThrows(
                KJsonValidationError.class,
                () -> validator.validate(parsed)
            );
        } catch (KJsonParseException e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testObjectValidatorNoRequiredValue() {

        String src = "{\"aboba\": 123}";
        KJsonValidator validator = KJsonObjectValidatorBuilder
            .create()
            .withSimpleField("aboba", KJsonValueType.NUMBER_INT)
            .withSimpleField("biba", KJsonValueType.NUMBER_INT)
            .build();

        try {
            KJsonValue parsed = this.jsonParser.parse(src);
            Assertions.assertThrows(
                KJsonValidationError.class,
                () -> validator.validate(parsed)
            );
        } catch (KJsonParseException e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testObjectValidatorGotNullButNotNullable() {

        String src = "{\"aboba\": null}";
        KJsonValidator validator = KJsonObjectValidatorBuilder
            .create()
            .withSimpleField("aboba", KJsonValueType.NUMBER_INT)
            .build();

        try {
            KJsonValue parsed = this.jsonParser.parse(src);
            Assertions.assertThrows(
                KJsonValidationError.class,
                () -> validator.validate(parsed)
            );
        } catch (KJsonParseException e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testObjectValidatorValueValidationFailed() {

        String src = "{\"aboba\": \"123\"}";
        KJsonValidator validator = KJsonObjectValidatorBuilder
            .create()
            .withField("aboba", KJsonValueType.STRING)
            .withValidator(new KJsonValueIsClassValidator())
            .finishField()
            .withSimpleField("biba", KJsonValueType.NUMBER_INT)
            .build();

        try {
            KJsonValue parsed = this.jsonParser.parse(src);
            Assertions.assertThrows(
                KJsonValidationError.class,
                () -> validator.validate(parsed)
            );
        } catch (KJsonParseException e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testArrayValidatorIncorrectType() {

        String src = "{}";
        KJsonValidator validator = KJsonArrayValidatorBuilder.createAndBuild(KJsonValueType.NUMBER_INT);

        try {
            KJsonValue parsed = this.jsonParser.parse(src);
            Assertions.assertThrows(
                KJsonValidationError.class,
                () -> validator.validate(parsed)
            );
        } catch (KJsonParseException e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testArrayValidatorIncorrectElementType() {

        String src = "[1, 2, 3]";
        KJsonValidator validator = new KJsonArrayValidator(
            KJsonValueType.STRING
        );

        try {
            KJsonValue parsed = this.jsonParser.parse(src);
            Assertions.assertThrows(
                KJsonValidationError.class,
                () -> validator.validate(parsed)
            );
        } catch (KJsonParseException e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testArrayValidatorElementValidationFailed() {

        String src = "[\"1\", \"2\", \"3\"]";
        KJsonValidator validator = new KJsonArrayValidator(
            KJsonValueType.STRING,
            new KJsonValueIsClassValidator()
        );

        try {
            KJsonValue parsed = this.jsonParser.parse(src);
            Assertions.assertThrows(
                KJsonValidationError.class,
                () -> validator.validate(parsed)
            );
        } catch (KJsonParseException e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testValueIsClassValidatorIncorrectType() {

        String src = "[\"1\", \"2\", \"3\"]";
        try {
            KJsonValue parsed = this.jsonParser.parse(src);
            Assertions.assertThrows(
                KJsonValidationError.class,
                () -> new KJsonValueIsClassValidator().validate(parsed)
            );
        } catch (KJsonParseException e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testPropertyBuilderNoName() {

        var builder = KJsonObjectValidatorBuilder
            .create()
            .withField("", KJsonValueType.STRING);

        Throwable thrown = Assertions.assertThrows(
            KJsonValidationError.class, builder::finishField
        );
        Assertions.assertEquals(
            "Property name cannot be empty",
            thrown.getMessage()
        );
    }

    @Test
    public void testPropertyBuilderNoDefaultValue() {

        var builder = KJsonObjectValidatorBuilder
            .create()
            .withField("test", KJsonValueType.NUMBER_INT)
            .withRequired(false);

        Throwable thrown = Assertions.assertThrows(
            KJsonValidationError.class, builder::finishField
        );
        Assertions.assertEquals(
            "Cannot set property required without provided default value",
            thrown.getMessage()
        );

    }
}
