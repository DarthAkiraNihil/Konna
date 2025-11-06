package io.github.darthakiranihil.konna.core.input;

public interface KKeyListener {

    void keyPressed(KKeyEventData data);
    void keyReleased(KKeyEventData data);
    void keyHold(KKeyEventData data);

}
