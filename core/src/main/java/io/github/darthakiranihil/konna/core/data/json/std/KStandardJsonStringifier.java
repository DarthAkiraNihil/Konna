package io.github.darthakiranihil.konna.core.data.json.std;

import io.github.darthakiranihil.konna.core.data.json.KJsonStringifier;
import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import java.util.Iterator;

/**
 * Standard implementation of {@link KJsonStringifier}
 */
public class KStandardJsonStringifier implements KJsonStringifier {

    /**
     * Default constructor
     */
    public KStandardJsonStringifier() {
    }

    @Override
    public String stringify(KJsonValue value) {
        StringBuilder builder = new StringBuilder();
        this.deepStringify(builder, value);
        return builder.toString();
    }

    @Override
    public String stringify(KJsonValue value, int indent) {
        StringBuilder builder = new StringBuilder();
        this.deepStringify(builder, value, indent, 0);
        return builder.toString();
    }

    private void deepStringify(StringBuilder builder, KJsonValue value) {
        switch (value.getType()) {
            case NULL -> builder.append("null");
            case BOOLEAN -> builder.append(value.getBoolean());
            case STRING -> builder.append(String.format("\"%s\"", value.getString()));
            case ARRAY -> {
                builder.append("[");
                String comma = "";
                for (Iterator<KJsonValue> it = value.iterator(); it.hasNext(); ) {
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

    private void deepStringify(StringBuilder builder, KJsonValue value, int indent, int level) {
        String indentPrefix = " ".repeat(indent * level);

        switch (value.getType()) {
            case NULL -> builder.append(indentPrefix).append("null");
            case BOOLEAN -> builder.append(indentPrefix).append(value.getBoolean());
            case STRING -> builder.append(indentPrefix).append(String.format("\"%s\"", value.getString()));
            case ARRAY -> {
                builder.append(indentPrefix).append("[\n");
                String comma = "";
                for (Iterator<KJsonValue> it = value.iterator(); it.hasNext(); ) {

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

    private void stringifyKeyValue(StringBuilder builder, String key, KJsonValue value, int indent, int level) {

        String indentPrefix = " ".repeat(indent * level);
        builder
            .append(indentPrefix)
            .append(String.format("\"%s\": ", key));

        switch (value.getType()) {
            case ARRAY -> {
                builder.append("[\n");

                String comma = "";
                for (var it = value.iterator(); it.hasNext(); ) {
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
                    this.stringifyKeyValue(builder, entry.getKey(), entry.getValue(), indent, level + 1);
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
