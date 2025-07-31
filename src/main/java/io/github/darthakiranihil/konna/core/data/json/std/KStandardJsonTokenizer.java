package io.github.darthakiranihil.konna.core.data.json.std;

import io.github.darthakiranihil.konna.core.data.json.KJsonToken;
import io.github.darthakiranihil.konna.core.data.json.KJsonTokenPair;
import io.github.darthakiranihil.konna.core.data.json.KJsonTokenizer;
import io.github.darthakiranihil.konna.core.data.json.except.KJsonTokenException;

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


    private KJsonTokenPair scan() throws KJsonTokenException {
        while (true) {
            char current = this.source.charAt(this.state.index);
            switch (current) {
                case '\t':
                case ' ': {
                    this.state.index++;
                    this.state.column++;
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

    // TODO: liters processing
    private KJsonTokenPair processLiter(char current) throws KJsonTokenException {
        int sourceLength = this.source.length();

        switch (current) {
            // single char tokens
            case '{': {
                this.state.index++;
                this.state.column++;
                return new KJsonTokenPair(KJsonToken.OPEN_BRACE, current);
            }
            case '}': {
                this.state.index++;
                this.state.column++;
                return new KJsonTokenPair(KJsonToken.CLOSE_BRACE, current);
            }
            case '[': {
                this.state.index++;
                this.state.column++;
                return new KJsonTokenPair(KJsonToken.OPEN_SQUARE_BRACKET, current);
            }
            case ']': {
                this.state.index++;
                this.state.column++;
                return new KJsonTokenPair(KJsonToken.CLOSE_SQUARE_BRACKET, current);
            }
            case ',': {
                this.state.index++;
                this.state.column++;
                return new KJsonTokenPair(KJsonToken.COMMA, current);
            }
            case ':': {
                this.state.index++;
                this.state.column++;
                return new KJsonTokenPair(KJsonToken.SEMICOLON, current);
            }
            case '\"': {
                int currentColumn = this.state.column;

                StringBuilder string = new StringBuilder();

                while (true) {

                    this.state.index++;
                    this.state.column++;

                    if (this.state.index >= sourceLength) {
                        throw new KJsonTokenException(this.state.line, currentColumn);
                    }

                    char next = this.source.charAt(this.state.index);

                    if (next == '\\') {
                        string.append(next);

                        this.state.index++;
                        this.state.column++;

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

                        }
                    } else if (next == '\"') {
                        this.state.index++;
                        this.state.column++;
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
                int currentColumn = this.state.column;

                StringBuilder numberCandidate = new StringBuilder(String.valueOf(current));

                char next;
                while (KStandardJsonTokenizer.isNotSpace(next = this.source.charAt(this.state.index)) && this.state.index < sourceLength) {
                    if (
                            !Character.isDigit(next)
                        &&  next != 'e'
                        &&  next != 'E'
                        &&  next != '+'
                        &&  next != '-'
                        &&  next != '.'
                        ) {
                        throw new KJsonTokenException(this.state.line, currentColumn);
                    }

                    numberCandidate.append(next);

                    this.state.index++;
                    this.state.column++;
                }

                String result = numberCandidate.toString();
                if (result.contains("e") || result.contains("E") || result.contains(".")) {
                    try {
                        return new KJsonTokenPair(KJsonToken.NUMBER, Double.parseDouble(result));
                    } catch (NumberFormatException e) {
                        throw new KJsonTokenException(this.state.line, currentColumn);
                    }
                }

                try {
                    return new KJsonTokenPair(KJsonToken.NUMBER, Integer.parseInt(result));
                } catch (NumberFormatException e) {
                    throw new KJsonTokenException(this.state.line, currentColumn);
                }

            }

            // literal tokens
            default: {
                int currentColumn = this.state.column;

                StringBuilder literal = new StringBuilder(String.valueOf(current));

                char next;
                while (KStandardJsonTokenizer.isNotSpace(next = this.source.charAt(this.state.index)) && this.state.index < sourceLength) {
                    if (!Character.isAlphabetic(next)) {
                        throw new KJsonTokenException(this.state.line, currentColumn);
                    }

                    literal.append(next);

                    this.state.index++;
                    this.state.column++;
                }

                String result = literal.toString();
                switch (result) {
                    case "true": {
                        this.state.index++;
                        this.state.column++;
                        return new KJsonTokenPair(KJsonToken.TRUE, result);
                    }
                    case "false": {
                        this.state.index++;
                        this.state.column++;
                        return new KJsonTokenPair(KJsonToken.FALSE, result);
                    }
                    case "null": {
                        this.state.index++;
                        this.state.column++;
                        return new KJsonTokenPair(KJsonToken.NULL, result);
                    }
                }

                throw new KJsonTokenException(this.state.line, currentColumn);
            }
        }
    }
}
