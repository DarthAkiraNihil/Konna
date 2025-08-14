package io.github.darthakiranihil.konna.core.data.json;

/**
 * Record for storing json token information
 * @param token Token descriptor
 * @param value Token match. Can be an int, float, string, boolean or null, or char if token is a single-character one (like braces etc.)
 */
public record KJsonTokenPair(KJsonToken token, Object value) {

    public static final KJsonTokenPair OPEN_BRACE = new KJsonTokenPair(KJsonToken.OPEN_BRACE, '{');
    public static final KJsonTokenPair CLOSE_BRACE = new KJsonTokenPair(KJsonToken.CLOSE_BRACE, '}');
    public static final KJsonTokenPair OPEN_SQUARE_BRACKET = new KJsonTokenPair(KJsonToken.OPEN_SQUARE_BRACKET, '[');
    public static final KJsonTokenPair CLOSE_SQUARE_BRACKET = new KJsonTokenPair(KJsonToken.CLOSE_SQUARE_BRACKET, ']');
    public static final KJsonTokenPair COMMA = new KJsonTokenPair(KJsonToken.COMMA, ',');
    public static final KJsonTokenPair SEMICOLON = new KJsonTokenPair(KJsonToken.SEMICOLON, ':');
    public static final KJsonTokenPair TRUE = new KJsonTokenPair(KJsonToken.TRUE, true);
    public static final KJsonTokenPair FALSE = new KJsonTokenPair(KJsonToken.FALSE, false);
    public static final KJsonTokenPair NULL = new KJsonTokenPair(KJsonToken.NULL, null);
    public static final KJsonTokenPair EOF = new KJsonTokenPair(KJsonToken.EOF, '\0');

    public static KJsonTokenPair fromString(String value) {
        return new KJsonTokenPair(KJsonToken.STRING, value);
    }

    public static KJsonTokenPair fromInteger(int value) {
        return new KJsonTokenPair(KJsonToken.NUMBER_INT, value);
    }

    public static KJsonTokenPair fromFloat(float value) {
        return new KJsonTokenPair(KJsonToken.NUMBER_FLOAT, value);
    }
}
