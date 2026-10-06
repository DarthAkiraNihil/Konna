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

#ifndef KONNA_CORE_KLOGLEVEL_H
#define KONNA_CORE_KLOGLEVEL_H

#include <Konna/type/PrimitiveTypes.h>

namespace Konna::Core::System {

    /**
     * Enumeration that describes severity and importance of logged messages.
     *
     * @version 0.2.0
     * @author Darth Akira Nihil
     */
    enum class KLogLevel: ku8 {
        /**
         * Indicates a severe error after that the application cannot continue
         * working? so it crashes.
         */
        FATAL,
        /**
         * Indicates an error that may break concrete functionality, but it
         * does not fatal for the whole application.
         */
        ERROR,
        /**
         * Indicates a potential issue occurred somewhere in the codebase
         * that may be a real problem if the issue is not fixed.
         */
        WARNING,
        /**
         * Describes general information about application execution processes.
         */
        INFO,
        /**
         * Contains different debug information that is useful during development
         * of application features or bug fixing.
         */
        DEBUG,
        /**
        * The most detailed level, providing the full data about execution process,
        * including entered methods etc.
        */
        TRACE,
    };

}

#endif //KONNA_CORE_KLOGLEVEL_H
