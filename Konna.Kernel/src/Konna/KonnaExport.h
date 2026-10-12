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

#ifndef KONNA_KERNEL_KONNAEXPORT_H
#define KONNA_KERNEL_KONNAEXPORT_H

#if defined(_WIN32) || defined(__CYGWIN__)
    #ifdef KONNA_KERNEL_EXPORTS
        #define KONNA_KERNEL_API __declspec(dllexport)
    #else
        #define KONNA_KERNEL_API __declspec(dllimport)
    #endif
#else
    #if __GNUC__ >= 4
        #define KONNA_KERNEL_API __attribute__((visibility("default")))
    #else
        #define KONNA_KERNEL_API
    #endif
#endif

#endif // KONNA_KERNEL_KONNAEXPORT_H
