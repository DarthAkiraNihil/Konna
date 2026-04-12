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

package io.github.darthakiranihil.konna.core.app;

import io.github.darthakiranihil.konna.core.util.KThreadUtils;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.*;

import java.util.List;

public class KStandardFrameTaskSystemAsyncTests extends KStandardTestClass {

    private static final class TestObject {
        int field;
    }

    private KFrameTaskSystem taskSystem;

    @BeforeEach
    void setUp(final TestInfo testInfo) {

        if (testInfo.getTags().contains("runWithFifo")) {
            this.taskSystem = new KStandardFrameTaskSystem(new KFrameTaskPrioritizer.Fifo());
        } else {
            this.taskSystem = new KStandardFrameTaskSystem();
        }

    }

    @Test
    public void testSchedulePersistent() {
        TestObject object = new TestObject();
        this.taskSystem.scheduleTask(
            KFrameTaskDescription.ofAsyncPersistent("pt1", KFrameEvent.TICK, 0),
            () -> object.field++
        );
        this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
        KThreadUtils.sleepForSeconds(1);
        Assertions.assertEquals(1, object.field);
        for (int i = 0; i < 16; i++) {
            List<KScheduledFrameTask> tasks = this.taskSystem.getScheduledTasks(KFrameEvent.TICK);
            Assertions.assertEquals(1, tasks.size());
            KScheduledFrameTask task = tasks.getFirst();
            Assertions.assertEquals("pt1", task.getId());
            Assertions.assertEquals(KFrameEvent.TICK, task.getEvent());
            KThreadUtils.sleepForSeconds(1);
            Assertions.assertEquals(0, task.getPriority());
            Assertions.assertEquals(0, task.getDelay());
            Assertions.assertFalse(task.isTemporal());
            Assertions.assertFalse(task.isDebug());

            this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
            KThreadUtils.sleepForSeconds(1);
            Assertions.assertEquals(i + 2, object.field);
        }
    }

    @Test
    public void testScheduleDelayedPersistent() {
        TestObject object = new TestObject();
        this.taskSystem.scheduleTask(
            KFrameTaskDescription.ofAsyncDelayedPersistent("pt1", KFrameEvent.TICK, 0, 16),
            () -> object.field++
        );
        this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
        KThreadUtils.sleepForSeconds(1);
        Assertions.assertEquals(0, object.field);
        for (int i = 0; i < 15; i++) {
            List<KScheduledFrameTask> tasks = this.taskSystem.getScheduledTasks(KFrameEvent.TICK);
            Assertions.assertEquals(1, tasks.size());
            KScheduledFrameTask task = tasks.getFirst();
            Assertions.assertEquals("pt1", task.getId());
            Assertions.assertEquals(KFrameEvent.TICK, task.getEvent());
            KThreadUtils.sleepForSeconds(1);
            Assertions.assertEquals(0, task.getPriority());
            Assertions.assertEquals(16, task.getDelay());
            Assertions.assertFalse(task.isTemporal());
            Assertions.assertFalse(task.isDebug());

            this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
            KThreadUtils.sleepForSeconds(1);
            Assertions.assertEquals(i == 14 ? 1 : 0, object.field);
        }
    }

    @Test
    public void testScheduleRepeatedPersistent() {
        TestObject object = new TestObject();
        KFrameTaskDescription description = new KFrameTaskDescription(
            "prt1", KFrameEvent.TICK, 0, 0, true, false, true, false
        );

        this.taskSystem.scheduleTask(description, () -> object.field++);
        this.taskSystem.scheduleTask(description, () -> object.field++);
        this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
        KThreadUtils.sleepForSeconds(1);
        Assertions.assertEquals(1, object.field);

        for (int i = 0; i < 16; i++) {
            List<KScheduledFrameTask> tasks = this.taskSystem.getScheduledTasks(KFrameEvent.TICK);
            Assertions.assertEquals(1, tasks.size());
            KScheduledFrameTask task = tasks.getFirst();
            Assertions.assertEquals("prt1", task.getId());
            Assertions.assertEquals(KFrameEvent.TICK, task.getEvent());
            KThreadUtils.sleepForSeconds(1);
            Assertions.assertEquals(0, task.getPriority());
            Assertions.assertEquals(0, task.getDelay());
            Assertions.assertFalse(task.isTemporal());
            Assertions.assertFalse(task.isDebug());

            this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
            KThreadUtils.sleepForSeconds(1);
            Assertions.assertEquals(i + 2, object.field);
        }
    }

