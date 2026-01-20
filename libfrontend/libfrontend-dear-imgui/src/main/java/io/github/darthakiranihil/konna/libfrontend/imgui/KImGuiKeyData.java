package io.github.darthakiranihil.konna.libfrontend.imgui;

/**
 * Interface representing ImGuiKeyData of Dear ImGui.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public interface KImGuiKeyData {

    boolean getDown();

    void setDown(boolean value);
    float getDownDuration();

    void setDownDuration(float value);
    float getDownDurationPrev();

    void setDownDurationPrev(float value);
    float getAnalogValue();

    void setAnalogValue(float value);

}
