/*
 * Copyright 2025-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.darthakiranihil.konna.core.object;

import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class KObjectExtraTests extends KStandardTestClass {

    @Test
    public void testCreateObjectWithTagsAndParent() {
        KObject parent = new KObject();
        KObject obj = new KObject("abiba", Set.of("T1", "T2"), parent);

        KObject retrieved = obj.getParent();
        Assertions.assertNotNull(retrieved);
        Assertions.assertEquals(parent, retrieved);
        Assertions.assertTrue(parent.getChildren().contains(obj));
        Assertions.assertTrue(obj.tags().contains("T1"));
        Assertions.assertTrue(obj.tags().contains("T2"));

    }

    @Test
    public void testCreateObjectWithParent() {

        KObject parent = new KObject();
        KObject obj = new KObject("abiba", parent);

        KObject retrieved = obj.getParent();
        Assertions.assertNotNull(retrieved);
        Assertions.assertEquals(parent, retrieved);
        Assertions.assertTrue(parent.getChildren().contains(obj));

    }

    @Test
    public void testSetParentForThatWhoIsAChildOfAnother() {

        KObject p1 = new KObject();
        KObject p2 = new KObject();
        KObject c = new KObject("child", p1);

        Assertions.assertNotNull(c.getParent());
        Assertions.assertEquals(p1, c.getParent());
        Assertions.assertTrue(p1.getChildren().contains(c));

        c.setParent(p2);
        Assertions.assertFalse(p1.getChildren().contains(c));

        Assertions.assertNotNull(c.getParent());
        Assertions.assertEquals(p2, c.getParent());
        Assertions.assertTrue(p2.getChildren().contains(c));
    }

    @Test
    public void testAddTags() {

        KObject object = new KObject("abiba", Set.of());
        object.addTags("T1", "T2");
        Assertions.assertEquals(2, object.tags().size());
        Assertions.assertTrue(object.tags().contains("T1"));
        Assertions.assertTrue(object.tags().contains("T2"));

    }

    @Test
    public void testRemoveTag() {

        KObject object = new KObject("abiba", Set.of("T1", "T2"));
        object.removeTag("T2");
        Assertions.assertEquals(1, object.tags().size());
        Assertions.assertFalse(object.tags().contains("T2"));
        object.removeTag("TX");
        Assertions.assertFalse(object.tags().contains("TX"));
        Assertions.assertEquals(1, object.tags().size());

    }

    @Test
    public void testDeleteWithComplexHierarchy() {


        KObject root = new KObject();
        KObject deleted = new KObject("deleted", root);
        KObject c1 = new KObject("child1", deleted);
        KObject c2 = new KObject("child2", deleted);
        KObject deep = new KObject("deepChild", c2);

        deleted.delete();

        Assertions.assertNull(deleted.getParent());
        Assertions.assertNull(c1.getParent());
        Assertions.assertNull(c2.getParent());
        Assertions.assertNull(deep.getParent());

        Assertions.assertEquals(0, root.getChildren().size());

    }

    @Test
    public void testEquals() {

        Assertions.assertNotEquals("123", new KObject());

        KObject o1 = new KObject("O1");
        KObject o2 = new KObject("O2");

        Assertions.assertNotEquals(o1, o2);

        KObject oo1 = new KObject("O1", Set.of("T1"));
        KObject oo2 = new KObject("O1", Set.of("T2"));

        Assertions.assertNotEquals(oo1, oo2);

        KObject ooo1 = new KObject("O1", Set.of());
        KObject ooo2 = new KObject("O1", Set.of(), new KObject());

        Assertions.assertNotEquals(ooo1, ooo2);

        KObject one = new KObject("O1", Set.of());
        KObject same = new KObject("O1", Set.of());

        Assertions.assertEquals(one, same);

    }
}
