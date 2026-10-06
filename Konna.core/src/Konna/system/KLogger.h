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

#ifndef KONNA_CORE_KLOGGER_H
#define KONNA_CORE_KLOGGER_H

#include <Konna/system/KLogLevel.h>
#include <Konna/system/KLogHandler.h>
#include <Konna/type/PrimitiveTypes.h>

namespace Konna::Core::System {

    /**
     * Convenience class that is basically a just a static service for logging everything from everywhere.
     *
     * @since 0.2.0
     * @author Darth Akira Nihil
     */
    class KLogger final {

        public:

            /**
             * Logs a message with @link KLogLevel::FATAL \endlink level.
             * @param tag Log message tag
             * @param format Message format
             * @param args Format args
             */
            template<typename... Args>
            static void fatal(const std::string& tag, const std::string& format, const Args&... args);
            /**
             * Logs a message with @link KLogLevel::ERROR \endlink level.
             * @param tag Log message tag
             * @param format Message format
             * @param args Format args
             */
            template<typename... Args>
            static void error(const std::string& tag, const std::string& format, const Args&... args);
            /**
             * Logs a message with @link KLogLevel::WARNING \endlink level.
             * @param tag Log message tag
             * @param format Message format
             * @param args Format args
             */
            template<typename... Args>
            static void warning(const std::string& tag, const std::string& format, const Args&... args);
            /**
             * Logs a message with @link KLogLevel::INFO \endlink level.
             * @param tag Log message tag
             * @param format Message format
             * @param args Format args
             */
            template<typename... Args>
            static void info(const std::string& tag, const std::string& format, const Args&... args);
            /**
             * Logs a message with @link KLogLevel::DEBUG \endlink level.
             * @param tag Log message tag
             * @param format Message format
             * @param args Format args
             */
            template<typename... Args>
            static void debug(const std::string& tag, const std::string& format, const Args&... args);
            /**
             * Logs a message with @link KLogLevel::TRACE \endlink level.
             * @param tag Log message tag
             * @param format Message format
             * @param args Format args
             */
            template<typename... Args>
            static void trace(const std::string& tag, const std::string& format, const Args&... args);

            static void addHandler(kptr<KLogHandler> handler);

        private:
            template<typename... Args>
            static void log(const KLogLevel& logLevel, const std::string& tag, const std::string& format, const Args&... args);
            static klist<kptr<KLogHandler>> handlers;
    };

}

#endif //KONNA_CORE_KLOGGER_H
