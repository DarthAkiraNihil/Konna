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

package io.github.darthakiranihil.konna.core.object;

/**
 * Convenience wrapper for immutable string tag (typically used for {@link KObject}.
 * @param name Name of the tag
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public record KTag(String name) {
    public static class DefaultTags {
        public static final KTag STD = new KTag("std");
        public static final KTag SYSTEM = new KTag("system");
        public static final KTag TEST = new KTag("test");
        public static final KTag IMMORTAL = new KTag("immortal");
        public static final KTag SINGLETON = new KTag("singleton");
        public static final KTag POOLABLE = new KTag("poolable");
        public static final KTag TRANSIENT = new KTag("transient");
        public static final KTag WEAK = new KTag("weak");
        public static final KTag POOL = new KTag("pool");
        public static final KTag WEAK_POOL = new KTag("weak_pool");
    }
}
