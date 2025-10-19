package io.github.darthakiranihil.konna.core.message;

import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.util.KStructUtils;

public abstract class KMessenger extends KObject {

    protected final KMessageSystem messageSystem;
    protected final String messageIdSpecifier;

    public KMessenger(
        @KInject final KMessageSystem messageSystem,
        final String messageIdSpecifier
    ) {
        super(
            String.format("messenger_%s", messageIdSpecifier),
            KStructUtils.setOfTags(KTag.DefaultTags.SYSTEM)
        );

        this.messageIdSpecifier = messageIdSpecifier;
        this.messageSystem = messageSystem;
    }

    public abstract void sendRegular(String shortMessageId, KUniversalMap body);
    public abstract void sendSystem(String shortMessageId, KUniversalMap body);
    public abstract void sendDebug(String shortMessageId, KUniversalMap body);
    public abstract void sendMetrics(String shortMessageId, KUniversalMap body);

    public abstract void sendRegularSync(String shortMessageId, KUniversalMap body);
    public abstract void sendSystemSync(String shortMessageId, KUniversalMap body);
    public abstract void sendDebugSync(String shortMessageId, KUniversalMap body);
    public abstract void sendMetricsSync(String shortMessageId, KUniversalMap body);

}