    @Test
    public void testScheduleDebugPersistent() {
        TestObject object = new TestObject();
        KFrameTaskDescription description = new KFrameTaskDescription(
            "pdt1", KFrameEvent.TICK, 0, 0, true, false, false, true
        );

        this.taskSystem.setIsDebug(true);
        this.taskSystem.scheduleTask(description, () -> object.field++);
        this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
        KThreadUtils.sleepForSeconds(1);
        Assertions.assertEquals(1, object.field);

        for (int i = 0; i < 16; i++) {
            List<KScheduledFrameTask> tasks = this.taskSystem.getScheduledTasks(KFrameEvent.TICK);
            Assertions.assertEquals(1, tasks.size());
            KScheduledFrameTask task = tasks.getFirst();
            Assertions.assertEquals("pdt1", task.getId());
            Assertions.assertEquals(KFrameEvent.TICK, task.getEvent());
            KThreadUtils.sleepForSeconds(1);
            Assertions.assertEquals(0, task.getPriority());
            Assertions.assertEquals(0, task.getDelay());
            Assertions.assertFalse(task.isTemporal());
            Assertions.assertTrue(task.isDebug());

            this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
            KThreadUtils.sleepForSeconds(1);
            Assertions.assertEquals(i + 2, object.field);
        }
    }

    @Test
    public void testScheduleDebugPersistentButItsNotDebug() {
        TestObject object = new TestObject();
        KFrameTaskDescription description = new KFrameTaskDescription(
            "pdt1", KFrameEvent.TICK, 0, 0, true, false, false, true
        );

        this.taskSystem.scheduleTask(description, () -> object.field++);
        this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
        KThreadUtils.sleepForSeconds(1);
        Assertions.assertEquals(0, object.field);

        for (int i = 0; i < 16; i++) {
            List<KScheduledFrameTask> tasks = this.taskSystem.getScheduledTasks(KFrameEvent.TICK);
            Assertions.assertEquals(0, tasks.size());
            this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
            KThreadUtils.sleepForSeconds(1);
            Assertions.assertEquals(0, object.field);
        }
    }

    @Test
    public void testSchedulePersistentForEnterEventButItsCompleted() {

        TestObject object = new TestObject();
        KFrameTaskDescription description = KFrameTaskDescription.ofAsyncPersistent("pt1", KFrameEvent.ENTER, 0);
        this.taskSystem.scheduleTask(description, () -> object.field++);
        this.taskSystem.executeScheduledTasks(KFrameEvent.ENTER);
        KThreadUtils.sleepForSeconds(1);
        Assertions.assertEquals(1, object.field);

        this.taskSystem.scheduleTask(description, () -> object.field++);
        List<KScheduledFrameTask> tasks = this.taskSystem.getScheduledTasks(KFrameEvent.TICK);
        Assertions.assertEquals(0, tasks.size());
        this.taskSystem.executeScheduledTasks(KFrameEvent.ENTER);
        KThreadUtils.sleepForSeconds(1);
        Assertions.assertEquals(1, object.field);

    }

    @Test
    public void testScheduleTemporal() {
        TestObject object = new TestObject();
        this.taskSystem.scheduleTask(
            KFrameTaskDescription.ofAsyncImmediateTemporal("pt1", KFrameEvent.TICK, 0),
            () -> object.field++
        );
        this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
        KThreadUtils.sleepForSeconds(1);
        Assertions.assertEquals(1, object.field);
        for (int i = 0; i < 16; i++) {
            List<KScheduledFrameTask> tasks = this.taskSystem.getScheduledTasks(KFrameEvent.TICK);
            Assertions.assertEquals(0, tasks.size());
            this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
            KThreadUtils.sleepForSeconds(1);
            Assertions.assertEquals(1, object.field);
        }
    }

