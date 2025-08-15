package io.github.darthakiranihil.konna.core.data.json;

import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KJsonValuePositiveTests extends KStandardTestClass {

    @Test
    public void testIsNullAndGetNullString() {

        KJsonValue realNull = new KJsonValue(KJsonValueType.STRING, null);
        KJsonValue nullByType = new KJsonValue(KJsonValueType.NULL, 1);

        Assertions.assertTrue(realNull.isNull());
        Assertions.assertTrue(nullByType.isNull());
        Assertions.assertNull(realNull.getString());
    }

}
