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

#include <format>
#include "KLogger.h"

namespace Konna::Core::System {

    template<typename ... Args>
    void KLogger::fatal(const std::string& tag, const std::string& format, const Args&... args) {
        KLogger::log(KLogLevel::FATAL, tag, format, args...);
    }

    template<typename ... Args>
    void KLogger::error(const std::string& tag, const std::string& format, const Args&... args) {
        KLogger::log(KLogLevel::ERROR, tag, format, args...);
    }

    template<typename ... Args>
    void KLogger::warning(const std::string& tag, const std::string& format, const Args&... args) {
        KLogger::log(KLogLevel::WARNING, tag, format, args...);
    }

    template<typename ... Args>
    void KLogger::info(const std::string& tag, const std::string& format, const Args&... args) {
        KLogger::log(KLogLevel::INFO, tag, format, args...);
    }

    template<typename ... Args>
    void KLogger::debug(const std::string& tag, const std::string& format, const Args&... args) {
        KLogger::log(KLogLevel::DEBUG, tag, format, args...);
    }

    template<typename ... Args>
    void KLogger::trace(const std::string& tag, const std::string& format, const Args&... args) {
        KLogger::log(KLogLevel::TRACE, tag, format, args...);
    }

    template<typename ... Args>
    void KLogger::log(const KLogLevel& logLevel, const std::string& tag, const std::string& format, const Args&... args) { // NOLINT

        const std::string preparedMessage = std::format(format, args...);
        for (const auto& handler : handlers) {
            handler->handleLog(logLevel, tag, preparedMessage); // TODO: check for has formatter and default formatter
        }
    }

    void KLogger::addHandler(const kptr<KLogHandler> handler) {
        handlers.push_back(handler);
    }

}
