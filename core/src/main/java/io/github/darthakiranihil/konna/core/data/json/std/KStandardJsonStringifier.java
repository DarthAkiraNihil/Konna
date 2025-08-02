package io.github.darthakiranihil.konna.core.data.json.std;

import io.github.darthakiranihil.konna.core.data.json.KJsonStringifier;
import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import java.util.Iterator;

/**
 * Standard implementation of KJsonStringifier
 */
public class KStandardJsonStringifier implements KJsonStringifier {

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

    // TODO: fix pretty json
    private void deepStringify(StringBuilder builder, KJsonValue value, int indent, int level) {
        switch (value.getType()) {
            case NULL -> builder.append(" ".repeat(indent * level)).append("null");
            case BOOLEAN -> builder.append(" ".repeat(indent * level)).append(value.getBoolean());
            case STRING -> builder.append(" ".repeat(indent * level)).append(String.format("\"%s\"", value.getString()));
            case ARRAY -> {
                builder.append(" ".repeat(indent * level)).append("[\n");
                String comma = "";
                for (Iterator<KJsonValue> it = value.iterator(); it.hasNext(); ) {
                    var e = it.next();
                    builder.append(comma);
                    this.deepStringify(builder, e, indent, level + 1);
                    comma = ",";
                    builder.append("\n");
                }
                builder.append(" ".repeat(indent * level)).append("]");
            }
            case OBJECT -> {
                builder.append(" ".repeat(indent * level)).append("{\n");
                String comma = "";
                for (var e: value.entrySet()) {
                    builder.append(comma);
                    builder.append(" ".repeat(indent * (level + 1))).append(String.format("\"%s\": ", e.getKey()));
                    this.deepStringify(builder, e.getValue(), indent, level + 1);
                    comma = ",";
                    builder.append("\n");
                }
                builder.append(" ".repeat(indent * level)).append("}");
            }
            case NUMBER_INT -> builder.append(" ".repeat(indent * level)).append(value.getInt());
            case NUMBER_FLOAT -> builder.append(" ".repeat(indent * level)).append(value.getFloat());
        }
    }
}
