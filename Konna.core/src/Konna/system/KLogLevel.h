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

    enum class KLogLevel: ku8 {
        FATAL,
        ERROR,
        WARNING,
        INFO,
        DEBUG,
        TRACE,
    };

}

#endif //KONNA_CORE_KLOGLEVEL_H
