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

package io.github.darthakiranihil.konna.level;

import io.github.darthakiranihil.konna.level.property.*;
import org.jspecify.annotations.Nullable;

import java.util.Map;

public class KTileInfo {

    private final int id;
    private final boolean passable;
    private final int transparency;
    private final Map<String, KTileProperty> properties;

    public KTileInfo(
        int id,
        boolean passable,
        int transparency,
        final Map<String, KTileProperty> properties
    ) {
        this.id = id;
        this.passable = passable;
        this.transparency = transparency;
        this.properties = properties;
    }

    public int getId() {
        return this.id;
    }

    public boolean isPassable() {
        return this.passable;
    }

    public int getTransparency() {
        return this.transparency;
    }

    public @Nullable KIntTileProperty getIntProperty(final String name) {
        KTileProperty prop = this.properties.get(name);
        if (prop != null && KIntTileProperty.class.isAssignableFrom(prop.getClass())) {
            return (KIntTileProperty) prop;
        }

        return null;
    }

    public @Nullable KIntArrayTileProperty getIntArrayProperty(final String name) {
        KTileProperty prop = this.properties.get(name);
        if (prop != null && KIntArrayTileProperty.class.isAssignableFrom(prop.getClass())) {
            return (KIntArrayTileProperty) prop;
        }

        return null;
    }

    public @Nullable KFloatTileProperty getFloatProperty(final String name) {
        KTileProperty prop = this.properties.get(name);
        if (prop != null && KFloatTileProperty.class.isAssignableFrom(prop.getClass())) {
            return (KFloatTileProperty) prop;
        }

        return null;
    }

    public @Nullable KFloatArrayTileProperty getFloatArrayProperty(final String name) {
        KTileProperty prop = this.properties.get(name);
        if (prop != null && KFloatArrayTileProperty.class.isAssignableFrom(prop.getClass())) {
            return (KFloatArrayTileProperty) prop;
        }

        return null;
    }

    public @Nullable KBooleanTileProperty getBooleanProperty(final String name) {
        KTileProperty prop = this.properties.get(name);
        if (prop != null && KBooleanTileProperty.class.isAssignableFrom(prop.getClass())) {
            return (KBooleanTileProperty) prop;
        }

        return null;
    }

    public @Nullable KBooleanArrayTileProperty getBooleanArrayProperty(final String name) {
        KTileProperty prop = this.properties.get(name);
        if (prop != null && KBooleanArrayTileProperty.class.isAssignableFrom(prop.getClass())) {
            return (KBooleanArrayTileProperty) prop;
        }

        return null;
    }

    public @Nullable KStringTileProperty getStringProperty(final String name) {
        KTileProperty prop = this.properties.get(name);
        if (prop != null && KStringTileProperty.class.isAssignableFrom(prop.getClass())) {
            return (KStringTileProperty) prop;
        }

        return null;
    }

    public @Nullable KStringArrayTileProperty getStringArrayProperty(final String name) {
        KTileProperty prop = this.properties.get(name);
        if (prop != null && KStringArrayTileProperty.class.isAssignableFrom(prop.getClass())) {
            return (KStringArrayTileProperty) prop;
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public @Nullable <T> KObjectTileProperty<T> getObjectProperty(final String name) {
        KTileProperty prop = this.properties.get(name);
        if (prop != null && KObjectTileProperty.class.isAssignableFrom(prop.getClass())) {
            try {
                return (KObjectTileProperty<T>) prop;
            } catch (ClassCastException e) {
                return null;
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public @Nullable <T> KObjectArrayTileProperty<T> getObjectArrayProperty(final String name) {
        KTileProperty prop = this.properties.get(name);
        if (prop != null && KObjectArrayTileProperty.class.isAssignableFrom(prop.getClass())) {
            try {
                return (KObjectArrayTileProperty<T>) prop;
            } catch (ClassCastException e) {
                return null;
            }
        }

        return null;
    }


}