    @Test
    public void testScheduleDelayedTemporal() {
        TestObject object = new TestObject();
        this.taskSystem.scheduleTask(
            KFrameTaskDescription.ofAsyncTemporal("pt1", KFrameEvent.TICK, 0,  8),
            () -> object.field++
        );
        for (int i = 0; i < 16; i++) {
            List<KScheduledFrameTask> tasks = this.taskSystem.getScheduledTasks(KFrameEvent.TICK);
            if (i < 7) {
                Assertions.assertEquals(1, tasks.size());
                KScheduledFrameTask task = tasks.getFirst();
                Assertions.assertEquals("pt1", task.getId());
                Assertions.assertEquals(KFrameEvent.TICK, task.getEvent());
                Assertions.assertEquals(0, task.getPriority());
                Assertions.assertEquals(8, task.getDelay());
                Assertions.assertTrue(task.isTemporal());
                Assertions.assertFalse(task.isDebug());
                this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
                KThreadUtils.sleepForSeconds(1);
                Assertions.assertEquals(0, object.field);
            } else if (i == 7) {
                Assertions.assertEquals(1, tasks.size());
                this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
                KThreadUtils.sleepForSeconds(1);
                Assertions.assertEquals(1, object.field);
            } else {
                Assertions.assertEquals(0, tasks.size());
                this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
                KThreadUtils.sleepForSeconds(1);
                Assertions.assertEquals(1, object.field);
            }

        }
    }

    @Test
    public void testScheduleRepeatedTemporal() {
        TestObject object = new TestObject();
        KFrameTaskDescription description = KFrameTaskDescription.ofAsyncRepeatableTemporal(
            "trt1", KFrameEvent.TICK, 0
        );

        this.taskSystem.scheduleTask(description, () -> object.field++);
        this.taskSystem.scheduleTask(description, () -> object.field++);
        Assertions.assertEquals(1, this.taskSystem.getScheduledTasks(KFrameEvent.TICK).size());
        this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
        KThreadUtils.sleepForSeconds(1);
        Assertions.assertEquals(1, object.field);
        Assertions.assertEquals(1, this.taskSystem.getScheduledTasks(KFrameEvent.TICK).size());
        this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
        KThreadUtils.sleepForSeconds(1);
        Assertions.assertEquals(2, object.field);

    }

    @Test
    public void testScheduleDelayedRepeatableTemporal() {

        KFrameTaskDescription description = KFrameTaskDescription.ofAsyncDelayedRepeatableTemporal("pt1", KFrameEvent.TICK, 0,  8);
        TestObject object = new TestObject();
        this.taskSystem.scheduleTask(description, () -> object.field++);
        this.taskSystem.scheduleTask(description, () -> object.field++);

        for (int i = 0; i < 16; i++) {
            List<KScheduledFrameTask> tasks = this.taskSystem.getScheduledTasks(KFrameEvent.TICK);
            if (i < 7) {
                Assertions.assertEquals(1, tasks.size());
                KScheduledFrameTask task = tasks.getFirst();
                Assertions.assertEquals("pt1", task.getId());
                Assertions.assertEquals(KFrameEvent.TICK, task.getEvent());
                KThreadUtils.sleepForSeconds(1);
                Assertions.assertEquals(0, task.getPriority());
                Assertions.assertEquals(8, task.getDelay());
                Assertions.assertTrue(task.isTemporal());
                Assertions.assertFalse(task.isDebug());
                this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
                KThreadUtils.sleepForSeconds(1);
                Assertions.assertEquals(0, object.field);
            } else if (i == 7) {
                Assertions.assertEquals(1, tasks.size());
                this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
                KThreadUtils.sleepForSeconds(1);
                Assertions.assertEquals(1, object.field);
            } else if (i == 15) {
                Assertions.assertEquals(1, tasks.size());
                this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
                KThreadUtils.sleepForSeconds(1);
                Assertions.assertEquals(2, object.field);
                Assertions.assertEquals(0, this.taskSystem.getScheduledTasks(KFrameEvent.TICK).size());
            } else {
                Assertions.assertEquals(1, tasks.size());
                this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
                KThreadUtils.sleepForSeconds(1);
                Assertions.assertEquals(1, object.field);
            }

        }

    }

