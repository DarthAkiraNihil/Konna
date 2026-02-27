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

package io.github.darthakiranihil.konna.core.util;

import io.github.darthakiranihil.konna.test.KExcludeFromGeneratedCoverageReport;
import org.jspecify.annotations.NullUnmarked;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * Base class for all annotation processors.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
@NullUnmarked
@KExcludeFromGeneratedCoverageReport
public abstract class KBaseAnnotationProcessor extends AbstractProcessor {

    /**
     * Filer instance of this annotation processor.
     */
    protected Filer filer;
    /**
     * Element utils instance of this annotation processor.
     */
    protected Elements elementUtils;
    /**
     * Messager instance of this annotation processor.
     */
    protected Messager messager;
    /**
     * Type utils instance of this annotation processor.
     */
    protected Types typeUtils;

    @Override
    public synchronized void init(final ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        this.filer = processingEnv.getFiler();
        this.elementUtils = processingEnv.getElementUtils();
        this.typeUtils = processingEnv.getTypeUtils();
        this.messager = processingEnv.getMessager();

    }
}
