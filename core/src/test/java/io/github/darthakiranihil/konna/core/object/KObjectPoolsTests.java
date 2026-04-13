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

import io.github.darthakiranihil.konna.core.di.KEngineModule;
import io.github.darthakiranihil.konna.core.except.KNoSuchElementException;
import io.github.darthakiranihil.konna.core.object.except.KEmptyObjectPoolException;
import io.github.darthakiranihil.konna.core.util.KThreadUtils;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;

@NullMarked
public class KObjectPoolsTests extends KStandardTestClass {

    private static KAllocatePool materializePoolMetadata(
        int initialSize,
        KAllocatePool.NoObjectPolicy noObjectPolicy,
        boolean extensible,
        int maxSize,
        float extensionFactor
    ) {
        return new KAllocatePool() {
            @Override
            public int initialSize() {
                return initialSize;
            }

            @Override
            public NoObjectPolicy noObjectPolicy() {
                return noObjectPolicy;
            }

            @Override
            public boolean extensible() {
                return extensible;
            }

            @Override
            public int maxSize() {
                return maxSize;
            }

            @Override
            public float extensionFactor() {
                return extensionFactor;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return KAllocatePool.class;
            }
        };
    }



    private final KActivator activator;
    private final KObjectRegistry objectRegistry;

    public KObjectPoolsTests() {

        KEngineModule module = KStandardTestClass.getModule();

        this.objectRegistry = module.objectRegistry();
        this.activator = module.activator();

    }

    @Test
    public void testGetSimplePoolableSuccess() {

        KObjectPool<TestSimplePoolable> strictPool = this.getStrictPool(TestSimplePoolable.class);
        KObjectPool<TestSimplePoolable> forgivingPool = this.getForgivingPool(TestSimplePoolable.class);
        KObjectPool<TestSimplePoolable> strictExtensiblePool = this.getStrictExtensiblePool(TestSimplePoolable.class);
        KObjectPool<TestSimplePoolable> forgivingExtensiblePool = this.getForgivingExtensiblePool(TestSimplePoolable.class);

        this.simpleTest(strictPool, forgivingPool, strictExtensiblePool, forgivingExtensiblePool);
        this.simpleTestWithTimeout(strictPool, forgivingPool, strictExtensiblePool, forgivingExtensiblePool);

    }

    @Test
    public void testGetInjectedPoolable() {
        KObjectPool<TestInjectedPoolable> strictPool = this.getStrictPool(TestInjectedPoolable.class);
        KObjectPool<TestInjectedPoolable> forgivingPool = this.getForgivingPool(TestInjectedPoolable.class);
        KObjectPool<TestInjectedPoolable> strictExtensiblePool = this.getStrictExtensiblePool(TestInjectedPoolable.class);
        KObjectPool<TestInjectedPoolable> forgivingExtensiblePool = this.getForgivingExtensiblePool(TestInjectedPoolable.class);

        try (var o = strictPool.obtain()) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertNotNull(o.get().getMessageSystem());
        }

