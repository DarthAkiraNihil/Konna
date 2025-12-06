package io.github.darthakiranihil.konna.core.message;

import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;

/**
 * Convenience base class which purpose is to simplify
 * sending messages by using only internal message id instead of full id.
 * Usually a component should create corresponding messenger and pass it to
 * component context (so it is used everywhere inside the component)
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public abstract class KMessenger extends KObject {

    /**
     * Reference to the message system.
     */
    protected final KMessageSystem messageSystem;
    /**
     * Prefix to be attached to internal message id on sending.
     */
    protected final String messageIdPrefix;

    /**
     * Base constructor.
     * @param messageSystem Parent message system that messenger sends messages to
     * @param messageIdPrefix Prefix to be attached to internal message id on sending
     */
    public KMessenger(
        @KInject final KMessageSystem messageSystem,
        final String messageIdPrefix
    ) {
        super(
            String.format("messenger_%s", messageIdPrefix),
            KStructUtils.setOfTags(KTag.DefaultTags.SYSTEM)
        );

        this.messageIdPrefix = messageIdPrefix;
        this.messageSystem = messageSystem;
    }

    /**
     * Creates a regular message and passes it to the message system to process.
     * This operation is asynchronous.
     * @param internalMessageId Internal message id (does not include component name)
     * @param body Message body
     */
    public abstract void sendRegular(String internalMessageId, KUniversalMap body);
    /**
     * Creates a system message and passes it to the message system to process.
     * This operation is asynchronous.
     * @param internalMessageId Internal message id (does not include component name)
     * @param body Message body
     */
    public abstract void sendSystem(String internalMessageId, KUniversalMap body);
    /**
     * Creates a debug message and passes it to the message system to process.
     * This operation is asynchronous.
     * @param internalMessageId Internal message id (does not include component name)
     * @param body Message body
     */
    public abstract void sendDebug(String internalMessageId, KUniversalMap body);
    /**
     * Creates a metrics message and passes it to the message system to process.
     * This operation is asynchronous.
     * @param internalMessageId Internal message id (does not include component name)
     * @param body Message body
     */
    public abstract void sendMetrics(String internalMessageId, KUniversalMap body);

    /**
     * Creates a regular message and passes it to the message system to process.
     * This operation is synchronous, which is useful for testing.
     * @param internalMessageId Internal message id (does not include component name)
     * @param body Message body
     */
    public abstract void sendRegularSync(String internalMessageId, KUniversalMap body);
    /**
     * Creates a system message and passes it to the message system to process.
     * This operation is synchronous, which is useful for testing.
     * @param internalMessageId Internal message id (does not include component name)
     * @param body Message body
     */
    public abstract void sendSystemSync(String internalMessageId, KUniversalMap body);
    /**
     * Creates a debug message and passes it to the message system to process.
     * This operation is synchronous, which is useful for testing.
     * @param internalMessageId Internal message id (does not include component name)
     * @param body Message body
     */
    public abstract void sendDebugSync(String internalMessageId, KUniversalMap body);
    /**
     * Creates a metrics message and passes it to the message system to process.
     * This operation is synchronous, which is useful for testing.
     * @param internalMessageId Internal message id (does not include component name)
     * @param body Message body
     */
    public abstract void sendMetricsSync(String internalMessageId, KUniversalMap body);

}
