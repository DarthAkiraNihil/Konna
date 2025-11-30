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

package io.github.darthakiranihil.konna.backend.lwjgl;

import io.github.darthakiranihil.konna.libfrontend.stbimage.KStbImage;
import io.github.darthakiranihil.konna.libfrontend.stbimage.KStbIoCallbacks;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.stb.STBIIOCallbacks;
import org.lwjgl.stb.STBImage;

import java.nio.*;

public final class KStbImageLwjgl implements KStbImage {

    private static STBIIOCallbacks wrap(KStbIoCallbacks callbacks) {
        STBIIOCallbacks internal = STBIIOCallbacks.create();
        internal.set(
            (user, data, size) -> callbacks.read().invoke(user, data, size),
            (user, n) -> callbacks.skip().invoke(user, n),
            (user) -> callbacks.eof().invoke(user)
        );
        return internal;
    }

    @Override
    public ByteBuffer stbi_load(ByteBuffer filename, IntBuffer x, IntBuffer y, IntBuffer channels_in_file, int desired_channels) {
        return STBImage.stbi_load(filename, x, y, channels_in_file, desired_channels);
    }

    @Override
    public ByteBuffer stbi_load(CharSequence filename, IntBuffer x, IntBuffer y, IntBuffer channels_in_file, int desired_channels) {
        return STBImage.stbi_load(filename, x, y, channels_in_file, desired_channels);
    }

    @Override
    public ByteBuffer stbi_load_from_memory(ByteBuffer buffer, IntBuffer x, IntBuffer y, IntBuffer channels_in_file, int desired_channels) {
        return STBImage.stbi_load_from_memory(buffer, x, y, channels_in_file, desired_channels);
    }

    @Override
    public ByteBuffer stbi_load_from_callbacks(KStbIoCallbacks clbk, long user, IntBuffer x, IntBuffer y, IntBuffer channels_in_file, int desired_channels) {
        return STBImage.stbi_load_from_callbacks(KStbImageLwjgl.wrap(clbk), user, x, y, channels_in_file, desired_channels);
    }

    @Override
    public ByteBuffer stbi_load_gif_from_memory(ByteBuffer buffer, LongBuffer delays, IntBuffer x, IntBuffer y, IntBuffer z, IntBuffer channels_in_file, int desired_channels) {
        PointerBuffer delaysBuffer = BufferUtils.createPointerBuffer(delays.capacity());
        delaysBuffer.put(delays);
        return STBImage.stbi_load_gif_from_memory(buffer, delaysBuffer, x, y, z, channels_in_file, desired_channels);
    }

    @Override
    public ShortBuffer stbi_load_16(ByteBuffer filename, IntBuffer x, IntBuffer y, IntBuffer channels_in_file, int desired_channels) {
        return STBImage.stbi_load_16(filename, x, y, channels_in_file, desired_channels);
    }

    @Override
    public ShortBuffer stbi_load_16(CharSequence filename, IntBuffer x, IntBuffer y, IntBuffer channels_in_file, int desired_channels) {
        return STBImage.stbi_load_16(filename, x, y, channels_in_file, desired_channels);
    }

    @Override
    public ShortBuffer stbi_load_16_from_memory(ByteBuffer buffer, IntBuffer x, IntBuffer y, IntBuffer channels_in_file, int desired_channels) {
        return STBImage.stbi_load_16_from_memory(buffer, x, y, channels_in_file, desired_channels);
    }

    @Override
    public ShortBuffer stbi_load_16_from_callbacks(KStbIoCallbacks clbk, long user, IntBuffer x, IntBuffer y, IntBuffer channels_in_file, int desired_channels) {
        return STBImage.stbi_load_16_from_callbacks(KStbImageLwjgl.wrap(clbk), user, x, y, channels_in_file, desired_channels);
    }

