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

import io.github.darthakiranihil.konna.core.data.json.except.KJsonParseException;
import io.github.darthakiranihil.konna.core.data.json.except.KJsonTokenException;
import io.github.darthakiranihil.konna.core.data.json.*;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Standard implementation of {@link KJsonParser}.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public class KStandardJsonParser implements KJsonParser {

    private final KJsonTokenizer tokenizer;

    /**
     * Constructs parser with concrete tokenizer.
     * @param tokenizer Any Json tokenizer
     */
    public KStandardJsonParser(final KJsonTokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    @Override
    public KJsonValue parse(final String string) throws KJsonParseException {
        return this.parse(new StringReader(string));
    }

    @Override
    public KJsonValue parse(final InputStream stream) throws KJsonParseException {
        return this.parse(new InputStreamReader(stream));
    }

    @Override
    public KJsonValue parse(final Reader reader) throws KJsonParseException {
        int sequenceToken = this.tokenizer.addSource(reader);

        KJsonValue result = this.value(sequenceToken);

        KJsonTokenPair last = this.getTokenOrFail(sequenceToken);
        if (last.token() != KJsonToken.EOF) {
            throw new KJsonParseException(last.token());
        }

        return result;
    }

    @Override
    public KJsonValue parse(final char[] chars) throws KJsonParseException {
        return this.parse(new CharArrayReader(chars));
    }

    private KJsonValue value(
        final KJsonTokenPair token,
        int sequenceToken
    ) throws KJsonParseException {
        return switch (token.token()) {
            case STRING -> new KJsonValue(KJsonValueType.STRING, token.value());
            case NUMBER_INT -> new KJsonValue(KJsonValueType.NUMBER_INT, token.value());
            case NUMBER_FLOAT -> new KJsonValue(KJsonValueType.NUMBER_FLOAT, token.value());
            case TRUE -> new KJsonValue(KJsonValueType.BOOLEAN, true);
            case FALSE -> new KJsonValue(KJsonValueType.BOOLEAN, false);
            case NULL -> new KJsonValue(KJsonValueType.NULL, null);
            case OPEN_BRACE -> this.object(sequenceToken);
            case OPEN_SQUARE_BRACKET -> this.array(sequenceToken);
            default -> throw new KJsonParseException(token.token());
        };
    }

    private KJsonValue value(int sequenceToken) throws KJsonParseException {
        KJsonTokenPair token = this.getTokenOrFail(sequenceToken);

        return switch (token.token()) {
            case STRING -> new KJsonValue(KJsonValueType.STRING, token.value());
            case NUMBER_INT -> new KJsonValue(KJsonValueType.NUMBER_INT, token.value());
            case NUMBER_FLOAT -> new KJsonValue(KJsonValueType.NUMBER_FLOAT, token.value());
            case TRUE -> new KJsonValue(KJsonValueType.BOOLEAN, true);
            case FALSE -> new KJsonValue(KJsonValueType.BOOLEAN, false);
            case NULL -> new KJsonValue(KJsonValueType.NULL, null);
            case OPEN_BRACE -> this.object(sequenceToken);
            case OPEN_SQUARE_BRACKET -> this.array(sequenceToken);
            default -> throw new KJsonParseException(token.token());
        };

    }

    private KJsonValue object(int sequenceToken) throws KJsonParseException {
        KJsonTokenPair token = this.getTokenOrFail(sequenceToken);

        if (token.token() == KJsonToken.CLOSE_BRACE) {
            return KJsonValue.fromMap(new LinkedHashMap<>());
        }

        Map<String, KJsonValue> result = new LinkedHashMap<>();

        do {

            if (token.token() != KJsonToken.STRING) {
                throw new KJsonParseException(token.token());
            }

            String key = (String) token.value();

            token = this.getTokenOrFail(sequenceToken);
            if (token.token() != KJsonToken.SEMICOLON) {
                throw new KJsonParseException(token.token());
            }

            result.put(key, this.value(sequenceToken));

            token = this.getTokenOrFail(sequenceToken);
            if (token.token() == KJsonToken.COMMA) {
                token = this.getTokenOrFail(sequenceToken);
            }

        } while (token.token() != KJsonToken.CLOSE_BRACE);

        return KJsonValue.fromMap(result);

    }

    private KJsonValue array(int sequenceToken) throws KJsonParseException {
        KJsonTokenPair token = this.getTokenOrFail(sequenceToken);

        if (token.token() == KJsonToken.CLOSE_SQUARE_BRACKET) {
            return KJsonValue.fromList(new LinkedList<>());
        }

        List<KJsonValue> result = new LinkedList<>();

        do {
            result.add(this.value(token, sequenceToken));

            token = this.getTokenOrFail(sequenceToken);
            if (token.token() == KJsonToken.COMMA) {
                token = this.getTokenOrFail(sequenceToken);
            }

        } while (token.token() != KJsonToken.CLOSE_SQUARE_BRACKET);

        return KJsonValue.fromList(result);
    }

    private KJsonTokenPair getTokenOrFail(int sequenceToken) throws KJsonParseException {
        try {
            return this.tokenizer.getNextToken(sequenceToken);
        } catch (KJsonTokenException e) {
            throw new KJsonParseException(e);
        }
    }
}
