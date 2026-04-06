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

import java.util.HashMap;
import java.util.Map;

/**
 * Interface for frame task prioritizer that returns a new priority
 * for passed task description.
 *
 * @since 0.6.0
 * @author Darth Akira Nihil
 */
public interface KFrameTaskPrioritizer {

    /**
     * Prioritizer passed on FIFO principle i.e.
     */
    final class Fifo implements KFrameTaskPrioritizer {

        private final Map<KFrameEvent, Integer> priorities;

        Fifo() {

            var frameEvents = KFrameEvent.values();
            this.priorities = new HashMap<>(frameEvents.length);
            for (KFrameEvent frameEvent: frameEvents) {
                this.priorities.put(frameEvent, 0);
            }
        }

        @Override
        public int getPriority(final KFrameTaskDescription description) {
            KFrameEvent event = description.event();
            int priority = this.priorities.get(event);
            this.priorities.put(event, priority + 1);
            return priority;
        }
    }

    /**
     * Prioritizer that just takes task priority from its description.
     */
    final class LeaveAsIs implements KFrameTaskPrioritizer {

        @Override
        public int getPriority(final KFrameTaskDescription description) {
            return description.priority();
        }

    }

    /**
     * @param description Task description
     * @return Priority for this task
     */
    int getPriority(KFrameTaskDescription description);

}
