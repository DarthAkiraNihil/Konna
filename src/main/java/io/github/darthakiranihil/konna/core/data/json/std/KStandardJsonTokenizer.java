package io.github.darthakiranihil.konna.core.data.json.std;

import io.github.darthakiranihil.konna.core.data.json.KJsonToken;
import io.github.darthakiranihil.konna.core.data.json.KJsonTokenPair;
import io.github.darthakiranihil.konna.core.data.json.KJsonTokenizer;
import io.github.darthakiranihil.konna.core.data.json.except.KJsonTokenException;

/**
 * Standard implementation of KJsonTokenizer
 */
public class KStandardJsonTokenizer extends KJsonTokenizer {

    private static class State {
        public int line;
        public int column;
        public int index;

        public State() {
            this.line = 1;
            this.column = 1;
            this.index = 0;
        }
    }

    private final State state;

    public KStandardJsonTokenizer(String source) {
        super(source);

        this.state = new State();
    }

    @Override
    public KJsonTokenPair getNextToken() throws KJsonTokenException {
        return this.scan();
    }

    private static boolean isNotSpace(char ch) {
        return ch != ' ' && ch != '\n' && ch != '\t';
    }

    private void next() {
        this.state.index++;
        this.state.column++;
    }

    private void next(StringBuilder builder, char ch) {
        this.next();
        builder.append(ch);
    }


    private KJsonTokenPair scan() throws KJsonTokenException {

        if (this.state.index >= this.source.length()) {
            return new KJsonTokenPair(KJsonToken.EOF, '\0');
        }

        while (true) {
            char current = this.source.charAt(this.state.index);
            switch (current) {
                case '\t':
                case '\r':
                case ' ': {
                    this.next();
                    break;
                }
                case '\n': {
                    this.state.index++;
                    this.state.line++;
                    this.state.column = 0;
                    break;
                }
                default: {
                    return this.processLiter(current);
                }
            }
        }
    }

    private KJsonTokenPair processLiter(char current) throws KJsonTokenException {
        int sourceLength = this.source.length();

        switch (current) {
            // single char tokens
            case '{': {
                this.next();
                return new KJsonTokenPair(KJsonToken.OPEN_BRACE, current);
            }
            case '}': {
                this.next();
                return new KJsonTokenPair(KJsonToken.CLOSE_BRACE, current);
            }
            case '[': {
                this.next();
                return new KJsonTokenPair(KJsonToken.OPEN_SQUARE_BRACKET, current);
            }
            case ']': {
                this.next();
                return new KJsonTokenPair(KJsonToken.CLOSE_SQUARE_BRACKET, current);
            }
            case ',': {
                this.next();
                return new KJsonTokenPair(KJsonToken.COMMA, current);
            }
            case ':': {
                this.next();
                return new KJsonTokenPair(KJsonToken.SEMICOLON, current);
            }
            case '\"': {
                int currentColumn = this.state.column;

                StringBuilder string = new StringBuilder();

                while (true) {

                    this.next();

                    if (this.state.index >= sourceLength) {
                        throw new KJsonTokenException(this.state.line, currentColumn);
                    }

                    char next = this.source.charAt(this.state.index);

                    if (next == '\\') {
                        this.next(string, next);

                        if (this.state.index >= sourceLength) {
                            throw new KJsonTokenException(this.state.line, currentColumn);
                        }

                        char afterNext = this.source.charAt(this.state.index);
                        string.append(afterNext);

                        if (afterNext == 'u') {
                            if (this.state.index + 4 >= sourceLength) {
                                throw new KJsonTokenException(this.state.line, currentColumn);
                            }

                            for (int i = 1; i < 5; i++) {
                                char u = this.source.charAt(this.state.index + i);
                                if (Character.digit(u, 16) == -1) {
                                    throw new KJsonTokenException(this.state.line, currentColumn);
                                }

                                string.append(u);
                            }

                            this.state.index += 4;
                            this.state.column += 4;

                            continue;

                        }
                    } else if (next == '\"') {
                        this.next();
                        break;
                    } else if (next == '\n') {
                        throw new KJsonTokenException(this.state.line, currentColumn);
                    }

                    string.append(next);

                }

                return new KJsonTokenPair(KJsonToken.STRING, string.toString());

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

                return this.parseNumber(current, sourceLength);

            }

            // literal tokens
            default: {
                int currentColumn = this.state.column;

                StringBuilder literal = new StringBuilder();

                char next;
                while (KStandardJsonTokenizer.isNotSpace(next = this.source.charAt(this.state.index)) && this.state.index < sourceLength) {
                    if (!Character.isAlphabetic(next)) {
                        break;
                    }

                    this.next(literal, next);

                }

                String result = literal.toString();
                return switch (result) {
                    case "true" -> {
                        this.next();
                        yield new KJsonTokenPair(KJsonToken.TRUE, result);
                    }
                    case "false" -> {
                        this.next();
                        yield new KJsonTokenPair(KJsonToken.FALSE, result);
                    }
                    case "null" -> {
                        this.next();
                        yield new KJsonTokenPair(KJsonToken.NULL, result);
                    }
                    default -> throw new KJsonTokenException(this.state.line, currentColumn);
                };

            }
        }
    }

