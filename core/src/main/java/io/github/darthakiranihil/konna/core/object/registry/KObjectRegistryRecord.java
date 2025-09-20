package io.github.darthakiranihil.konna.core.object.registry;

import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KObjectInstantiationType;

public record KObjectRegistryRecord(
    KObject object,
    KObjectInstantiationType instantiationType) {

}
