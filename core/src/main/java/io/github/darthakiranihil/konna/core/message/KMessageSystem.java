package io.github.darthakiranihil.konna.core.message;

public interface KMessageSystem {

    void deliverMessage(KMessage message);
    void deliverMessageSync(KMessage message);
    KMessageSystem addMessageRoute(String messageId, String destinationEndpoint, KTunnel... tunnels);

}
