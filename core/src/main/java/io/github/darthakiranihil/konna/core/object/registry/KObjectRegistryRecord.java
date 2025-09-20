package io.github.darthakiranihil.konna.core.object.registry;

import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KObjectInstantiationType;

/**
 * Representation of a registry record of {@link KObjectRegistry}.
 * @param object Registered object
 * @param instantiationType Instantiation type of the object.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public record KObjectRegistryRecord(
    KObject object,
    KObjectInstantiationType instantiationType) {

}