    @Test
    public void testScheduleDebugTemporal() {
        TestObject object = new TestObject();
        KFrameTaskDescription description = new KFrameTaskDescription(
            "pt1", KFrameEvent.TICK, 0, 0, true, true, false, true
        );
        this.taskSystem.setIsDebug(true);
        this.taskSystem.scheduleTask(
            description,
            () -> object.field++
        );

        this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
        KThreadUtils.sleepForSeconds(1);
        Assertions.assertEquals(1, object.field);
        for (int i = 0; i < 16; i++) {
            List<KScheduledFrameTask> tasks = this.taskSystem.getScheduledTasks(KFrameEvent.TICK);
            Assertions.assertEquals(0, tasks.size());
            this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
            KThreadUtils.sleepForSeconds(1);
            Assertions.assertEquals(1, object.field);
        }
    }

    @Test
    public void testScheduleDebugTemporalButItsNotDebug() {
        TestObject object = new TestObject();
        KFrameTaskDescription description = new KFrameTaskDescription(
            "pt1", KFrameEvent.TICK, 0, 0, true, true, false, true
        );
        this.taskSystem.scheduleTask(
            description,
            () -> object.field++
        );

        this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
        KThreadUtils.sleepForSeconds(1);
        Assertions.assertEquals(0, object.field);
        for (int i = 0; i < 16; i++) {
            List<KScheduledFrameTask> tasks = this.taskSystem.getScheduledTasks(KFrameEvent.TICK);
            Assertions.assertEquals(0, tasks.size());
            this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
            KThreadUtils.sleepForSeconds(1);
            Assertions.assertEquals(0, object.field);
        }
    }

    @Test
    public void testScheduleTemporalForEnterEventButItsCompleted() {
        TestObject object = new TestObject();
        KFrameTaskDescription description = KFrameTaskDescription.ofAsyncImmediateTemporal("pt1", KFrameEvent.ENTER, 0);
        this.taskSystem.scheduleTask(description, () -> object.field++);
        this.taskSystem.executeScheduledTasks(KFrameEvent.ENTER);
        KThreadUtils.sleepForSeconds(1);
        Assertions.assertEquals(1, object.field);

        this.taskSystem.scheduleTask(description, () -> object.field++);
        List<KScheduledFrameTask> tasks = this.taskSystem.getScheduledTasks(KFrameEvent.TICK);
        Assertions.assertEquals(0, tasks.size());
        this.taskSystem.executeScheduledTasks(KFrameEvent.ENTER);
        KThreadUtils.sleepForSeconds(1);
        Assertions.assertEquals(1, object.field);
    }

    @Test
    @Tag("runWithFifo")
    public void testSchedulePersistentWithFifo() {
        TestObject object = new TestObject();
        this.taskSystem.scheduleTask(
            KFrameTaskDescription.ofAsyncPersistent("pt1", KFrameEvent.TICK, 0),
            () -> object.field++
        );
        this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
        KThreadUtils.sleepForSeconds(1);
        Assertions.assertEquals(1, object.field);
        for (int i = 0; i < 16; i++) {
            List<KScheduledFrameTask> tasks = this.taskSystem.getScheduledTasks(KFrameEvent.TICK);
            Assertions.assertEquals(1, tasks.size());
            KScheduledFrameTask task = tasks.getFirst();
            Assertions.assertEquals("pt1", task.getId());
            Assertions.assertEquals(KFrameEvent.TICK, task.getEvent());
            KThreadUtils.sleepForSeconds(1);
            Assertions.assertEquals(0, task.getPriority());
            Assertions.assertEquals(0, task.getDelay());
            Assertions.assertFalse(task.isTemporal());
            Assertions.assertFalse(task.isDebug());

            this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
            KThreadUtils.sleepForSeconds(1);
            Assertions.assertEquals(i + 2, object.field);
        }
    }

    @Test
    @Tag("runWithFifo")
    public void testScheduleDelayedPersistentWithFifo() {
        TestObject object = new TestObject();
        this.taskSystem.scheduleTask(
            KFrameTaskDescription.ofAsyncDelayedPersistent("pt1", KFrameEvent.TICK, 0, 16),
            () -> object.field++
        );
        this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
        KThreadUtils.sleepForSeconds(1);
        Assertions.assertEquals(0, object.field);
        for (int i = 0; i < 15; i++) {
            List<KScheduledFrameTask> tasks = this.taskSystem.getScheduledTasks(KFrameEvent.TICK);
            Assertions.assertEquals(1, tasks.size());
            KScheduledFrameTask task = tasks.getFirst();
            Assertions.assertEquals("pt1", task.getId());
            Assertions.assertEquals(KFrameEvent.TICK, task.getEvent());
            Assertions.assertEquals(0, task.getPriority());
            Assertions.assertEquals(16, task.getDelay());
            Assertions.assertFalse(task.isTemporal());
            Assertions.assertFalse(task.isDebug());

            this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
            KThreadUtils.sleepForSeconds(1);
            Assertions.assertEquals(i == 14 ? 1 : 0, object.field);
        }
    }

