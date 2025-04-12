package io.github.darthakiranihil.konna.core.data.json;

import io.github.darthakiranihil.konna.core.data.json.except.KJsonValueException;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class KJsonValue implements Iterable<KJsonValue> {

    private final KJsonValueType type;
    private final Object value;

    public KJsonValue(KJsonValueType type, Object value) {
        this.type = type;
        this.value = value;
    }

    public KJsonValueType getType() {
        return type;
    }

    @Override
    public Iterator<KJsonValue> iterator() {
        if (this.type != KJsonValueType.ARRAY) {
            throw new KJsonValueException(
                String.format("Cannot iterate over json value: it's not an array. The actual type is: %s", this.type)
            );
        }

        return ((Iterable<KJsonValue>) this.value).iterator();
    }

    @Override
    public void forEach(Consumer<? super KJsonValue> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<KJsonValue> spliterator() {
        return Iterable.super.spliterator();
    }
}
