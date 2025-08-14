package io.github.darthakiranihil.konna.core.util;

public class KTriplet<F, S, T> {

    private final F first;
    private final S second;
    private final T third;

    public KTriplet(F first, S second, T third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public F getFirst() {
        return this.first;
    }

    public S getSecond() {
        return this.second;
    }

    public T getThird() {
        return this.third;
    }
}
