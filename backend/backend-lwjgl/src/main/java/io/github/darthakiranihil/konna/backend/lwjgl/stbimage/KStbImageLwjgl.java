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

package io.github.darthakiranihil.konna.backend.lwjgl.stbimage;

import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.test.KExcludeFromGeneratedCoverageReport;
import io.github.darthakiranihil.konna.libfrontend.stb.KStbImage;
import io.github.darthakiranihil.konna.libfrontend.stb.KStbIoCallbacks;
import org.jspecify.annotations.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.stb.STBIIOCallbacks;
import org.lwjgl.stb.STBImage;

import java.nio.*;

/**
 * STBImage library frontend implementation,
 * using corresponding bindings from {@link STBImage}.
 *
 * @author Darth Akira Nihil
 * @version 0.3.0
 */
@KSingleton
@KExcludeFromGeneratedCoverageReport
public final class KStbImageLwjgl extends KObject implements KStbImage {

    private static STBIIOCallbacks wrap(final KStbIoCallbacks callbacks) {
        STBIIOCallbacks internal = STBIIOCallbacks.create();
        internal.set(
            (user, data, size) -> callbacks.read().invoke(user, data, size),
            (user, n) -> callbacks.skip().invoke(user, n),
            (user) -> callbacks.eof().invoke(user)
        );
        return internal;
    }

    @Override
    public @Nullable ByteBuffer stbi_load(
        final ByteBuffer filename,
        final IntBuffer x,
        final IntBuffer y,
        final IntBuffer channels_in_file,
        int desired_channels
    ) {
        return STBImage.stbi_load(
            filename,
            x,
            y,
            channels_in_file,
            desired_channels
        );
    }

    @Override
    public @Nullable ByteBuffer stbi_load(
        final CharSequence filename,
        final IntBuffer x,
        final IntBuffer y,
        final IntBuffer channels_in_file,
        int desired_channels
    ) {
        return STBImage.stbi_load(filename, x, y, channels_in_file, desired_channels);
    }

    @Override
    public @Nullable ByteBuffer stbi_load_from_memory(
        final ByteBuffer buffer,
        final IntBuffer x,
        final IntBuffer y,
        final IntBuffer channels_in_file,
        int desired_channels
    ) {
        return STBImage.stbi_load_from_memory(buffer, x, y, channels_in_file, desired_channels);
    }

    @Override
    public @Nullable ByteBuffer stbi_load_from_callbacks(
        final KStbIoCallbacks clbk,
        long user,
        final IntBuffer x,
        final IntBuffer y,
        final IntBuffer channels_in_file,
        int desired_channels
    ) {
        return STBImage.stbi_load_from_callbacks(
            KStbImageLwjgl.wrap(clbk),
            user,
            x,
            y,
            channels_in_file,
            desired_channels
        );
    }

    @Override
    public @Nullable ByteBuffer stbi_load_gif_from_memory(
        final ByteBuffer buffer,
        final LongBuffer delays,
        final IntBuffer x,
        final IntBuffer y,
        final IntBuffer z,
        final IntBuffer channels_in_file,
        int desired_channels
    ) {
        PointerBuffer delaysBuffer = BufferUtils.createPointerBuffer(delays.capacity());
        delaysBuffer.put(delays);
        ByteBuffer result = STBImage.stbi_load_gif_from_memory(
            buffer,
            delaysBuffer,
            x,
            y,
            z,
            channels_in_file,
            desired_channels
        );
        delaysBuffer.free();
        return result;
    }

    @Override
    public @Nullable ShortBuffer stbi_load_16(
        final ByteBuffer filename,
        final IntBuffer x,
        final IntBuffer y,
        final IntBuffer channels_in_file,
        int desired_channels
    ) {
        return STBImage.stbi_load_16(filename, x, y, channels_in_file, desired_channels);
    }

    @Override
    public @Nullable ShortBuffer stbi_load_16(
        final CharSequence filename,
        final IntBuffer x,
        final IntBuffer y,
        final IntBuffer channels_in_file,
        int desired_channels
    ) {
        return STBImage.stbi_load_16(filename, x, y, channels_in_file, desired_channels);
    }

