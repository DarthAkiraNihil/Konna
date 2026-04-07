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

import io.github.darthakiranihil.konna.core.log.KLogLevel;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;

import java.util.Objects;

public final class KSystemFeatures extends KObject {

    private static boolean getDebug(final KApplicationFeatures features) {
        String debugFeature = features.getFeature("debug");
        return debugFeature != null && debugFeature.equals("true");
    }

    private static boolean getFileLoggingActive(final KApplicationFeatures features) {
        String logToFileFeature = features.getFeature("log-to-file");
        return logToFileFeature != null && logToFileFeature.equals("true");
    }

    private static KLogLevel getLogLevel(final KApplicationFeatures features) {
        String logLevel = features.getFeature("log-level");
        return KLogLevel.valueOf(
            Objects.requireNonNull(
                logLevel,
                "Log level is required"
            )
        );
    }

    private static int getMaxFps(final KApplicationFeatures features) {
        String maxFpsFeature = features.getFeature("max-fps");
        return Integer.parseInt(
            Objects.requireNonNull(maxFpsFeature, "Max FPS is required")
        );
    }

    private final boolean debugEnabled;
    private final boolean fileLoggingActive;
    private final KLogLevel logLevel;
    private final int maxFps;

    public KSystemFeatures(final KApplicationFeatures features) {
        super("SystemFeatures", KStructUtils.setOfTags(KTag.DefaultTags.SYSTEM));
        this.debugEnabled = KSystemFeatures.getDebug(features);
        this.fileLoggingActive = KSystemFeatures.getFileLoggingActive(features);
        this.logLevel = KSystemFeatures.getLogLevel(features);
        this.maxFps = KSystemFeatures.getMaxFps(features);
    }

    public KSystemFeatures() {
        super("SystemFeatures", KStructUtils.setOfTags(KTag.DefaultTags.SYSTEM));
        this.debugEnabled = false;
        this.fileLoggingActive = false;
        this.logLevel = KLogLevel.WARNING;
        this.maxFps = -1;
    }

    public boolean isDebugEnabled() {
        return this.debugEnabled;
    }

    public boolean isFileLoggingActive() {
        return this.fileLoggingActive;
    }

    public KLogLevel getLogLevel() {
        return this.logLevel;
    }

    public int getMaxFps() {
        return this.maxFps;
    }
}
