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

package io.github.darthakiranihil.konna.core.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Base class for all Konna objects: components, services etc.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public class KObject implements Serializable {

    private static final String DEFAULT_OBJECT_NAME = "object@%d";

    private static long createdObjects = 0;

    /**
     * Unique id of an object.
     * It is assigned when object is created, and it is final, so id cannot be changed
     */
    protected final UUID id;

    /**
     * Name of the object.
     */
    protected final String name;
    /**
     * Object tags.
     */
    protected List<KTag> tags;
    /**
     * Parent object.
     */
    protected KObject parent;

    /**
     * Zero-args KObject constructor.
     * Creates an object with default name, empty tag list and without parent object.
     */
    public KObject() {
        this.id = UUID.randomUUID();
        this.name = String.format(DEFAULT_OBJECT_NAME, KObject.createdObjects);
        this.tags = new ArrayList<>();
        this.parent = null;

        KObject.createdObjects++;
    }

    /**
     * Creates a new object with given name.
     * @param name Name of the object
     */
    public KObject(final String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.tags = new ArrayList<>();
        this.parent = null;

        KObject.createdObjects++;
    }

    /**
     * Constructs a new object with given name and parent object.
     * @param name Name of the object
     * @param parent Parent object
     */
    public KObject(final String name, final KObject parent) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.tags = new ArrayList<>();
        this.parent = parent;

        KObject.createdObjects++;
    }

    /**
     * Constructs a new object with given name with provided tags and parent object.
     * @param name Name of the object
     * @param tags List of object tags
     * @param parent Parent object
     */
    public KObject(final String name, final List<KTag> tags, final KObject parent) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.tags = tags;
        this.parent = parent;

        KObject.createdObjects++;
    }

    /**
     * Constructs a new object with given name with provided tags.
     * @param name Name of the object
     * @param tags List of object tags
     */
    public KObject(final String name, final List<KTag> tags) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.tags = tags;
        this.parent = null;

        KObject.createdObjects++;
    }

    /**
     * Sets a new parent object for current one.
     * @param parent New parent object.
     */
    public void setParent(final KObject parent) {
        this.parent = parent;
    }

    /**
     * Compares a KObject to another object.
     * The comparison does not involve object ids since it is generated randomly
     * for all created objects, so two "most likely to be equal" objects will have
     * different object ids
     * @param o Object to compare
     * @return {@code true} if the objects have the same name, tags and parent object
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KObject kObject = (KObject) o;
        return
                Objects.equals(this.name, kObject.name)
            &&  Objects.equals(this.tags, kObject.tags)
            &&  Objects.equals(this.parent, kObject.parent);
    }

    /**
     * Calculates hash code of the object.
     * @return Hash code of the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.tags, this.parent);
    }

    @Override
    public String toString() {
        return String.format(
            "%s[%s][%s]",
            this.getClass().getSimpleName(),
            this.id == null ? "unknown" : this.id.toString(),
            this.name
        );
    }
}
