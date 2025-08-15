package io.github.darthakiranihil.konna.core.util;

/**
 * Represents an immutable group of three values of different types like a tuple.
 * @param first The first element of the triplet
 * @param second The second element of the triplet
 * @param third The third element of the triplet
 * @param <F> Type of the first element
 * @param <S> Type of the second element
 * @param <T> Type of the third element
 */
public record KTriplet<F, S, T>(F first, S second, T third) {

}
