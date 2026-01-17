package io.github.darthakiranihil.konna.libfrontend.stb;

import org.jspecify.annotations.Nullable;

import java.nio.IntBuffer;

public interface KStbTtPackRange {

    float getFont_size();
    int getFirst_unicode_codepoint_in_range();
    @Nullable IntBuffer getArray_of_unicode_codepoints();
    int getNum_chars();
    KStbTtPackedChar[] getChardata_for_range();
    byte getH_oversample();
    byte getV_oversample();

    void setFont_size(float value);
    void setFirst_unicode_codepoint_in_range(int value);
    void setArray_of_unicode_codepoints(IntBuffer value);
    void setNum_chars(int value);
    void setChardata_for_range(KStbTtPackedChar[] value);
    void setH_oversample(byte value);
    void setV_oversample(byte value);


}
