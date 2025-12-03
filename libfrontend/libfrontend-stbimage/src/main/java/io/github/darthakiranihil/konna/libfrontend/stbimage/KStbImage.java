/*
 * Copyright 2025-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.darthakiranihil.konna.libfrontend.stbimage;

import java.nio.*;

/**
 * Library frontend of STBImage.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public interface KStbImage {

    int STBI_default = 0;
    int STBI_grey = 1;
    int STBI_grey_alpha = 2;
    int STBI_rgb = 3;
    int STBI_rgb_alpha = 4;

    ByteBuffer stbi_load(
        ByteBuffer filename,
        IntBuffer x,
        IntBuffer y,
        IntBuffer channels_in_file,
        int desired_channels
    );
    ByteBuffer stbi_load(
        CharSequence filename,
        IntBuffer x,
        IntBuffer y,
        IntBuffer channels_in_file,
        int desired_channels
    );
    ByteBuffer stbi_load_from_memory(
        ByteBuffer buffer,
        IntBuffer x,
        IntBuffer y,
        IntBuffer channels_in_file,
        int desired_channels
    );
    ByteBuffer stbi_load_from_callbacks(
        KStbIoCallbacks clbk,
        long user,
        IntBuffer x,
        IntBuffer y,
        IntBuffer channels_in_file,
        int desired_channels
    );
    ByteBuffer stbi_load_gif_from_memory(
        ByteBuffer buffer,
        LongBuffer delays,
        IntBuffer x,
        IntBuffer y,
        IntBuffer z,
        IntBuffer channels_in_file,
        int desired_channels
    );
    ShortBuffer stbi_load_16(
        ByteBuffer filename,
        IntBuffer x,
        IntBuffer y,
        IntBuffer channels_in_file,
        int desired_channels
    );
    ShortBuffer stbi_load_16(
        CharSequence filename,
        IntBuffer x,
        IntBuffer y,
        IntBuffer channels_in_file,
        int desired_channels
    );
    ShortBuffer stbi_load_16_from_memory(
        ByteBuffer buffer,
        IntBuffer x,
        IntBuffer y,
        IntBuffer channels_in_file,
        int desired_channels
    );
    ShortBuffer stbi_load_16_from_callbacks(
        KStbIoCallbacks clbk,
        long user,
        IntBuffer x,
        IntBuffer y,
        IntBuffer channels_in_file,
        int desired_channels
    );
    FloatBuffer stbi_loadf(
        ByteBuffer filename,
        IntBuffer x,
        IntBuffer y,
        IntBuffer channels_in_file,
        int desired_channels
    );
    FloatBuffer stbi_loadf(
        CharSequence filename,
        IntBuffer x,
        IntBuffer y,
        IntBuffer channels_in_file,
        int desired_channels
    );
    FloatBuffer stbi_loadf_from_memory(
        ByteBuffer buffer,
        IntBuffer x,
        IntBuffer y,
        IntBuffer channels_in_file,
        int desired_channels
    );
    FloatBuffer stbi_loadf_from_callbacks(
        KStbIoCallbacks clbk,
        long user,
        IntBuffer x,
        IntBuffer y,
        IntBuffer channels_in_file,
        int desired_channels
    );

    void stbi_hdr_to_ldr_gamma(float gamma);
    void stbi_hdr_to_ldr_scale(float scale);
    void stbi_ldr_to_hdr_gamma(float gamma);
    void stbi_ldr_to_hdr_scale(float scale);
    boolean stbi_is_hdr(ByteBuffer filename);
    boolean stbi_is_hdr(CharSequence filename);
    boolean stbi_is_hdr_from_memory(ByteBuffer buffer);
    boolean stbi_is_hdr_from_callbacks(KStbIoCallbacks clbk, long user);
    String stbi_failure_reason();
    void stbi_image_free(ShortBuffer retval_from_stbi_load);
    void stbi_image_free(FloatBuffer retval_from_stbi_load);
    boolean stbi_info(ByteBuffer filename, IntBuffer x, IntBuffer y, IntBuffer comp);
    boolean stbi_info(CharSequence filename, IntBuffer x, IntBuffer y, IntBuffer comp);
    boolean stbi_info_from_memory(ByteBuffer buffer, IntBuffer x, IntBuffer y, IntBuffer comp);
    boolean stbi_info_from_callbacks(
        KStbIoCallbacks clbk,
        long user,
        IntBuffer x,
        IntBuffer y,
        IntBuffer comp
    );
    boolean stbi_is_16_bit(ByteBuffer filename);
    boolean stbi_is_16_bit(CharSequence filename);
    boolean stbi_is_16_bit_from_memory(ByteBuffer buffer);
    boolean stbi_is_16_bit_from_callbacks(KStbIoCallbacks clbk, long user);
    void stbi_set_unpremultiply_on_load(boolean flag_true_if_should_unpremultiply);
    void stbi_convert_iphone_png_to_rgb(boolean flag_true_if_should_convert);
    void stbi_set_flip_vertically_on_load(boolean flag_true_if_should_flip);
    void stbi_convert_iphone_png_to_rgb_thread(boolean flag_true_if_should_convert);
    void stbi_set_flip_vertically_on_load_thread(int flag_true_if_should_flip);
    ByteBuffer stbi_zlib_decode_malloc_guesssize(ByteBuffer buffer, int initial_size);
    ByteBuffer stbi_zlib_decode_malloc_guesssize_headerflag(
        ByteBuffer buffer,
        int initial_size,
        boolean parse_header
    );

    ByteBuffer stbi_zlib_decode_malloc(ByteBuffer buffer);
    int stbi_zlib_decode_buffer(ByteBuffer obuffer, ByteBuffer ibuffer);
    ByteBuffer stbi_zlib_decode_noheader_malloc(ByteBuffer buffer);
    int stbi_zlib_decode_noheader_buffer(ByteBuffer obuffer, ByteBuffer ibuffer);

    ByteBuffer stbi_load(
        ByteBuffer filename,
        int[] x,
        int[] y,
        int[] channels_in_file,
        int desired_channels
    );
    ByteBuffer stbi_load(
        CharSequence filename,
        int[] x,
        int[] y,
        int[] channels_in_file,
        int desired_channels
    );
    ByteBuffer stbi_load_from_memory(
        ByteBuffer buffer,
        int[] x,
        int[] y,
        int[] channels_in_file,
        int desired_channels
    );
    ByteBuffer stbi_load_from_callbacks(
        KStbIoCallbacks clbk,
        long user,
        int[] x,
        int[] y,
        int[] channels_in_file,
        int desired_channels
    );
    ByteBuffer stbi_load_gif_from_memory(
        ByteBuffer buffer,
        LongBuffer delays,
        int[] x,
        int[] y,
        int[] z,
        int[] channels_in_file,
        int desired_channels
    );
    ShortBuffer stbi_load_16(
        ByteBuffer filename,
        int[] x,
        int[] y,
        int[] channels_in_file,
        int desired_channels
    );
    ShortBuffer stbi_load_16(
        CharSequence filename,
        int[] x,
        int[] y,
        int[] channels_in_file,
        int desired_channels
    );
    ShortBuffer stbi_load_16_from_memory(
        ByteBuffer buffer,
        int[] x,
        int[] y,
        int[] channels_in_file,
        int desired_channels
    );
    ShortBuffer stbi_load_16_from_callbacks(
        KStbIoCallbacks clbk,
        long user,
        int[] x,
        int[] y,
        int[] channels_in_file,
        int desired_channels
    );
    FloatBuffer stbi_loadf(
        ByteBuffer filename,
        int[] x,
        int[] y,
        int[] channels_in_file,
        int desired_channels
    );
    FloatBuffer stbi_loadf(
        CharSequence filename,
        int[] x,
        int[] y,
        int[] channels_in_file,
        int desired_channels
    );
    FloatBuffer stbi_loadf_from_memory(
        ByteBuffer buffer,
        int[] x,
        int[] y,
        int[] channels_in_file,
        int desired_channels
    );
    FloatBuffer stbi_loadf_from_callbacks(
        KStbIoCallbacks clbk,
        long user,
        int[] x,
        int[] y,
        int[] channels_in_file,
        int desired_channels
    );
    
    boolean stbi_info(ByteBuffer filename, int[] x, int[] y, int[] comp);
    boolean stbi_info(CharSequence filename, int[] x, int[] y, int[] comp);
    boolean stbi_info_from_memory(ByteBuffer buffer, int[] x, int[] y, int[] comp);
    boolean stbi_info_from_callbacks(KStbIoCallbacks clbk, long user, int[] x, int[] y, int[] comp);

}
