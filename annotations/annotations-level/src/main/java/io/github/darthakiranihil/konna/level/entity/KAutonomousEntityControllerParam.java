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

import java.lang.annotation.*;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.CONSTRUCTOR)
@Repeatable(KAutonomousEntityControllerParams.class)
public @interface KAutonomousEntityControllerParam {

    enum Metatype {

        INT,
        FLOAT,
        STRING,
        BOOLEAN,
        CLASS,
        CLASS_THAT_EXTENDS,
        ENUM,
        SUBDEF,

        INT_ARRAY,
        FLOAT_ARRAY,
        STRING_ARRAY,
        BOOLEAN_ARRAY,
        CLASS_ARRAY,
        CLASS_THAT_EXTENDS_ARRAY,
        SUBDEF_ARRAY,

    }

    String name();
    Metatype type();
    String qualifier() default "";

}