    @Override
    public @Nullable ShortBuffer stbi_load_16_from_memory(
        final ByteBuffer buffer,
        final IntBuffer x,
        final IntBuffer y,
        final IntBuffer channels_in_file,
        int desired_channels
    ) {
        return STBImage.stbi_load_16_from_memory(buffer, x, y, channels_in_file, desired_channels);
    }

    @Override
    public @Nullable ShortBuffer stbi_load_16_from_callbacks(
        final KStbIoCallbacks clbk,
        long user,
        final IntBuffer x,
        final IntBuffer y,
        final IntBuffer channels_in_file,
        int desired_channels
    ) {
        return STBImage.stbi_load_16_from_callbacks(
            KStbImageLwjgl.wrap(clbk),
            user,
            x,
            y,
            channels_in_file,
            desired_channels
        );
    }

    @Override
    public @Nullable FloatBuffer stbi_loadf(
        final ByteBuffer filename,
        final IntBuffer x,
        final IntBuffer y,
        final IntBuffer channels_in_file,
        int desired_channels
    ) {
        return STBImage.stbi_loadf(filename, x, y, channels_in_file, desired_channels);
    }

    @Override
    public @Nullable FloatBuffer stbi_loadf(
        final CharSequence filename,
        final IntBuffer x,
        final IntBuffer y,
        final IntBuffer channels_in_file,
        int desired_channels
    ) {
        return STBImage.stbi_loadf(filename, x, y, channels_in_file, desired_channels);
    }

    @Override
    public @Nullable FloatBuffer stbi_loadf_from_memory(
        final ByteBuffer buffer,
        final IntBuffer x,
        final IntBuffer y,
        final IntBuffer channels_in_file,
        int desired_channels
    ) {
        return STBImage.stbi_loadf_from_memory(buffer, x, y, channels_in_file, desired_channels);
    }

