package io.github.darthakiranihil.konna.core.data.json;

/**
 * Record for storing json token information
 * @param token Token descriptor
 * @param value Token match. Can be an int, float, string, boolean or null, or char if token is a single-character one (like braces etc.)
 */
public record KJsonTokenPair(KJsonToken token, Object value) {

}
