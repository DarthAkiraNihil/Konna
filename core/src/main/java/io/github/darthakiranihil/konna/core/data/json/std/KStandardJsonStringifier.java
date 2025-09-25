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

package io.github.darthakiranihil.konna.core.data.json.std;

import io.github.darthakiranihil.konna.core.data.json.KJsonStringifier;
import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KSingleton;

import java.util.Iterator;

/**
 * Standard implementation of {@link KJsonStringifier}.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
@KSingleton(immortal = true)
public class KStandardJsonStringifier extends KObject implements KJsonStringifier {

    @Override
    public String stringify(final KJsonValue value) {
        StringBuilder builder = new StringBuilder();
        this.deepStringify(builder, value);
        return builder.toString();
    }

    @Override
    public String stringify(final KJsonValue value, int indent) {
        StringBuilder builder = new StringBuilder();
        this.deepStringify(builder, value, indent, 0);
        return builder.toString();
    }

    private void deepStringify(final StringBuilder builder, final KJsonValue value) {
        switch (value.getType()) {
            case NULL -> builder.append("null");
            case BOOLEAN -> builder.append(value.getBoolean());
            case STRING -> builder.append(String.format("\"%s\"", value.getString()));
            case ARRAY -> {
                builder.append("[");
                String comma = "";
                for (Iterator<KJsonValue> it = value.iterator(); it.hasNext();) {
                    var e = it.next();
                    builder.append(comma);
                    this.deepStringify(builder, e);
                    comma = ",";
                }
                builder.append("]");
            }
            case OBJECT -> {
                builder.append("{");
                String comma = "";
                for (var e: value.entrySet()) {
                    builder.append(comma);
                    builder.append(String.format("\"%s\":", e.getKey()));
                    this.deepStringify(builder, e.getValue());
                    comma = ",";
                }
                builder.append("}");
            }
            case NUMBER_INT -> builder.append(value.getInt());
            case NUMBER_FLOAT -> builder.append(value.getFloat());
        }
    }

    private void deepStringify(
        final StringBuilder builder,
        final KJsonValue value,
        int indent,
        int level
    ) {
        String indentPrefix = " ".repeat(indent * level);

        switch (value.getType()) {
            case NULL -> builder.append(indentPrefix).append("null");
            case BOOLEAN -> builder.append(indentPrefix).append(value.getBoolean());
            case STRING -> builder
                            .append(indentPrefix)
                            .append(String.format("\"%s\"", value.getString()));
            case ARRAY -> {
                builder.append(indentPrefix).append("[\n");
                String comma = "";
                for (Iterator<KJsonValue> it = value.iterator(); it.hasNext();) {

                    var e = it.next();

                    builder.append(comma);
                    this.deepStringify(builder, e, indent, level + 1);
                    comma = ",\n";

                }
                builder.append('\n').append(indentPrefix).append("]");
            }
            case OBJECT -> {
                builder.append(indentPrefix).append("{\n");
                String comma = "";

                for (var e: value.entrySet()) {

                    builder.append(comma);
                    this.stringifyKeyValue(builder, e.getKey(), e.getValue(), indent, level + 1);
                    comma = ",\n";

                }
                builder.append('\n').append(indentPrefix).append("}");
            }
            case NUMBER_INT -> builder.append(indentPrefix).append(value.getInt());
            case NUMBER_FLOAT -> builder.append(indentPrefix).append(value.getFloat());
        }
    }

    private void stringifyKeyValue(
        final StringBuilder builder,
        final String key,
        final KJsonValue value,
        int indent,
        int level
    ) {

        String indentPrefix = " ".repeat(indent * level);
        builder
            .append(indentPrefix)
            .append(String.format("\"%s\": ", key));

        switch (value.getType()) {
            case ARRAY -> {
                builder.append("[\n");

                String comma = "";
                for (var it = value.iterator(); it.hasNext();) {
                    builder.append(comma);
                    KJsonValue entry = it.next();
                    this.deepStringify(builder, entry, indent, level + 1);
                    if (comma.isEmpty()) {
                        comma = ",\n";
                    }
                }
                builder.append('\n').append(indentPrefix).append(']');
            }
            case OBJECT -> {
                builder.append("{\n");
                String comma = "";
                for (var entry: value.entrySet()) {
                    builder.append(comma);
                    this.stringifyKeyValue(
                        builder, entry.getKey(), entry.getValue(), indent, level + 1
                    );
                    if (comma.isEmpty()) {
                        comma = ",\n";
                    }
                }
                builder.append('\n').append(indentPrefix).append('}');
            }
            default -> builder.append(this.stringify(value));
        }
    }
}
