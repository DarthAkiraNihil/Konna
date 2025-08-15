package io.github.darthakiranihil.konna.core.util;

/**
 * Represents an immutable pair of values of different types like a tuple.
 * @param first The first element of the pair
 * @param second The second element of the pair
 * @param <F> Type of the first pair
 * @param <S> Type of the second pair
 */
public record KPair<F, S>(F first, S second) {

}
