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

package io.github.darthakiranihil.konna.core.data.json.std;

import io.github.darthakiranihil.konna.core.data.json.KJsonTokenizer;
import io.github.darthakiranihil.konna.core.data.json.except.KJsonTokenException;
import io.github.darthakiranihil.konna.core.data.json.KJsonTokenPair;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Standard implementation of {@link KJsonTokenizer}.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public class KStandardJsonTokenizer implements KJsonTokenizer {

    private static final int UNICODE_DIGITS_COUNT = 4;

    private static final int UNICODE_BASE = 16;

    private static class State {
        private int line;
        private int column;

        private boolean rolledBack;
        private int rolledBackChar;

        private final Reader source;

        State(final Reader source) {
            this.source = source;
            this.line = 1;
            this.column = 0;
        }

        public void rollback(int lastReadChar) {
            this.rolledBack = true;
            this.rolledBackChar = lastReadChar;
        }

        public void backOnTrack() {
            this.rolledBack = false;
        }

        public void nextLine() {
            this.line++;
            this.column = 0;
        }

        public void next() {
            this.column++;
        }
    }

    private static int addedSequences = 0;

    private final Map<Integer, State> states;

    /**
     * Default constructor.
     */
    public KStandardJsonTokenizer() {
        states = new HashMap<>();
    }

    @Override
    public KJsonTokenPair getNextToken(int sequenceToken) throws KJsonTokenException {
        if (!states.containsKey(sequenceToken)) {
            return KJsonTokenPair.EOF;
        }

        State recovered = states.get(sequenceToken);
        KJsonTokenPair token = this.scan(recovered);
        if (token == KJsonTokenPair.EOF) {
            states.remove(sequenceToken);
        }

        return token;
    }

    @Override
    public synchronized int addSource(final InputStream source) {
        return this.addSource(new InputStreamReader(source));
    }

    @Override
    public synchronized int addSource(final String source) {
        return this.addSource(new StringReader(source));
    }

    @Override
    public synchronized int addSource(final Reader source) {

        int key = KStandardJsonTokenizer.addedSequences;
        states.put(key, new State(source));
        KStandardJsonTokenizer.addedSequences++;
        return key;

    }

    @Override
    public synchronized int addSource(final char[] source) {
        return this.addSource(new CharArrayReader(source));
    }

    private static boolean isNotSpace(char ch) {
        return ch != ' ' && ch != '\n' && ch != '\t';
    }

    private int next(final State state) {

        state.next();
        return this.read(state.source);

    }

    private int next(final State state, final StringBuilder builder, char ch) {
        builder.append(ch);
        return this.next(state);
    }

    private int read(final Reader reader) {
        try {
            return reader.read();
        } catch (IOException e) {
            return -1;
        }
    }


    private KJsonTokenPair scan(final State state) throws KJsonTokenException {

        int readData;
        if (state.rolledBack) {
            state.backOnTrack();
            readData = state.rolledBackChar;
        } else {
            readData = this.next(state);
        }

        if (readData == -1) {
            return KJsonTokenPair.EOF;
        }

        while (true) {
            char current = (char) readData;
            switch (current) {
                case '\n': {
                    state.nextLine();
                }
                case '\t':
                case '\r':
                case ' ': {
                    readData = this.next(state);
                    break;
                }

                default: {
                    return this.processLiter(state, current);
                }
            }
        }
    }

    private KJsonTokenPair processLiter(
        final State state,
        char current
    ) throws KJsonTokenException {
        
        switch (current) {
            // single char tokens
            case '{': {
                return KJsonTokenPair.OPEN_BRACE;
            }
            case '}': {
                return KJsonTokenPair.CLOSE_BRACE;
            }
            case '[': {
                return KJsonTokenPair.OPEN_SQUARE_BRACKET;
            }
            case ']': {
                return KJsonTokenPair.CLOSE_SQUARE_BRACKET;
            }
            case ',': {
                return KJsonTokenPair.COMMA;
            }
            case ':': {
                return KJsonTokenPair.SEMICOLON;
            }
            case '\"': {
                int currentColumn = state.column;

                StringBuilder string = new StringBuilder();

                while (true) {

                    int readData = this.next(state);

                    if (readData == -1) {
                        throw new KJsonTokenException(state.line, currentColumn);
                    }

                    char next = (char) readData;

                    if (next == '\\') {
                        readData = this.next(state, string, next);

                        if (readData == -1) {
                            throw new KJsonTokenException(state.line, currentColumn);
                        }

                        char afterNext = (char) readData;
                        string.append(afterNext);

                        if (afterNext == 'u') {

                            for (
                                int i = 0;
                                i < KStandardJsonTokenizer.UNICODE_DIGITS_COUNT;
                                i++
                            ) {
                                int readUnicodePart = this.next(state);
                                if (readUnicodePart == -1) {
                                    throw new KJsonTokenException(state.line, currentColumn);
                                }
                                
                                char u = (char) readUnicodePart;
                                if (Character.digit(u, KStandardJsonTokenizer.UNICODE_BASE) == -1) {
                                    throw new KJsonTokenException(state.line, currentColumn);
                                }

                                string.append(u);
                            }

                        }

                        continue;
                    } else if (next == '\"') {
                        state.rollback(this.next(state));
                        break;
                    } else if (next == '\n') {
                        throw new KJsonTokenException(state.line, currentColumn);
                    }

                    string.append(next);

                }

                return KJsonTokenPair.fromString(string.toString());

            }

            case '+':
            case '-':
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9': {

                return this.parseNumber(state, current);

            }

            // literal tokens
            default: {
                int currentColumn = state.column;

                StringBuilder literal = new StringBuilder(String.valueOf(current));

                int data = this.next(state);
                while (data != -1) {
                    char next = (char) data;
                    
                    if (!Character.isAlphabetic(next) || !isNotSpace(next)) {
                        state.rollback(data);
                        break;
                    }

                    data = this.next(state, literal, next);

                }

                String result = literal.toString();
                return switch (result) {
                    case "true" -> KJsonTokenPair.TRUE;
                    case "false" -> KJsonTokenPair.FALSE;
                    case "null" -> KJsonTokenPair.NULL;
                    default -> throw new KJsonTokenException(state.line, currentColumn);
                };

            }
        }
    }

    private KJsonTokenPair parseNumber(final State state, char current) throws KJsonTokenException {

        int currentColumn = state.column;
        StringBuilder numberCandidate = new StringBuilder(String.valueOf(current));

        int data = this.next(state);
        while (data != -1) {

            char next = (char) data;

            if (Character.isDigit(next)) {
                data = this.next(state, numberCandidate, next);

                if (data == -1) {
                    return KJsonTokenPair.fromInteger(Integer.parseInt(numberCandidate.toString()));
                }

            } else if (next == 'e' || next == 'E') {
                return this.parseExponential(state, next, numberCandidate, currentColumn);
            } else if (next == '.') {
                int nextData = this.next(state, numberCandidate, next);

                if (nextData == -1) {
                    throw new KJsonTokenException(state.line, currentColumn);
                }

                next = (char) nextData;
                if (!Character.isDigit(next)) {
                    throw new KJsonTokenException(state.line, currentColumn);
                }

                nextData = this.next(state, numberCandidate, next);
                while (nextData != -1) {
                    next = (char) nextData;

                    if (Character.isDigit(next)) {
                        nextData = this.next(state, numberCandidate, next);
                    } else if (next == 'e' || next == 'E') {
                        return this.parseExponential(
                            state, next, numberCandidate, currentColumn
                        );
                    } else {
                        state.rollback(nextData);
                        return KJsonTokenPair.fromFloat(
                            Float.parseFloat(numberCandidate.toString())
                        );
                    }
                }

                return KJsonTokenPair.fromFloat(Float.parseFloat(numberCandidate.toString()));

            } else {
                state.rollback(data);
                return KJsonTokenPair.fromInteger(Integer.parseInt(numberCandidate.toString()));
            }
        }

        throw new KJsonTokenException(state.line, currentColumn);
    }

    private KJsonTokenPair parseExponential(
        final State state,
        char next,
        final StringBuilder numberCandidate,
        int currentColumn
    ) throws KJsonTokenException {
        int data = this.next(state, numberCandidate, next);

        if (data == -1) {
            throw new KJsonTokenException(state.line, currentColumn);
        }

        next = (char) data;
        if (next != '-' && next != '+') {
            throw new KJsonTokenException(state.line, currentColumn);
        }

        data = this.next(state, numberCandidate, next);

        if (data == -1) {
            throw new KJsonTokenException(state.line, currentColumn);
        }

        next = (char) data;
        if (!Character.isDigit(next)) {
            throw new KJsonTokenException(state.line, currentColumn);
        }

        data = this.next(state, numberCandidate, next);
        while (data != -1) {

            next = (char) data;
            if (Character.isDigit(next)) {
                data = this.next(state, numberCandidate, next);
            } else {
                state.rollback(data);
                return KJsonTokenPair.fromFloat(Float.parseFloat(numberCandidate.toString()));
            }
        }

        return KJsonTokenPair.fromFloat(Float.parseFloat(numberCandidate.toString()));
    }
}
