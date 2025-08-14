package io.github.darthakiranihil.konna.core.util;

public class KPair<F, S> {

    private final F first;
    private final S second;

    public KPair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public F getFirst() {
        return this.first;
    }

    public S getSecond() {
        return this.second;
    }

}
