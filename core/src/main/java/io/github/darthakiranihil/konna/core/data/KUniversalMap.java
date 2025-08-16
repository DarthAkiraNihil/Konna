package io.github.darthakiranihil.konna.core.data;

import java.util.*;

public class KUniversalMap extends HashMap<String, Object> {

    public <T> T get(String key, Class<T> clazz) {
        return clazz.cast(this.get(key));
    }

}