    @Override
    public FloatBuffer stbi_loadf(ByteBuffer filename, IntBuffer x, IntBuffer y, IntBuffer channels_in_file, int desired_channels) {
        return STBImage.stbi_loadf(filename, x, y, channels_in_file, desired_channels);
    }

    @Override
    public FloatBuffer stbi_loadf(CharSequence filename, IntBuffer x, IntBuffer y, IntBuffer channels_in_file, int desired_channels) {
        return STBImage.stbi_loadf(filename, x, y, channels_in_file, desired_channels);
    }

    @Override
    public FloatBuffer stbi_loadf_from_memory(ByteBuffer buffer, IntBuffer x, IntBuffer y, IntBuffer channels_in_file, int desired_channels) {
        return STBImage.stbi_loadf_from_memory(buffer, x, y, channels_in_file, desired_channels);
    }

    @Override
    public FloatBuffer stbi_loadf_from_callbacks(KStbIoCallbacks clbk, long user, IntBuffer x, IntBuffer y, IntBuffer channels_in_file, int desired_channels) {
        return STBImage.stbi_loadf_from_callbacks(KStbImageLwjgl.wrap(clbk), user, x, y, channels_in_file, desired_channels);
    }

    @Override
    public void stbi_hdr_to_ldr_gamma(float gamma) {
        STBImage.stbi_hdr_to_ldr_gamma(gamma);
    }

    @Override
    public void stbi_hdr_to_ldr_scale(float scale) {
        STBImage.stbi_hdr_to_ldr_scale(scale);
    }

    @Override
    public void stbi_ldr_to_hdr_gamma(float gamma) {
        STBImage.stbi_ldr_to_hdr_gamma(gamma);
    }

    @Override
    public void stbi_ldr_to_hdr_scale(float scale) {
        STBImage.stbi_ldr_to_hdr_scale(scale);
    }

    @Override
    public boolean stbi_is_hdr(ByteBuffer filename) {
        return STBImage.stbi_is_hdr(filename);
    }

    @Override
    public boolean stbi_is_hdr(CharSequence filename) {
        return STBImage.stbi_is_hdr(filename);
    }

    @Override
    public boolean stbi_is_hdr_from_memory(ByteBuffer buffer) {
        return STBImage.stbi_is_hdr_from_memory(buffer);
    }

    @Override
    public boolean stbi_is_hdr_from_callbacks(KStbIoCallbacks clbk, long user) {
        return STBImage.stbi_is_hdr_from_callbacks(KStbImageLwjgl.wrap(clbk), user);
    }

    @Override
    public String stbi_failure_reason() {
        return STBImage.stbi_failure_reason();
    }

    @Override
    public void stbi_image_free(ShortBuffer retval_from_stbi_load) {
        STBImage.stbi_image_free(retval_from_stbi_load);
    }

    @Override
    public void stbi_image_free(FloatBuffer retval_from_stbi_load) {
        STBImage.stbi_image_free(retval_from_stbi_load);
    }

    @Override
    public boolean stbi_info(ByteBuffer filename, IntBuffer x, IntBuffer y, IntBuffer comp) {
        return STBImage.stbi_info(filename, x, y, comp);
    }

    @Override
    public boolean stbi_info(CharSequence filename, IntBuffer x, IntBuffer y, IntBuffer comp) {
        return STBImage.stbi_info(filename, x, y, comp);
    }

    @Override
    public boolean stbi_info_from_memory(ByteBuffer buffer, IntBuffer x, IntBuffer y, IntBuffer comp) {
        return STBImage.stbi_info_from_memory(buffer, x, y, comp);
    }

    @Override
    public boolean stbi_info_from_callbacks(KStbIoCallbacks clbk, long user, IntBuffer x, IntBuffer y, IntBuffer comp) {
        return STBImage.stbi_info_from_callbacks(KStbImageLwjgl.wrap(clbk), user, x, y, comp);
    }

