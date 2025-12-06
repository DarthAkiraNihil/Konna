package io.github.darthakiranihil.konna.core.data.json;

/**
 * Represents a json validator that check if the json value
 * is correct according to checks, that are implementation-defined.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
@FunctionalInterface
public interface KJsonValidator {

    /**
     * Validates a json value.
     * @throws  io.github.darthakiranihil.konna.core.data.json.except.KJsonValidationError
     *          if json value does not pass validation checks
     * @param value Json value to validate
     */
    void validate(KJsonValue value);

}
