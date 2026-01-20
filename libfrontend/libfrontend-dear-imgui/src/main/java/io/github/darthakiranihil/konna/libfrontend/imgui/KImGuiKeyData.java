package io.github.darthakiranihil.konna.libfrontend.imgui;

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
