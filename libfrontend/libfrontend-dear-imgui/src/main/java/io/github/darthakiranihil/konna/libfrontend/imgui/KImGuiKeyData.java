package io.github.darthakiranihil.konna.libfrontend.imgui;

public interface KImGuiKeyData {

    boolean getDown();

    void setDown(final boolean value);
    float getDownDuration();

    void setDownDuration(final float value);
    float getDownDurationPrev();

    void setDownDurationPrev(final float value);
    float getAnalogValue();

    void setAnalogValue(final float value);

}