    @Test
    @Tag("runWithFifo")
    public void testScheduleRepeatedPersistentWithFifo() {
        TestObject object = new TestObject();
        KFrameTaskDescription description = new KFrameTaskDescription(
            "prt1", KFrameEvent.TICK, 0, 0, true, false, true, false
        );

        this.taskSystem.scheduleTask(description, () -> object.field++);
        this.taskSystem.scheduleTask(description, () -> object.field++);
        this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
        KThreadUtils.sleepForSeconds(1);
        Assertions.assertEquals(1, object.field);

        for (int i = 0; i < 16; i++) {
            List<KScheduledFrameTask> tasks = this.taskSystem.getScheduledTasks(KFrameEvent.TICK);
            Assertions.assertEquals(1, tasks.size());
            KScheduledFrameTask task = tasks.getFirst();
            Assertions.assertEquals("prt1", task.getId());
            Assertions.assertEquals(KFrameEvent.TICK, task.getEvent());
            Assertions.assertEquals(0, task.getPriority());
            Assertions.assertEquals(0, task.getDelay());
            Assertions.assertFalse(task.isTemporal());
            Assertions.assertFalse(task.isDebug());

            this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
            KThreadUtils.sleepForSeconds(1);
            Assertions.assertEquals(i + 2, object.field);
        }
    }

    @Test
    @Tag("runWithFifo")
    public void testScheduleDebugPersistentWithFifo() {
        TestObject object = new TestObject();
        KFrameTaskDescription description = new KFrameTaskDescription(
            "pdt1", KFrameEvent.TICK, 0, 0, true, false, false, true
        );

        this.taskSystem.setIsDebug(true);
        this.taskSystem.scheduleTask(description, () -> object.field++);
        this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
        KThreadUtils.sleepForSeconds(1);
        Assertions.assertEquals(1, object.field);

        for (int i = 0; i < 16; i++) {
            List<KScheduledFrameTask> tasks = this.taskSystem.getScheduledTasks(KFrameEvent.TICK);
            Assertions.assertEquals(1, tasks.size());
            KScheduledFrameTask task = tasks.getFirst();
            Assertions.assertEquals("pdt1", task.getId());
            Assertions.assertEquals(KFrameEvent.TICK, task.getEvent());
            Assertions.assertEquals(0, task.getPriority());
            Assertions.assertEquals(0, task.getDelay());
            Assertions.assertFalse(task.isTemporal());
            Assertions.assertTrue(task.isDebug());
            Assertions.assertTrue(task.isAsync());

            this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
            KThreadUtils.sleepForSeconds(1);
            Assertions.assertEquals(i + 2, object.field);
        }
    }

    @Test
    @Tag("runWithFifo")
    public void testScheduleDebugPersistentButItsNotDebugWithFifo() {
        TestObject object = new TestObject();
        KFrameTaskDescription description = new KFrameTaskDescription(
            "pdt1", KFrameEvent.TICK, 0, 0, true, false, false, true
        );

        this.taskSystem.scheduleTask(description, () -> object.field++);
        this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
        KThreadUtils.sleepForSeconds(1);
        Assertions.assertEquals(0, object.field);

        for (int i = 0; i < 16; i++) {
            List<KScheduledFrameTask> tasks = this.taskSystem.getScheduledTasks(KFrameEvent.TICK);
            Assertions.assertEquals(0, tasks.size());
            this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
            KThreadUtils.sleepForSeconds(1);
            Assertions.assertEquals(0, object.field);
        }
    }

