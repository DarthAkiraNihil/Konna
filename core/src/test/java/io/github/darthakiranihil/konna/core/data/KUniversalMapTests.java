package io.github.darthakiranihil.konna.core.data;

import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KUniversalMapTests extends KStandardTestClass {

    @Test
    public void testGetValueOfItsAndAnotherType() {

        KUniversalMap map = new KUniversalMap();
        map.put("test", 1);

        Assertions.assertEquals(1, map.get("test", Integer.class));
        Assertions.assertThrowsExactly(ClassCastException.class, () -> map.get("test", String.class));
    }

}
