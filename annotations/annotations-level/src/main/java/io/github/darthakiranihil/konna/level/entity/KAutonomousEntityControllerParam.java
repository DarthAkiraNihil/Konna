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

package io.github.darthakiranihil.konna.level.entity;

import io.github.darthakiranihil.konna.test.KExcludeFromGeneratedCoverageReport;

import java.lang.annotation.*;

/**
 * Specifies a parameter for an autonomous entity controller to be in
 * its params.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.CONSTRUCTOR)
@Repeatable(KAutonomousEntityControllerParams.class)
public @interface KAutonomousEntityControllerParam {

    /**
     * Utility enumeration that represents type of asset definition property,
     * which is a param of the controller.
     *
     * @since 0.5.0
     * @author Darth Akira Nihil
     */
    @KExcludeFromGeneratedCoverageReport
    enum Metatype {

        /**
         * Specifies param type as int.
         */
        INT,
        /**
         * Specifies param type as float.
         */
        FLOAT,
        /**
         * Specifies param type as string (nullable).
         */
        STRING,
        /**
         * Specifies param type as boolean.
         */
        BOOLEAN,
        /**
         * Specifies param type as class.
         */
        CLASS,
        /**
         * Specifies param type as class, that is assignable
         * from another class. Requires qualifier to specify class constraint.
         */
        CLASS_THAT_EXTENDS,
        /**
         * Specifies param type as enum. Required qualifier to specify type of enum.
         */
        ENUM,
        /**
         * Specifies param type as subdefinition.
         */
        SUBDEF,

        /**
         * Specifies param type as int array.
         */
        INT_ARRAY,
        /**
         * Specifies param type as float array.
         */
        FLOAT_ARRAY,
        /**
         * Specifies param type as string array.
         */
        STRING_ARRAY,
        /**
         * Specifies param type as boolean array.
         */
        BOOLEAN_ARRAY,
        /**
         * Specifies param type as class array.
         */
        CLASS_ARRAY,
        /**
         * Specifies param type as array of classes that are assignable
         * from another class. Requires qualifier to specify class constraint
         */
        CLASS_THAT_EXTENDS_ARRAY,
        /**
         * Specifies param type as subdefinition array.
         */
        SUBDEF_ARRAY,

    }

    /**
     * @return Name of this parameter
     */
    String name();

    /**
     * @return Metatype of this parameter
     */
    Metatype type();

    /**
     * @return Additional qualifier for this parameter. Required only for
     *         {@link Metatype#CLASS_THAT_EXTENDS}, {@link Metatype#CLASS_THAT_EXTENDS_ARRAY}
     *         and {@link Metatype#ENUM} metatypes.
     */
    String qualifier() default "";

}