    @Override
    public boolean stbi_is_16_bit(ByteBuffer filename) {
        return STBImage.stbi_is_16_bit(filename);
    }

    @Override
    public boolean stbi_is_16_bit(CharSequence filename) {
        return STBImage.stbi_is_16_bit(filename);
    }

    @Override
    public boolean stbi_is_16_bit_from_memory(ByteBuffer buffer) {
        return STBImage.stbi_is_16_bit_from_memory(buffer);
    }

    @Override
    public boolean stbi_is_16_bit_from_callbacks(KStbIoCallbacks clbk, long user) {
        return STBImage.stbi_is_16_bit_from_callbacks(KStbImageLwjgl.wrap(clbk), user);
    }

    @Override
    public void stbi_set_unpremultiply_on_load(boolean flag_true_if_should_unpremultiply) {
        STBImage.stbi_set_unpremultiply_on_load(flag_true_if_should_unpremultiply);
    }

    @Override
    public void stbi_convert_iphone_png_to_rgb(boolean flag_true_if_should_convert) {
        STBImage.stbi_convert_iphone_png_to_rgb(flag_true_if_should_convert);
    }

    @Override
    public void stbi_set_flip_vertically_on_load(boolean flag_true_if_should_flip) {
        STBImage.stbi_set_flip_vertically_on_load(flag_true_if_should_flip);
    }

    @Override
    public void stbi_convert_iphone_png_to_rgb_thread(boolean flag_true_if_should_convert) {
        STBImage.stbi_convert_iphone_png_to_rgb_thread(flag_true_if_should_convert);
    }

    @Override
    public void stbi_set_flip_vertically_on_load_thread(int flag_true_if_should_flip) {
        STBImage.stbi_set_flip_vertically_on_load_thread(flag_true_if_should_flip);
    }

    @Override
    public ByteBuffer stbi_zlib_decode_malloc_guesssize(ByteBuffer buffer, int initial_size) {
        return STBImage.stbi_zlib_decode_malloc_guesssize(buffer, initial_size);
    }

    @Override
    public ByteBuffer stbi_zlib_decode_malloc_guesssize_headerflag(ByteBuffer buffer, int initial_size, boolean parse_header) {
        return STBImage.stbi_zlib_decode_malloc_guesssize_headerflag(buffer, initial_size, parse_header);
    }

    @Override
    public ByteBuffer stbi_zlib_decode_malloc(ByteBuffer buffer) {
        return STBImage.stbi_zlib_decode_malloc(buffer);
    }

    @Override
    public int stbi_zlib_decode_buffer(ByteBuffer obuffer, ByteBuffer ibuffer) {
        return STBImage.stbi_zlib_decode_buffer(obuffer, ibuffer);
    }

    @Override
    public ByteBuffer stbi_zlib_decode_noheader_malloc(ByteBuffer buffer) {
        return STBImage.stbi_zlib_decode_noheader_malloc(buffer);
    }

    @Override
    public int stbi_zlib_decode_noheader_buffer(ByteBuffer obuffer, ByteBuffer ibuffer) {
        return STBImage.stbi_zlib_decode_noheader_buffer(obuffer, ibuffer);
    }

    @Override
    public ByteBuffer stbi_load(ByteBuffer filename, int[] x, int[] y, int[] channels_in_file, int desired_channels) {
        return STBImage.stbi_load(filename, x, y, channels_in_file, desired_channels);
    }

    @Override
    public ByteBuffer stbi_load(CharSequence filename, int[] x, int[] y, int[] channels_in_file, int desired_channels) {
        return STBImage.stbi_load(filename, x, y, channels_in_file, desired_channels);
    }

    @Override
    public ByteBuffer stbi_load_from_memory(ByteBuffer buffer, int[] x, int[] y, int[] channels_in_file, int desired_channels) {
        return STBImage.stbi_load_from_memory(buffer, x, y, channels_in_file, desired_channels);
    }

