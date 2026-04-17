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

package io.github.darthakiranihil.konna.core.app;

import org.jspecify.annotations.Nullable;

/**
 * Simple container for version data (based on semantic versioning).
 * @param major Major version number
 * @param minor Minor version number
 * @param revision Revision number
 * @param name Additional version name
 * @param postfix Additional version postfix
 *
 * @since 0.6.0
 * @author Darth Akira Nihil
 */
public record KVersion(
    int major,
    int minor,
    int revision,
    @Nullable String name,
    @Nullable String postfix
) {

    /**
     * Constructs a revisionless version.
     * @param major Major version number
     * @param minor Minor version number
     */
    public KVersion(
        int major,
        int minor
    ) {
        this(
            major,
            minor,
            0,
            null,
            null
        );
    }

    /**
     * Constructs a named version with postfix.
     * @param major Major version number
     * @param minor Minor version number
     * @param revision Revision number
     * @param postfix Additional version postfix
     */
    public KVersion(
        int major,
        int minor,
        int revision,
        final String postfix
    ) {
        this(major, minor, revision, null, postfix);
    }

    @Override
    public String toString() {
        String v = this.postfix == null
            ? String.format("v%d.%d.%d", this.major, this.minor, this.revision)
            : String.format("v%d.%d.%d-%s", this.major, this.minor, this.revision, this.postfix);

        return this.name == null
            ? v
            : String.format("%s \"%s\"", v, this.name);
    }
}