        try (var o = forgivingPool.obtain()) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertNotNull(o.get().getMessageSystem());
        }

        try (var o = strictExtensiblePool.obtain()) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertNotNull(o.get().getMessageSystem());
        }

        try (var o = forgivingExtensiblePool.obtain()) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertNotNull(o.get().getMessageSystem());
        }

        this.simpleTest(strictPool, forgivingPool, strictExtensiblePool, forgivingExtensiblePool);
        this.simpleTestWithTimeout(strictPool, forgivingPool, strictExtensiblePool, forgivingExtensiblePool);
    }

    @Test
    public void testGetExplicitPoolable() {
        KObjectPool<TestExplicitPoolable> strictPool = this.getStrictPool(TestExplicitPoolable.class);
        KObjectPool<TestExplicitPoolable> forgivingPool = this.getForgivingPool(TestExplicitPoolable.class);
        KObjectPool<TestExplicitPoolable> strictExtensiblePool = this.getStrictExtensiblePool(TestExplicitPoolable.class);
        KObjectPool<TestExplicitPoolable> forgivingExtensiblePool = this.getForgivingExtensiblePool(TestExplicitPoolable.class);

        KArgs args = () -> new Object[] {1};
        try (var o = strictPool.obtain(args)) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertEquals(1, o.get().getValue());
        }

        try (var o = forgivingPool.obtain(args)) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertEquals(1, o.get().getValue());
        }

        try (var o = strictExtensiblePool.obtain(args)) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertEquals(1, o.get().getValue());
        }

        try (var o = forgivingExtensiblePool.obtain(args)) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertEquals(1, o.get().getValue());
        }

        this.simpleTest(strictPool, forgivingPool, strictExtensiblePool, forgivingExtensiblePool, args);
        this.simpleTestWithTimeout(strictPool, forgivingPool, strictExtensiblePool, forgivingExtensiblePool, args);
    }

    @Test
    public void testGetExplicitPoolableWithInjected() {
        KObjectPool<TestExplicitPoolableWithInjected> strictPool = this.getStrictPool(TestExplicitPoolableWithInjected.class);
        KObjectPool<TestExplicitPoolableWithInjected> forgivingPool = this.getForgivingPool(TestExplicitPoolableWithInjected.class);
        KObjectPool<TestExplicitPoolableWithInjected> strictExtensiblePool = this.getStrictExtensiblePool(TestExplicitPoolableWithInjected.class);
        KObjectPool<TestExplicitPoolableWithInjected> forgivingExtensiblePool = this.getForgivingExtensiblePool(TestExplicitPoolableWithInjected.class);

        KArgs args = () -> new Object[] {1};
        try (var o = strictPool.obtain(args)) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertNotNull(o.get().getMessageSystem());
            Assertions.assertEquals(1, o.get().getValue());
        }

        try (var o = forgivingPool.obtain(args)) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertNotNull(o.get().getMessageSystem());
            Assertions.assertEquals(1, o.get().getValue());
        }

        try (var o = strictExtensiblePool.obtain(args)) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertNotNull(o.get().getMessageSystem());
            Assertions.assertEquals(1, o.get().getValue());
        }

        try (var o = forgivingExtensiblePool.obtain(args)) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertNotNull(o.get().getMessageSystem());
            Assertions.assertEquals(1, o.get().getValue());
        }

        this.simpleTest(strictPool, forgivingPool, strictExtensiblePool, forgivingExtensiblePool, args);
        this.simpleTestWithTimeout(strictPool, forgivingPool, strictExtensiblePool, forgivingExtensiblePool, args);
    }

    @Test
    public void testGetExplicitPoolableWithFalseInjected() {
        KObjectPool<TestExplicitFalseInjectedPoolable> strictPool = this.getStrictPool(TestExplicitFalseInjectedPoolable.class);
        KObjectPool<TestExplicitFalseInjectedPoolable> forgivingPool = this.getForgivingPool(TestExplicitFalseInjectedPoolable.class);
        KObjectPool<TestExplicitFalseInjectedPoolable> strictExtensiblePool = this.getStrictExtensiblePool(TestExplicitFalseInjectedPoolable.class);
        KObjectPool<TestExplicitFalseInjectedPoolable> forgivingExtensiblePool = this.getForgivingExtensiblePool(TestExplicitFalseInjectedPoolable.class);

        KArgs args = () -> new Object[] {1};
        try (var o = strictPool.obtain(args)) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertNull(o.get().getMessageSystem());
            Assertions.assertEquals(1, o.get().getValue());
        }

        try (var o = forgivingPool.obtain(args)) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertNull(o.get().getMessageSystem());
            Assertions.assertEquals(1, o.get().getValue());
        }

        try (var o = strictExtensiblePool.obtain(args)) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertNull(o.get().getMessageSystem());
            Assertions.assertEquals(1, o.get().getValue());
        }

        try (var o = forgivingExtensiblePool.obtain(args)) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertNull(o.get().getMessageSystem());
            Assertions.assertEquals(1, o.get().getValue());
        }

        this.simpleTest(strictPool, forgivingPool, strictExtensiblePool, forgivingExtensiblePool, args);
        this.simpleTestWithTimeout(strictPool, forgivingPool, strictExtensiblePool, forgivingExtensiblePool, args);
    }

    @Test
    public void testGetPoolableWithFalseInjected() {
        KObjectPool<TestFalseInjectedPoolable> strictPool = this.getStrictPool(TestFalseInjectedPoolable.class);
        KObjectPool<TestFalseInjectedPoolable> forgivingPool = this.getForgivingPool(TestFalseInjectedPoolable.class);
        KObjectPool<TestFalseInjectedPoolable> strictExtensiblePool = this.getStrictExtensiblePool(TestFalseInjectedPoolable.class);
        KObjectPool<TestFalseInjectedPoolable> forgivingExtensiblePool = this.getForgivingExtensiblePool(TestFalseInjectedPoolable.class);

        try (var o = strictPool.obtain()) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertNull(o.get().getMessageSystem());
        }

        try (var o = forgivingPool.obtain()) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertNull(o.get().getMessageSystem());
        }

        try (var o = strictExtensiblePool.obtain()) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertNull(o.get().getMessageSystem());
        }

        try (var o = forgivingExtensiblePool.obtain()) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertNull(o.get().getMessageSystem());
        }

        this.simpleTest(strictPool, forgivingPool, strictExtensiblePool, forgivingExtensiblePool);
        this.simpleTestWithTimeout(strictPool, forgivingPool, strictExtensiblePool, forgivingExtensiblePool);
    }

    @Test
    public void testGetPoolableWithZeroArgOnObtain() {
        KObjectPool<TestPoolableWithZeroArgOnObtain> strictPool = this.getStrictPool(TestPoolableWithZeroArgOnObtain.class);
        KObjectPool<TestPoolableWithZeroArgOnObtain> forgivingPool = this.getForgivingPool(TestPoolableWithZeroArgOnObtain.class);
        KObjectPool<TestPoolableWithZeroArgOnObtain> strictExtensiblePool = this.getStrictExtensiblePool(TestPoolableWithZeroArgOnObtain.class);
        KObjectPool<TestPoolableWithZeroArgOnObtain> forgivingExtensiblePool = this.getForgivingExtensiblePool(TestPoolableWithZeroArgOnObtain.class);

        try (var o = strictPool.obtain()) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertEquals(1, o.get().getValue());
        }

        try (var o = forgivingPool.obtain()) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertEquals(1, o.get().getValue());
        }

        try (var o = strictExtensiblePool.obtain()) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertEquals(1, o.get().getValue());
        }

        try (var o = forgivingExtensiblePool.obtain()) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertEquals(1, o.get().getValue());
        }

        this.simpleTest(strictPool, forgivingPool, strictExtensiblePool, forgivingExtensiblePool);
        this.simpleTestWithTimeout(strictPool, forgivingPool, strictExtensiblePool, forgivingExtensiblePool);
    }

    @Test
    public void testLateObtain() {
        KObjectPool<TestSimplePoolable> strictPool = this.getStrictPool(TestSimplePoolable.class);
        KObjectPool<TestSimplePoolable> forgivingPool = this.getForgivingPool(TestSimplePoolable.class);

        KThreadUtils.runAsync(() -> {
            try (var o1 = strictPool.obtain()) {
                Assertions.assertTrue(o1.isPresent());
                KThreadUtils.sleepForSeconds(2);
            }
        });

        try (var o2 = strictPool.obtain()) {
            Assertions.assertTrue(o2.isPresent());
            try (var o3 = strictPool.obtain(4)) {
                Assertions.assertTrue(o3.isPresent());
            }
        }
        KThreadUtils.runAsync(() -> {
            try (var o1 = forgivingPool.obtain()) {
                Assertions.assertTrue(o1.isPresent());
                KThreadUtils.sleepForSeconds(2);
            }
        });

        try (var o2 = forgivingPool.obtain()) {
            Assertions.assertTrue(o2.isPresent());
            try (var o3 = forgivingPool.obtain(4)) {
                Assertions.assertTrue(o3.isPresent());
            }
        }
    }

    private <T extends KPoolable> KObjectPool<T> getStrictPool(Class<T> clazz) {
        return KObjectPool.create(
            clazz,
            this.activator,
            this.objectRegistry,
            materializePoolMetadata(2, KAllocatePool.NoObjectPolicy.THROW_EXCEPTION, false, -1, 1.5f)
        );
    }

    private <T extends KPoolable> KObjectPool<T> getForgivingPool(Class<T> clazz) {
        return KObjectPool.create(
            clazz,
            this.activator,
            this.objectRegistry,
            materializePoolMetadata(2, KAllocatePool.NoObjectPolicy.RETURN_EMPTY, false, -1, 1.5f)
        );
    }

    private <T extends KPoolable> KObjectPool<T> getStrictExtensiblePool(Class<T> clazz) {
        return KObjectPool.create(
            clazz,
            this.activator,
            this.objectRegistry,
            materializePoolMetadata(2, KAllocatePool.NoObjectPolicy.THROW_EXCEPTION, true, 3, 1.5f)
        );
    }

    private <T extends KPoolable> KObjectPool<T> getForgivingExtensiblePool(Class<T> clazz) {
        return KObjectPool.create(
            clazz,
            this.activator,
            this.objectRegistry,
            materializePoolMetadata(2, KAllocatePool.NoObjectPolicy.RETURN_EMPTY, true, 3, 1.5f)
        );
    }

    private <T extends KPoolable> void simpleTest(
        KObjectPool<T> strictPool,
        KObjectPool<T> forgivingPool,
        KObjectPool<T> strictExtensiblePool,
        KObjectPool<T> forgivingExtensiblePool
    ) {
        try (var o = strictPool.obtain()) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertDoesNotThrow(o::get);

            try (var o2 = strictPool.obtain()) {
                Assertions.assertTrue(o2.isPresent());
                Assertions.assertDoesNotThrow(o2::get);

                Assertions.assertThrows(
                    KEmptyObjectPoolException.class,
                    () -> {
                        try (var o3 = strictPool.obtain()) {
                            Assertions.assertFalse(o3.isPresent());
                        }
                    }
                );
            }
        }

        try (var o = forgivingPool.obtain()) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertDoesNotThrow(o::get);

            try (var o2 = forgivingPool.obtain()) {
                Assertions.assertTrue(o2.isPresent());
                Assertions.assertDoesNotThrow(o2::get);

                try (var o3 = forgivingPool.obtain()) {
                    Assertions.assertFalse(o3.isPresent());
                    Assertions.assertThrows(
                        KNoSuchElementException.class,
                        o3::get
                    );
                }
            }
        }

        try (var o = strictExtensiblePool.obtain()) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertDoesNotThrow(o::get);

            try (var o2 = strictExtensiblePool.obtain()) {
                Assertions.assertTrue(o2.isPresent());
                Assertions.assertDoesNotThrow(o2::get);

                try (var o3 = strictExtensiblePool.obtain()) {
                    Assertions.assertTrue(o3.isPresent());
                    Assertions.assertDoesNotThrow(o3::get);

                    Assertions.assertThrows(
                        KEmptyObjectPoolException.class,
                        () -> {
                            try (var o4 = strictExtensiblePool.obtain()) {
                                Assertions.assertFalse(o4.isPresent());
                            }
                        }
                    );
                }
            }
        }

        try (var o = forgivingExtensiblePool.obtain()) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertDoesNotThrow(o::get);

            try (var o2 = forgivingExtensiblePool.obtain()) {
                Assertions.assertTrue(o2.isPresent());
                Assertions.assertDoesNotThrow(o2::get);

                try (var o3 = forgivingExtensiblePool.obtain()) {
                    Assertions.assertTrue(o3.isPresent());
                    Assertions.assertDoesNotThrow(o3::get);

                    try (var o4 = forgivingExtensiblePool.obtain()) {
                        Assertions.assertFalse(o4.isPresent());
                        Assertions.assertThrows(KNoSuchElementException.class, o4::get);
                    }
                }
            }
        }
    }

    private <T extends KPoolable> void simpleTestWithTimeout(
        KObjectPool<T> strictPool,
        KObjectPool<T> forgivingPool,
        KObjectPool<T> strictExtensiblePool,
        KObjectPool<T> forgivingExtensiblePool
    ) {
        try (var o = strictPool.obtain(1)) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertDoesNotThrow(o::get);

            try (var o2 = strictPool.obtain(1)) {
                Assertions.assertTrue(o2.isPresent());
                Assertions.assertDoesNotThrow(o2::get);

                Assertions.assertThrows(
                    KEmptyObjectPoolException.class,
                    () -> {
                        try (var o3 = strictPool.obtain(1)) {
                            Assertions.assertFalse(o3.isPresent());
                        }
                    }
                );
            }
        }

        try (var o = forgivingPool.obtain(1)) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertDoesNotThrow(o::get);

            try (var o2 = forgivingPool.obtain(1)) {
                Assertions.assertTrue(o2.isPresent());
                Assertions.assertDoesNotThrow(o2::get);

                try (var o3 = forgivingPool.obtain(1)) {
                    Assertions.assertFalse(o3.isPresent());
                    Assertions.assertThrows(
                        KNoSuchElementException.class,
                        o3::get
                    );
                }
            }
        }

        try (var o = strictExtensiblePool.obtain(1)) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertDoesNotThrow(o::get);

            try (var o2 = strictExtensiblePool.obtain(1)) {
                Assertions.assertTrue(o2.isPresent());
                Assertions.assertDoesNotThrow(o2::get);

                try (var o3 = strictExtensiblePool.obtain(1)) {
                    Assertions.assertTrue(o3.isPresent());
                    Assertions.assertDoesNotThrow(o3::get);

                    Assertions.assertThrows(
                        KEmptyObjectPoolException.class,
                        () -> {
                            try (var o4 = strictExtensiblePool.obtain(1)) {
                                Assertions.assertFalse(o4.isPresent());
                            }
                        }
                    );
                }
            }
        }

        try (var o = forgivingExtensiblePool.obtain(1)) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertDoesNotThrow(o::get);

            try (var o2 = forgivingExtensiblePool.obtain(1)) {
                Assertions.assertTrue(o2.isPresent());
                Assertions.assertDoesNotThrow(o2::get);

                try (var o3 = forgivingExtensiblePool.obtain(1)) {
                    Assertions.assertTrue(o3.isPresent());
                    Assertions.assertDoesNotThrow(o3::get);

                    try (var o4 = forgivingExtensiblePool.obtain(1)) {
                        Assertions.assertFalse(o4.isPresent());
                        Assertions.assertThrows(KNoSuchElementException.class, o4::get);
                    }
                }
            }
        }
    }

    private <T extends KPoolable> void simpleTest(
        KObjectPool<T> strictPool,
        KObjectPool<T> forgivingPool,
        KObjectPool<T> strictExtensiblePool,
        KObjectPool<T> forgivingExtensiblePool,
        KArgs args
    ) {
        try (var o = strictPool.obtain(args)) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertDoesNotThrow(o::get);

            try (var o2 = strictPool.obtain(args)) {
                Assertions.assertTrue(o2.isPresent());
                Assertions.assertDoesNotThrow(o2::get);

                Assertions.assertThrows(
                    KEmptyObjectPoolException.class,
                    () -> {
                        try (var o3 = strictPool.obtain(args)) {
                            Assertions.assertFalse(o3.isPresent());
                        }
                    }
                );
            }
        }

        try (var o = forgivingPool.obtain(args)) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertDoesNotThrow(o::get);

            try (var o2 = forgivingPool.obtain(args)) {
                Assertions.assertTrue(o2.isPresent());
                Assertions.assertDoesNotThrow(o2::get);

                try (var o3 = forgivingPool.obtain(args)) {
                    Assertions.assertFalse(o3.isPresent());
                    Assertions.assertThrows(
                        KNoSuchElementException.class,
                        o3::get
                    );
                }
            }
        }

        try (var o = strictExtensiblePool.obtain(args)) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertDoesNotThrow(o::get);

            try (var o2 = strictExtensiblePool.obtain(args)) {
                Assertions.assertTrue(o2.isPresent());
                Assertions.assertDoesNotThrow(o2::get);

                try (var o3 = strictExtensiblePool.obtain(args)) {
                    Assertions.assertTrue(o3.isPresent());
                    Assertions.assertDoesNotThrow(o3::get);

                    Assertions.assertThrows(
                        KEmptyObjectPoolException.class,
                        () -> {
                            try (var o4 = strictExtensiblePool.obtain(args)) {
                                Assertions.assertFalse(o4.isPresent());
                            }
                        }
                    );
                }
            }
        }

        try (var o = forgivingExtensiblePool.obtain(args)) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertDoesNotThrow(o::get);

            try (var o2 = forgivingExtensiblePool.obtain(args)) {
                Assertions.assertTrue(o2.isPresent());
                Assertions.assertDoesNotThrow(o2::get);

                try (var o3 = forgivingExtensiblePool.obtain(args)) {
                    Assertions.assertTrue(o3.isPresent());
                    Assertions.assertDoesNotThrow(o3::get);

                    try (var o4 = forgivingExtensiblePool.obtain(args)) {
                        Assertions.assertFalse(o4.isPresent());
                        Assertions.assertThrows(KNoSuchElementException.class, o4::get);
                    }
                }
            }
        }
    }

    private <T extends KPoolable> void simpleTestWithTimeout(
        KObjectPool<T> strictPool,
        KObjectPool<T> forgivingPool,
        KObjectPool<T> strictExtensiblePool,
        KObjectPool<T> forgivingExtensiblePool,
        KArgs args
    ) {
        try (var o = strictPool.obtain(args, 1)) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertDoesNotThrow(o::get);

            try (var o2 = strictPool.obtain(args, 1)) {
                Assertions.assertTrue(o2.isPresent());
                Assertions.assertDoesNotThrow(o2::get);

                Assertions.assertThrows(
                    KEmptyObjectPoolException.class,
                    () -> {
                        try (var o3 = strictPool.obtain(args, 1)) {
                            Assertions.assertFalse(o3.isPresent());
                        }
                    }
                );
            }
        }

        try (var o = forgivingPool.obtain(args, 1)) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertDoesNotThrow(o::get);

            try (var o2 = forgivingPool.obtain(args, 1)) {
                Assertions.assertTrue(o2.isPresent());
                Assertions.assertDoesNotThrow(o2::get);

                try (var o3 = forgivingPool.obtain(args, 1)) {
                    Assertions.assertFalse(o3.isPresent());
                    Assertions.assertThrows(
                        KNoSuchElementException.class,
                        o3::get
                    );
                }
            }
        }

        try (var o = strictExtensiblePool.obtain(args, 1)) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertDoesNotThrow(o::get);

            try (var o2 = strictExtensiblePool.obtain(args, 1)) {
                Assertions.assertTrue(o2.isPresent());
                Assertions.assertDoesNotThrow(o2::get);

                try (var o3 = strictExtensiblePool.obtain(args, 1)) {
                    Assertions.assertTrue(o3.isPresent());
                    Assertions.assertDoesNotThrow(o3::get);

                    Assertions.assertThrows(
                        KEmptyObjectPoolException.class,
                        () -> {
                            try (var o4 = strictExtensiblePool.obtain(args, 1)) {
                                Assertions.assertFalse(o4.isPresent());
                            }
                        }
                    );
                }
            }
        }

        try (var o = forgivingExtensiblePool.obtain(args, 1)) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertDoesNotThrow(o::get);

            try (var o2 = forgivingExtensiblePool.obtain(args, 1)) {
                Assertions.assertTrue(o2.isPresent());
                Assertions.assertDoesNotThrow(o2::get);

                try (var o3 = forgivingExtensiblePool.obtain(args, 1)) {
                    Assertions.assertTrue(o3.isPresent());
                    Assertions.assertDoesNotThrow(o3::get);

                    try (var o4 = forgivingExtensiblePool.obtain(args, 1)) {
                        Assertions.assertFalse(o4.isPresent());
                        Assertions.assertThrows(KNoSuchElementException.class, o4::get);
                    }
                }
            }
        }
    }
}
