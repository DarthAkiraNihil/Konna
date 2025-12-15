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

package io.github.darthakiranihil.konna.core.except;

/**
 * Represents severity level of an engine exception.
 * The level could be important in exception handling since some
 * error are not directing the application to crash, so the work continues.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public enum KExceptionSeverity {

    /**
     * The exception is expected to occur. Usually it means that there has been a test
     * that is performed before another action as a check operation. Another suitable
     * situation for this level is absolute exception suppression - the exception is just ignored
     */
    EXPECTED,
    /**
     * The occurred exception is not fatal for application,
     * but the application could work unstable after it, that
     * however, it is not always if the exception is caused because of
     * missing optional dependencies or the like.
     */
    WARNING,
    /**
     * The occurred exception may not be fatal for
     * the application but its work may be surely unstable.
     * You are likely to handle it in a proper way or else
     * transform into a fatal error
     */
    ERROR,
    /**
     * The application cannot work after the exception has occurred.
     */
    FATAL,

}
