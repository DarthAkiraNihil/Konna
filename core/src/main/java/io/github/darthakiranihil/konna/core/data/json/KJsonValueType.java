package io.github.darthakiranihil.konna.core.data.json;

/**
 * Enumeration for representing the type of json value
 */
public enum KJsonValueType {

    /**
     * The value is a json object
     */
    OBJECT,
    /**
     * The value is an array
     */
    ARRAY,
    /**
     * The value is an integral number
     */
    NUMBER_INT,
    /**
     * The value is a float
     */
    NUMBER_FLOAT,
    /**
     * The value is a string
     */
    STRING,
    /**
     * The value is true of false
     */
    BOOLEAN,
    /**
     * The value is null
     */
    NULL

}
