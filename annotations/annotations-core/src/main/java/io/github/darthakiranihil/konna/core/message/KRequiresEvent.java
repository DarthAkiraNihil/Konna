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

package io.github.darthakiranihil.konna.core.message;

import java.lang.annotation.*;

/**
 * Declares that this class requires a non-standard event (not connected with the frame)
 * to work properly. Should be used to generate a global event register at compile-time.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
@Repeatable(KRequiresEvents.class)
public @interface KRequiresEvent {

    /**
     * @return Name of the event
     */
    String name();

    /**
     * @return Whether the event is simple or not
     */
    boolean simple() default true;

    /**
     * Returns type of the event argument. This should be ignored if event is simple.
     * @return Type of event argument
     */
    Class<?> type() default KRequiresEvent.class;

}
