package io.github.darthakiranihil.konna.core.data.json.std;

import io.github.darthakiranihil.konna.core.data.json.except.KJsonParseException;
import io.github.darthakiranihil.konna.core.data.json.except.KJsonTokenException;
import io.github.darthakiranihil.konna.core.data.json.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Standard implementation of KJsonParser
 */
public class KStandardJsonParser implements KJsonParser {


    @Override
    public KJsonValue parse(String string) throws KJsonParseException {
        KJsonTokenizer tokenizer = new KStandardJsonTokenizer(string);


        KJsonValue result = this.value(tokenizer);

        KJsonTokenPair last = this.getTokenOrFail(tokenizer);
        if (last.token() != KJsonToken.EOF) {
            throw new KJsonParseException(last);
        }

        return result;

    }

    private KJsonValue value(KJsonTokenizer tokenizer, KJsonTokenPair token) throws KJsonParseException {
        return switch (token.token()) {
            case STRING -> new KJsonValue(KJsonValueType.STRING, token.value());
            case NUMBER_INT, NUMBER_FLOAT -> new KJsonValue(KJsonValueType.NUMBER, token.value());
            case TRUE -> new KJsonValue(KJsonValueType.BOOLEAN, true);
            case FALSE -> new KJsonValue(KJsonValueType.BOOLEAN, false);
            case NULL -> new KJsonValue(KJsonValueType.NULL, null);
            case OPEN_BRACE -> this.object(tokenizer);
            case OPEN_SQUARE_BRACKET -> this.array(tokenizer);
            default -> throw new KJsonParseException(token);
        };
    }

    private KJsonValue value(KJsonTokenizer tokenizer) throws KJsonParseException {
        KJsonTokenPair token = this.getTokenOrFail(tokenizer);

        return switch (token.token()) {
            case STRING -> new KJsonValue(KJsonValueType.STRING, token.value());
            case NUMBER_INT, NUMBER_FLOAT -> new KJsonValue(KJsonValueType.NUMBER, token.value());
            case TRUE -> new KJsonValue(KJsonValueType.BOOLEAN, true);
            case FALSE -> new KJsonValue(KJsonValueType.BOOLEAN, false);
            case NULL -> new KJsonValue(KJsonValueType.NULL, null);
            case OPEN_BRACE -> this.object(tokenizer);
            case OPEN_SQUARE_BRACKET -> this.array(tokenizer);
            default -> throw new KJsonParseException(token);
        };

    }

    private KJsonValue object(KJsonTokenizer tokenizer) throws KJsonParseException{
        KJsonTokenPair token = this.getTokenOrFail(tokenizer);

        if (token.token() == KJsonToken.CLOSE_BRACE) {
            return KJsonValue.fromMap(new HashMap<>());
        }

        Map<String, KJsonValue> result = new HashMap<>();

        do {

            if (token.token() != KJsonToken.STRING) {
                throw new KJsonParseException(token.token());
            }

            String key = (String) token.value();

            token = this.getTokenOrFail(tokenizer);
            if (token.token() != KJsonToken.SEMICOLON) {
                throw new KJsonParseException(token.token());
            }

            result.put(key, this.value(tokenizer));

            token = this.getTokenOrFail(tokenizer);
            if (token.token() == KJsonToken.COMMA) {
                token = this.getTokenOrFail(tokenizer);
            }

        } while (token.token() != KJsonToken.CLOSE_BRACE);

        return KJsonValue.fromMap(result);

    }

    private KJsonValue array(KJsonTokenizer tokenizer) throws KJsonParseException{
        KJsonTokenPair token = this.getTokenOrFail(tokenizer);

        if (token.token() == KJsonToken.CLOSE_SQUARE_BRACKET) {
            return KJsonValue.fromList(new LinkedList<>());
        }

        List<KJsonValue> result = new LinkedList<>();

        do {
            result.add(this.value(tokenizer, token));

            token = this.getTokenOrFail(tokenizer);
            if (token.token() == KJsonToken.COMMA) {
                token = this.getTokenOrFail(tokenizer);
            }

        } while (token.token() != KJsonToken.CLOSE_SQUARE_BRACKET);

        return KJsonValue.fromList(result);
    }

    private KJsonTokenPair getTokenOrFail(KJsonTokenizer tokenizer) throws KJsonParseException {
        try {
            return tokenizer.getNextToken();
        } catch (KJsonTokenException e) {
            throw new KJsonParseException(e);
        }
    }
}
