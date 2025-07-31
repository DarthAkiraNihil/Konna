package io.github.darthakiranihil.konna.core.data.json;

/**
 * Enumeration that describes a json token
 */
public enum KJsonToken {

    /**
     * Symbol of "{"
     */
    OPEN_BRACE,
    /**
     * Symbol of "}"
     */
    CLOSE_BRACE,

    /**
     * Symbol of "["
     */
    OPEN_SQUARE_BRACKET,
    /**
     * Symbol of "]"
     */
    CLOSE_SQUARE_BRACKET,

    /**
     * A string
     */
    STRING,
    /**
     * An integer or a float (can be written in exponential form)
     */
    NUMBER,

    /**
     * Symbol of ","
     */
    COMMA,
    /**
     * Symbol of ":"
     */
    SEMICOLON,

    /**
     * True literal
     */
    TRUE,
    /**
     * False literal
     */
    FALSE,
    /**
     * Null literal
     */
    NULL,
    /**
     * Signalizes the end of a file
     */
    EOF,

}