    @SuppressWarnings("DuplicateExpressions")
    private KJsonTokenPair parseNumber(char current, int sourceLength) throws KJsonTokenException {

        int currentColumn = this.state.column;
        StringBuilder numberCandidate = new StringBuilder(String.valueOf(current));

        this.next();

        while (this.state.index < sourceLength) {

            char next = this.source.charAt(this.state.index);

            if (Character.isDigit(next)) {
                this.next(numberCandidate, next);

                if (this.state.index >= sourceLength) {
                    return new KJsonTokenPair(KJsonToken.NUMBER_INT, Integer.parseInt(numberCandidate.toString()));
                }

            } else if (next == 'e' || next == 'E') {
                return this.parseExponential(next, numberCandidate, sourceLength, currentColumn);
            } else if (next == '.') {
                this.next(numberCandidate, next);

                if (this.state.index >= sourceLength) {
                    throw new KJsonTokenException(this.state.line, currentColumn);
                }

                next = this.source.charAt(this.state.index);
                if (!Character.isDigit(next)) {
                    throw new KJsonTokenException(this.state.line, currentColumn);
                }

                this.next(numberCandidate, next);

                while (this.state.index < sourceLength) {

                    next = this.source.charAt(this.state.index);
                    if (Character.isDigit(next)) {
                        this.next(numberCandidate, next);
                    } else if (next == 'e' || next == 'E') {
                        return this.parseExponential(next, numberCandidate, sourceLength, currentColumn);
                    } else {
                        return new KJsonTokenPair(KJsonToken.NUMBER_FLOAT, Float.parseFloat(numberCandidate.toString()));
                    }
                }

            } else {
                return new KJsonTokenPair(KJsonToken.NUMBER_INT, Integer.parseInt(numberCandidate.toString()));
            }
        }

        throw new KJsonTokenException(this.state.line, currentColumn);
    }

    private KJsonTokenPair parseExponential(char next, StringBuilder numberCandidate, int sourceLength, int currentColumn) throws KJsonTokenException {
        this.next(numberCandidate, next);

        if (this.state.index >= sourceLength) {
            throw new KJsonTokenException(this.state.line, currentColumn);
        }

        next = this.source.charAt(this.state.index);
        if (next != '-' && next != '+') {
            throw new KJsonTokenException(this.state.line, currentColumn);
        }

        this.next(numberCandidate, next);

        if (this.state.index >= sourceLength) {
            throw new KJsonTokenException(this.state.line, currentColumn);
        }

        next = this.source.charAt(this.state.index);
        if (!Character.isDigit(next)) {
            throw new KJsonTokenException(this.state.line, currentColumn);
        }

        this.next(numberCandidate, next);

        while (this.state.index < sourceLength) {

            next = this.source.charAt(this.state.index);
            if (Character.isDigit(next)) {
                this.next(numberCandidate, next);
            } else {
                return new KJsonTokenPair(KJsonToken.NUMBER_FLOAT, Float.parseFloat(numberCandidate.toString()));
            }
        }

        throw new KJsonTokenException(this.state.line, currentColumn);
    }
}
