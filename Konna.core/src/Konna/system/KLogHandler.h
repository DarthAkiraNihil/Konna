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

#ifndef KONNA_CORE_KLOGHANDLER_H
#define KONNA_CORE_KLOGHANDLER_H

#include <Konna/system/KLogLevel.h>

namespace Konna::Core::System {

    /**
     * Interface for a log handler that writes result log line to
     * a source, provided by its implementation.
     *
     * @version 0.2.0
     * @author Darth Akira Nihil
     */
    class KLogHandler {
        public:
            virtual ~KLogHandler() = default;

            /**
             * Handles log in its own way.
             * @param logLevel Log message level
             * @param tag Log message tag
             * @param formattedMessage Log message itself
             */
            virtual void handleLog(const KLogLevel& logLevel, const std::string& tag, const std::string& formattedMessage) = 0;

            /**
             * Return state of custom {@link KLogFormatter} containment inside implementation
             * of the handler.
             * @return {@code true} if handler contains inside it custom {@link KLogFormatter}
             */
            virtual bool hasFormatter() = 0;
    };

}

#endif //KONNA_CORE_KLOGHANDLER_H
