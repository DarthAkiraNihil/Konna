package io.github.darthakiranihil.konna.core.data.json.std;

import io.github.darthakiranihil.konna.core.data.json.KJsonTokenPair;
import io.github.darthakiranihil.konna.core.data.json.KJsonTokenizer;
import io.github.darthakiranihil.konna.core.data.json.except.KJsonTokenException;

public class KStandardJsonTokenizer extends KJsonTokenizer {

    private class State {
        public int line;
        public int column;
        public int index;

        public State() {
            this.line = 1;
            this.column = 1;
            this.index = 0;
        }
    }

    private State state;

    public KStandardJsonTokenizer(String source) {
        super(source);

        this.state = new State();
    }

    @Override
    public KJsonTokenPair getNextToken() throws KJsonTokenException {
        return this.scan();
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
                    return this.processLiter();
                }
            }
        }
    }

    // TODO: liters processing
    private KJsonTokenPair processLiter() throws KJsonTokenException {
        return null;
    }
}
