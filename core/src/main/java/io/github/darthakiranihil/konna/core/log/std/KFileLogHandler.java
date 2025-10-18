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

package io.github.darthakiranihil.konna.core.log.std;

import io.github.darthakiranihil.konna.core.log.KLogFormatter;
import io.github.darthakiranihil.konna.core.log.KLogHandler;
import io.github.darthakiranihil.konna.core.log.KLogLevel;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KTag;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of {@link KLogHandler} that writes log
 * messages to a file (it appends to it). Requires formatter
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public class KFileLogHandler extends KObject implements KLogHandler, AutoCloseable {

    private final KLogFormatter logFormatter;
    private final Writer fileWriter;

    /**
     * Constructs handler with opening file with given name and provided formatter.
     * @param filename Filename of log to open
     * @param logFormatter Log formatter
     */
    public KFileLogHandler(final String filename, final KLogFormatter logFormatter) {
        super(KFileLogHandler.class.getSimpleName(), new HashSet<>(List.of(KTag.DefaultTags.STD)));

        this.logFormatter = logFormatter;

        Writer writer;
        try {
            writer = new FileWriter(filename, true);
        } catch (IOException e) {
            System.out.printf("Warning: KFileLogHandler was unable to open log file: %s%n", e);
            writer = null;
        }

        this.fileWriter = writer;
    }

    @Override
    public void handleLog(final KLogLevel logLevel, final String message, final Object... args) {
        if (this.fileWriter == null) {
            System.out.println("Warning: file writer of KFileLogHandler is null!");
            return;
        }

        try {
            this.fileWriter.write(this.logFormatter.format(logLevel, message, args));
        } catch (IOException e) {
            System.out.printf("Warning: Cannot write log to file: %s%n", e);
        }
    }

    @Override
    public boolean hasFormatter() {
        return false;
    }

    @Override
    public void close() throws Exception {
        this.fileWriter.close();
    }
}