    @Override
    public @Nullable FloatBuffer stbi_loadf_from_callbacks(
        final KStbIoCallbacks clbk,
        long user,
        final IntBuffer x,
        final IntBuffer y,
        final IntBuffer channels_in_file,
        int desired_channels
    ) {
        return STBImage.stbi_loadf_from_callbacks(
            KStbImageLwjgl.wrap(clbk),
            user,
            x,
            y,
            channels_in_file,
            desired_channels
        );
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
    public boolean stbi_is_hdr(final ByteBuffer filename) {
        return STBImage.stbi_is_hdr(filename);
    }

    @Override
    public boolean stbi_is_hdr(final CharSequence filename) {
        return STBImage.stbi_is_hdr(filename);
    }

    @Override
    public boolean stbi_is_hdr_from_memory(final ByteBuffer buffer) {
        return STBImage.stbi_is_hdr_from_memory(buffer);
    }

    @Override
    public boolean stbi_is_hdr_from_callbacks(final KStbIoCallbacks clbk, long user) {
        return STBImage.stbi_is_hdr_from_callbacks(KStbImageLwjgl.wrap(clbk), user);
    }

    @Override
    public @Nullable String stbi_failure_reason() {
        return STBImage.stbi_failure_reason();
    }

    @Override
    public void stbi_image_free(final ShortBuffer retval_from_stbi_load) {
        STBImage.stbi_image_free(retval_from_stbi_load);
    }

    @Override
    public void stbi_image_free(final FloatBuffer retval_from_stbi_load) {
        STBImage.stbi_image_free(retval_from_stbi_load);
    }

    @Override
    public boolean stbi_info(
        final ByteBuffer filename,
        final IntBuffer x,
        final IntBuffer y,
        final IntBuffer comp
    ) {
        return STBImage.stbi_info(filename, x, y, comp);
    }

    @Override
    public boolean stbi_info(
        final CharSequence filename,
        final IntBuffer x,
        final IntBuffer y,
        final IntBuffer comp
    ) {
        return STBImage.stbi_info(filename, x, y, comp);
    }

    @Override
    public boolean stbi_info_from_memory(
        final ByteBuffer buffer,
        final IntBuffer x,
        final IntBuffer y,
        final IntBuffer comp
    ) {
        return STBImage.stbi_info_from_memory(buffer, x, y, comp);
    }

    @Override
    public boolean stbi_info_from_callbacks(
        final KStbIoCallbacks clbk,
        long user,
        final IntBuffer x,
        final IntBuffer y,
        final IntBuffer comp
    ) {
        return STBImage.stbi_info_from_callbacks(KStbImageLwjgl.wrap(clbk), user, x, y, comp);
    }

    @Override
    public boolean stbi_is_16_bit(final ByteBuffer filename) {
        return STBImage.stbi_is_16_bit(filename);
    }

    @Override
    public boolean stbi_is_16_bit(final CharSequence filename) {
        return STBImage.stbi_is_16_bit(filename);
    }

    @Override
    public boolean stbi_is_16_bit_from_memory(final ByteBuffer buffer) {
        return STBImage.stbi_is_16_bit_from_memory(buffer);
    }

    @Override
    public boolean stbi_is_16_bit_from_callbacks(final KStbIoCallbacks clbk, long user) {
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
    public @Nullable ByteBuffer stbi_zlib_decode_malloc_guesssize(
        final ByteBuffer buffer,
        int initial_size
    ) {
        return STBImage.stbi_zlib_decode_malloc_guesssize(buffer, initial_size);
    }

    @Override
    public @Nullable ByteBuffer stbi_zlib_decode_malloc_guesssize_headerflag(
        final ByteBuffer buffer,
        int initial_size,
        boolean parse_header
    ) {
        return STBImage.stbi_zlib_decode_malloc_guesssize_headerflag(
            buffer,
            initial_size,
            parse_header
        );
    }

    @Override
    public @Nullable ByteBuffer stbi_zlib_decode_malloc(final ByteBuffer buffer) {
        return STBImage.stbi_zlib_decode_malloc(buffer);
    }

    @Override
    public int stbi_zlib_decode_buffer(final ByteBuffer obuffer, final ByteBuffer ibuffer) {
        return STBImage.stbi_zlib_decode_buffer(obuffer, ibuffer);
    }

    @Override
    public @Nullable ByteBuffer stbi_zlib_decode_noheader_malloc(final ByteBuffer buffer) {
        return STBImage.stbi_zlib_decode_noheader_malloc(buffer);
    }

    @Override
    public int stbi_zlib_decode_noheader_buffer(
        final ByteBuffer obuffer,
        final ByteBuffer ibuffer
    ) {
        return STBImage.stbi_zlib_decode_noheader_buffer(obuffer, ibuffer);
    }

    @Override
    public @Nullable ByteBuffer stbi_load(
        final ByteBuffer filename,
        final int[] x,
        final int[] y,
        final int[] channels_in_file,
        int desired_channels
    ) {
        return STBImage.stbi_load(filename, x, y, channels_in_file, desired_channels);
    }

    @Override
    public @Nullable ByteBuffer stbi_load(
        final CharSequence filename,
        final int[] x,
        final int[] y,
        final int[] channels_in_file,
        int desired_channels
    ) {
        return STBImage.stbi_load(filename, x, y, channels_in_file, desired_channels);
    }

    @Override
    public @Nullable ByteBuffer stbi_load_from_memory(
        final ByteBuffer buffer,
        final int[] x,
        final int[] y,
        final int[] channels_in_file,
        int desired_channels
    ) {
        return STBImage.stbi_load_from_memory(buffer, x, y, channels_in_file, desired_channels);
    }

    @Override
    public @Nullable ByteBuffer stbi_load_from_callbacks(
        final KStbIoCallbacks clbk,
        long user,
        final int[] x,
        final int[] y,
        final int[] channels_in_file,
        int desired_channels
    ) {
        return STBImage.stbi_load_from_callbacks(
            KStbImageLwjgl.wrap(clbk),
            user,
            x,
            y,
            channels_in_file,
            desired_channels
        );
    }

    @Override
    public @Nullable ByteBuffer stbi_load_gif_from_memory(
        final ByteBuffer buffer,
        final LongBuffer delays,
        final int[] x,
        final int[] y,
        final int[] z,
        final int[] channels_in_file,
        int desired_channels
    ) {
        PointerBuffer delaysBuffer = BufferUtils.createPointerBuffer(delays.capacity());
        delaysBuffer.put(delays);
        ByteBuffer result = STBImage.stbi_load_gif_from_memory(
            buffer,
            delaysBuffer,
            x,
            y,
            z,
            channels_in_file,
            desired_channels
        );
        delaysBuffer.free();
        return result;
    }

    @Override
    public @Nullable ShortBuffer stbi_load_16(
        final ByteBuffer filename,
        final int[] x,
        final int[] y,
        final int[] channels_in_file,
        int desired_channels
    ) {
        return STBImage.stbi_load_16(filename,
            x,
            y,
            channels_in_file,
            desired_channels
        );
    }

    @Override
    public @Nullable ShortBuffer stbi_load_16(
        final CharSequence filename,
        final int[] x,
        final int[] y,
        final int[] channels_in_file,
        int desired_channels
    ) {
        return STBImage.stbi_load_16(filename, x, y, channels_in_file, desired_channels);
    }

    @Override
    public @Nullable ShortBuffer stbi_load_16_from_memory(
        final ByteBuffer buffer,
        final int[] x,
        final int[] y,
        final int[] channels_in_file,
        int desired_channels
    ) {
        return STBImage.stbi_load_16_from_memory(buffer, x, y, channels_in_file, desired_channels);
    }

    @Override
    public @Nullable ShortBuffer stbi_load_16_from_callbacks(
        final KStbIoCallbacks clbk,
        long user,
        final int[] x,
        final int[] y,
        final int[] channels_in_file,
        int desired_channels
    ) {
        return STBImage.stbi_load_16_from_callbacks(
            KStbImageLwjgl.wrap(clbk),
            user,
            x,
            y,
            channels_in_file,
            desired_channels
        );
    }

    @Override
    public @Nullable FloatBuffer stbi_loadf(
        final ByteBuffer filename,
        final int[] x,
        final int[] y,
        final int[] channels_in_file,
        int desired_channels
    ) {
        return STBImage.stbi_loadf(
            filename,
            x,
            y,
            channels_in_file,
            desired_channels
        );
    }

    @Override
    public @Nullable FloatBuffer stbi_loadf(
        final CharSequence filename,
        final int[] x,
        final int[] y,
        final int[] channels_in_file,
        int desired_channels
    ) {
        return STBImage.stbi_loadf(filename, x, y, channels_in_file, desired_channels);
    }

    @Override
    public @Nullable FloatBuffer stbi_loadf_from_memory(
        final ByteBuffer buffer,
        final int[] x,
        final int[] y,
        final int[] channels_in_file,
        int desired_channels
    ) {
        return STBImage.stbi_loadf_from_memory(buffer, x, y, channels_in_file, desired_channels);
    }

    @Override
    public @Nullable FloatBuffer stbi_loadf_from_callbacks(
        final KStbIoCallbacks clbk,
        long user,
        final int[] x,
        final int[] y,
        final int[] channels_in_file,
        int desired_channels
    ) {
        return STBImage.stbi_loadf_from_callbacks(
            KStbImageLwjgl.wrap(clbk),
            user,
            x,
            y,
            channels_in_file,
            desired_channels
        );
    }

    @Override
    public boolean stbi_info(
        final ByteBuffer filename,
        final int[] x,
        final int[] y,
        final int[] comp
    ) {
        return STBImage.stbi_info(filename, x, y, comp);
    }

    @Override
    public boolean stbi_info(
        final CharSequence filename,
        final int[] x,
        final int[] y,
        final int[] comp
    ) {
        return STBImage.stbi_info(filename, x, y, comp);
    }

    @Override
    public boolean stbi_info_from_memory(
        final ByteBuffer buffer,
        final int[] x,
        final int[] y,
        final int[] comp
    ) {
        return STBImage.stbi_info_from_memory(buffer, x, y, comp);
    }

    @Override
    public boolean stbi_info_from_callbacks(
        final KStbIoCallbacks clbk,
        long user,
        final int[] x,
        final int[] y,
        final int[] comp
    ) {
        return STBImage.stbi_info_from_callbacks(KStbImageLwjgl.wrap(clbk), user, x, y, comp);
    }


}
