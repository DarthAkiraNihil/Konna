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

#ifndef KONNA_CORE_KLOGFORMATTER_H
#define KONNA_CORE_KLOGFORMATTER_H
#include "KLogLevel.h"

namespace Konna::Core::System {

    /**
     * Interface for a log formatter that is designed to prepare log messages
     * to be handled by @link KLogHandler \endlink in specified format.
     *
     * @version 0.2.0
     * @author Darth Akira Nihil
     */
    class KLogFormatter {

        public:
            virtual ~KLogFormatter() = default;

            /**
             * Formats a log message with additional information in addition with the base formatted message.
             *
             * @param level Log message level
             * @param tag Log message tag
             * @param formattedMessage MEssage that has been previously formatted by KLogger
             * @return Formatted log message
             */
            virtual const char* format(KLogLevel level, const std::string& tag, const std::string& formattedMessage) = 0;

    };

}


#endif //KONNA_CORE_KLOGFORMATTER_H