    @Test
    @Tag("runWithFifo")
    public void testSchedulePersistentForEnterEventButItsCompletedWithFifo() {

        TestObject object = new TestObject();
        KFrameTaskDescription description = KFrameTaskDescription.ofAsyncPersistent("pt1", KFrameEvent.ENTER, 0);
        this.taskSystem.scheduleTask(description, () -> object.field++);
        this.taskSystem.executeScheduledTasks(KFrameEvent.ENTER);
        KThreadUtils.sleepForSeconds(1);
        Assertions.assertEquals(1, object.field);

        this.taskSystem.scheduleTask(description, () -> object.field++);
        List<KScheduledFrameTask> tasks = this.taskSystem.getScheduledTasks(KFrameEvent.TICK);
        Assertions.assertEquals(0, tasks.size());
        this.taskSystem.executeScheduledTasks(KFrameEvent.ENTER);
        KThreadUtils.sleepForSeconds(1);
        Assertions.assertEquals(1, object.field);

    }

    @Test
    @Tag("runWithFifo")
    public void testScheduleTemporalWithFifo() {
        TestObject object = new TestObject();
        this.taskSystem.scheduleTask(
            KFrameTaskDescription.ofAsyncImmediateTemporal("pt1", KFrameEvent.TICK, 0),
            () -> object.field++
        );
        this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
        KThreadUtils.sleepForSeconds(1);
        Assertions.assertEquals(1, object.field);
        for (int i = 0; i < 16; i++) {
            List<KScheduledFrameTask> tasks = this.taskSystem.getScheduledTasks(KFrameEvent.TICK);
            Assertions.assertEquals(0, tasks.size());
            this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
            KThreadUtils.sleepForSeconds(1);
            Assertions.assertEquals(1, object.field);
        }
    }

    @Test
    @Tag("runWithFifo")
    public void testScheduleDelayedTemporalWithFifo() {
        TestObject object = new TestObject();
        this.taskSystem.scheduleTask(
            KFrameTaskDescription.ofAsyncTemporal("pt1", KFrameEvent.TICK, 0,  8),
            () -> object.field++
        );
        for (int i = 0; i < 16; i++) {
            List<KScheduledFrameTask> tasks = this.taskSystem.getScheduledTasks(KFrameEvent.TICK);
            if (i < 7) {
                Assertions.assertEquals(1, tasks.size());
                KScheduledFrameTask task = tasks.getFirst();
                Assertions.assertEquals("pt1", task.getId());
                Assertions.assertEquals(KFrameEvent.TICK, task.getEvent());
                Assertions.assertEquals(0, task.getPriority());
                Assertions.assertEquals(8, task.getDelay());
                Assertions.assertTrue(task.isTemporal());
                Assertions.assertFalse(task.isDebug());
                this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
                KThreadUtils.sleepForSeconds(1);
                Assertions.assertEquals(0, object.field);
            } else if (i == 7) {
                Assertions.assertEquals(1, tasks.size());
                this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
                KThreadUtils.sleepForSeconds(1);
                Assertions.assertEquals(1, object.field);
            } else {
                Assertions.assertEquals(0, tasks.size());
                this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
                KThreadUtils.sleepForSeconds(1);
                Assertions.assertEquals(1, object.field);
            }

        }
    }

    @Test
    @Tag("runWithFifo")
    public void testScheduleRepeatedTemporalWithFifo() {
        TestObject object = new TestObject();
        KFrameTaskDescription description = KFrameTaskDescription.ofAsyncRepeatableTemporal(
            "trt1", KFrameEvent.TICK, 0
        );

        this.taskSystem.scheduleTask(description, () -> object.field++);
        this.taskSystem.scheduleTask(description, () -> object.field++);
        Assertions.assertEquals(1, this.taskSystem.getScheduledTasks(KFrameEvent.TICK).size());
        this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
        KThreadUtils.sleepForSeconds(1);
        Assertions.assertEquals(1, object.field);
        Assertions.assertEquals(1, this.taskSystem.getScheduledTasks(KFrameEvent.TICK).size());
        this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
        KThreadUtils.sleepForSeconds(1);
        Assertions.assertEquals(2, object.field);

    }