    @Override
    public ByteBuffer stbi_load_from_callbacks(KStbIoCallbacks clbk, long user, int[] x, int[] y, int[] channels_in_file, int desired_channels) {
        return STBImage.stbi_load_from_callbacks(KStbImageLwjgl.wrap(clbk), user, x, y, channels_in_file, desired_channels);
    }

    @Override
    public ByteBuffer stbi_load_gif_from_memory(ByteBuffer buffer, LongBuffer delays, int[] x, int[] y, int[] z, int[] channels_in_file, int desired_channels) {
        PointerBuffer delaysBuffer = BufferUtils.createPointerBuffer(delays.capacity());
        delaysBuffer.put(delays);
        return STBImage.stbi_load_gif_from_memory(buffer, delaysBuffer, x, y, z, channels_in_file, desired_channels);
    }

    @Override
    public ShortBuffer stbi_load_16(ByteBuffer filename, int[] x, int[] y, int[] channels_in_file, int desired_channels) {
        return STBImage.stbi_load_16(filename, x, y, channels_in_file, desired_channels);
    }

    @Override
    public ShortBuffer stbi_load_16(CharSequence filename, int[] x, int[] y, int[] channels_in_file, int desired_channels) {
        return STBImage.stbi_load_16(filename, x, y, channels_in_file, desired_channels);
    }

    @Override
    public ShortBuffer stbi_load_16_from_memory(ByteBuffer buffer, int[] x, int[] y, int[] channels_in_file, int desired_channels) {
        return STBImage.stbi_load_16_from_memory(buffer, x, y, channels_in_file, desired_channels);
    }

    @Override
    public ShortBuffer stbi_load_16_from_callbacks(KStbIoCallbacks clbk, long user, int[] x, int[] y, int[] channels_in_file, int desired_channels) {
        return STBImage.stbi_load_16_from_callbacks(KStbImageLwjgl.wrap(clbk), user, x, y, channels_in_file, desired_channels);
    }

    @Override
    public FloatBuffer stbi_loadf(ByteBuffer filename, int[] x, int[] y, int[] channels_in_file, int desired_channels ) {
        return STBImage.stbi_loadf(filename, x, y, channels_in_file, desired_channels);
    }

    @Override
    public FloatBuffer stbi_loadf(CharSequence filename, int[] x, int[] y, int[] channels_in_file, int desired_channels) {
        return STBImage.stbi_loadf(filename, x, y, channels_in_file, desired_channels);
    }

    @Override
    public FloatBuffer stbi_loadf_from_memory(ByteBuffer buffer, int[] x, int[] y, int[] channels_in_file, int desired_channels) {
        return STBImage.stbi_loadf_from_memory(buffer, x, y, channels_in_file, desired_channels);
    }

    @Override
    public FloatBuffer stbi_loadf_from_callbacks(KStbIoCallbacks clbk, long user, int[] x, int[] y, int[] channels_in_file, int desired_channels) {
        return STBImage.stbi_loadf_from_callbacks(KStbImageLwjgl.wrap(clbk), user, x, y, channels_in_file, desired_channels);
    }

    @Override
    public boolean stbi_info(ByteBuffer filename, int[] x, int[] y, int[] comp) {
        return STBImage.stbi_info(filename, x, y, comp);
    }

    @Override
    public boolean stbi_info(CharSequence filename, int[] x, int[] y, int[] comp) {
        return STBImage.stbi_info(filename, x, y, comp);
    }

    @Override
    public boolean stbi_info_from_memory(ByteBuffer buffer, int[] x, int[] y, int[] comp) {
        return STBImage.stbi_info_from_memory(buffer, x, y, comp);
    }

    @Override
    public boolean stbi_info_from_callbacks(KStbIoCallbacks clbk, long user, int[] x, int[] y, int[] comp) {
        return STBImage.stbi_info_from_callbacks(KStbImageLwjgl.wrap(clbk), user, x, y, comp);
    }



}
