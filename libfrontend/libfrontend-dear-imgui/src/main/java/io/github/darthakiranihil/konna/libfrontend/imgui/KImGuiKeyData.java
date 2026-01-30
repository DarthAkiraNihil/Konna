package io.github.darthakiranihil.konna.libfrontend.imgui;

import io.github.darthakiranihil.konna.core.struct.KDestroyable;

/**
 * Interface representing ImGuiKeyData of Dear ImGui.
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
public interface KImGuiKeyData extends KDestroyable {

    boolean getDown();

    void setDown(boolean value);
    float getDownDuration();

    void setDownDuration(float value);
    float getDownDurationPrev();

    void setDownDurationPrev(float value);
    float getAnalogValue();

    void setAnalogValue(float value);

}
