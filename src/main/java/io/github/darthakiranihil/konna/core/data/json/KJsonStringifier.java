package io.github.darthakiranihil.konna.core.data.json;

/**
 * Interface for Json stringifiers that converts a json value to a json string that can ve written
 * to other places like files, streams etc.
 */
public interface KJsonStringifier {

    /**
     * Stringifies json value into one-liner, without prettifying
     * @param value Value to stringify
     * @return Stringified json value
     */
    String stringify(KJsonValue value);

    /**
     * Stringifies json value into a pretty string, with new lines and indentations
     * @param value Value to stringify
     * @param indent Indentation
     * @return Stringified json value
     */
    String stringify(KJsonValue value, int indent);

}
