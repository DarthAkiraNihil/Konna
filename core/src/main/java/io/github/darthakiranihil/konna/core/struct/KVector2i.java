package io.github.darthakiranihil.konna.core.struct;

public record KVector2i(
    int x,
    int y
) {

    public static final KVector2i ZERO = new KVector2i(0, 0);

    public KVector2i add(KVector2i other) {
        return new KVector2i(this.x + other.x(), this.y + other.y());
    }

    public KVector2i subtract(KVector2i other) {
        return new KVector2i(this.x - other.x(), this.y - other.y());
    }

    public KVector2i negate() {
        return new KVector2i(-this.x, -this.y);
    }
}