    @Test
    @Tag("runWithFifo")
    public void testScheduleDelayedRepeatableTemporalWithFifo() {

        KFrameTaskDescription description = KFrameTaskDescription.ofAsyncDelayedRepeatableTemporal("pt1", KFrameEvent.TICK, 0,  8);
        TestObject object = new TestObject();
        this.taskSystem.scheduleTask(description, () -> object.field++);
        this.taskSystem.scheduleTask(description, () -> object.field++);

        for (int i = 0; i < 16; i++) {
            List<KScheduledFrameTask> tasks = this.taskSystem.getScheduledTasks(KFrameEvent.TICK);
            if (i < 7) {
                Assertions.assertEquals(1, tasks.size());
                KScheduledFrameTask task = tasks.getFirst();
                Assertions.assertEquals("pt1", task.getId());
                Assertions.assertEquals(KFrameEvent.TICK, task.getEvent());
                Assertions.assertEquals(0, task.getPriority());
                Assertions.assertEquals(8, task.getDelay());
                Assertions.assertTrue(task.isTemporal());
                Assertions.assertFalse(task.isDebug());
                this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
                KThreadUtils.sleepForSeconds(1);
                Assertions.assertEquals(0, object.field);
            } else if (i == 7) {
                Assertions.assertEquals(1, tasks.size());
                this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
                KThreadUtils.sleepForSeconds(1);
                Assertions.assertEquals(1, object.field);
            } else if (i == 15) {
                Assertions.assertEquals(1, tasks.size());
                this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
                KThreadUtils.sleepForSeconds(1);
                Assertions.assertEquals(2, object.field);
                Assertions.assertEquals(0, this.taskSystem.getScheduledTasks(KFrameEvent.TICK).size());
            } else {
                Assertions.assertEquals(1, tasks.size());
                this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
                KThreadUtils.sleepForSeconds(1);
                Assertions.assertEquals(1, object.field);
            }

        }

    }

    @Test
    @Tag("runWithFifo")
    public void testScheduleDebugTemporalWithFifo() {
        TestObject object = new TestObject();
        KFrameTaskDescription description = new KFrameTaskDescription(
            "pt1", KFrameEvent.TICK, 0, 0, true, true, false, true
        );
        this.taskSystem.setIsDebug(true);
        this.taskSystem.scheduleTask(
            description,
            () -> object.field++
        );

        this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
        KThreadUtils.sleepForSeconds(1);
        Assertions.assertEquals(1, object.field);
        for (int i = 0; i < 16; i++) {
            List<KScheduledFrameTask> tasks = this.taskSystem.getScheduledTasks(KFrameEvent.TICK);
            Assertions.assertEquals(0, tasks.size());
            this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
            KThreadUtils.sleepForSeconds(1);
            Assertions.assertEquals(1, object.field);
        }
    }

    @Test
    @Tag("runWithFifo")
    public void testScheduleDebugTemporalButItsNotDebugWithFifo() {
        TestObject object = new TestObject();
        KFrameTaskDescription description = new KFrameTaskDescription(
            "pt1", KFrameEvent.TICK, 0, 0, true, true, false, true
        );
        this.taskSystem.scheduleTask(
            description,
            () -> object.field++
        );

        this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
        KThreadUtils.sleepForSeconds(1);
        Assertions.assertEquals(0, object.field);
        for (int i = 0; i < 16; i++) {
            List<KScheduledFrameTask> tasks = this.taskSystem.getScheduledTasks(KFrameEvent.TICK);
            Assertions.assertEquals(0, tasks.size());
            this.taskSystem.executeScheduledTasks(KFrameEvent.TICK);
            KThreadUtils.sleepForSeconds(1);
            Assertions.assertEquals(0, object.field);
        }
    }

    @Test
    @Tag("runWithFifo")
    public void testScheduleTemporalForEnterEventButItsCompletedWithFifo() {
        TestObject object = new TestObject();
        KFrameTaskDescription description = KFrameTaskDescription.ofAsyncImmediateTemporal("pt1", KFrameEvent.ENTER, 0);
        this.taskSystem.scheduleTask(description, () -> object.field++);
        this.taskSystem.executeScheduledTasks(KFrameEvent.ENTER);
        KThreadUtils.sleepForSeconds(1);
        Assertions.assertEquals(1, object.field);

        this.taskSystem.scheduleTask(description, () -> object.field++);
        List<KScheduledFrameTask> tasks = this.taskSystem.getScheduledTasks(KFrameEvent.TICK);
        Assertions.assertEquals(0, tasks.size());
        this.taskSystem.executeScheduledTasks(KFrameEvent.ENTER);
        KThreadUtils.sleepForSeconds(1);
        Assertions.assertEquals(1, object.field);
    }

}
