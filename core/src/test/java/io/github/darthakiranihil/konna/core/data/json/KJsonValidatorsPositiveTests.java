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
import io.github.darthakiranihil.konna.core.data.json.std.KJsonObjectValidator;
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KJsonValidatorsPositiveTests extends KStandardTestClass {


    @Test
    public void testObjectValidatorWithDefaultValue() {

        String src = "{\"aboba\": 123}";
        var propBuilder = new KJsonPropertyValidationInfo.Builder();
        KJsonValidator validator = new KJsonObjectValidator(
            propBuilder
                .withName("aboba")
                .withExpectedType(KJsonValueType.NUMBER_INT)
                .build(),
            propBuilder
                .withName("biba")
                .withExpectedType(KJsonValueType.NUMBER_INT)
                .withRequired(false)
                .withDefaultValue(1)
                .build()
        );


        try {
            KJsonValue parsed = this.jsonParser.parse(src);
            validator.validate(parsed);

            Assertions.assertTrue(parsed.hasProperty("biba"));

            KJsonValue prop = parsed.getProperty("biba");
            Assertions.assertEquals(KJsonValueType.NUMBER_INT, prop.getType());
            Assertions.assertEquals(1, prop.getInt());

        } catch (KJsonParseException e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testObjectValidatorWithNullableValue() {

        String src = "{\"aboba\": null}";
        var propBuilder = new KJsonPropertyValidationInfo.Builder();
        KJsonValidator validator = new KJsonObjectValidator(
            propBuilder
                .withName("aboba")
                .withExpectedType(KJsonValueType.NUMBER_INT)
                .withNullable(true)
                .build()
        );


        try {
            KJsonValue parsed = this.jsonParser.parse(src);
            validator.validate(parsed);

            Assertions.assertTrue(parsed.hasProperty("aboba"));

            KJsonValue prop = parsed.getProperty("aboba");
            Assertions.assertEquals(KJsonValueType.NULL, prop.getType());
            Assertions.assertTrue(prop.isNull());

        } catch (KJsonParseException e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testPropertyBuilderWithDefaultValueAsNull() {

        var builder = new KJsonPropertyValidationInfo.Builder()
            .withName("test")
            .withNullable(true)
            .withExpectedType(KJsonValueType.NUMBER_INT)
            .withRequired(false);

        try {
            builder.build();
        } catch (KJsonValidationError e) {
            Assertions.fail(e);
        }

    }
}
