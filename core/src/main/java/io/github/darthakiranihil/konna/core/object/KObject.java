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

import org.jspecify.annotations.Nullable;

import java.io.Serializable;
import java.util.*;

/**
 * Base class for all Konna objects: components, services etc.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public class KObject implements KDeletable, Serializable {

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
    protected final Set<String> tags;
    /**
     * Parent object.
     */
    private @Nullable KObject parent;
    private final Set<KObject> children;

    /**
     * Zero-args KObject constructor.
     * Creates an object with default name, empty tag list and without parent object.
     */
    public KObject() {
        this.id = UUID.randomUUID();
        this.name = String.format(DEFAULT_OBJECT_NAME, KObject.createdObjects);
        this.tags = new HashSet<>();
        this.parent = null;
        this.children = new HashSet<>();

        KObject.createdObjects++;
    }

    /**
     * Creates a new object with given name.
     * @param name Name of the object
     */
    public KObject(final String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.tags = new HashSet<>();
        this.parent = null;
        this.children = new HashSet<>();

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
        this.tags = new HashSet<>();
        this.parent = parent;
        this.children = new HashSet<>();

        KObject.createdObjects++;
    }

    /**
     * Constructs a new object with given name with provided tags and parent object.
     * @param name Name of the object
     * @param tags List of object tags
     * @param parent Parent object
     */
    public KObject(final String name, final Set<String> tags, final KObject parent) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.tags = new HashSet<>(tags);
        this.parent = parent;
        this.children = new HashSet<>();

        KObject.createdObjects++;
    }

    /**
     * Constructs a new object with given name with provided tags.
     * @param name Name of the object
     * @param tags List of object tags
     */
    public KObject(final String name, final Set<String> tags) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.tags = new HashSet<>(tags);
        this.parent = null;
        this.children = new HashSet<>();

        KObject.createdObjects++;
    }

    /**
     * Sets a new parent object for this object.
     * @param parent New parent object.
     */
    public final void setParent(final KObject parent) {
        if (this.parent != null) {
            this.parent.children
                .removeIf(x -> x.id.equals(this.id));
        }

        this.parent = parent;
        parent.children.add(this);
    }

    public final void addChild(final KObject child) {
        child.setParent(this);
    }

    /**
     * @return ID of the object
     */
    public final UUID id() {
        return this.id;
    }

    /**
     * @return Name of the object
     */
    public final String name() {
        return this.name;
    }

    /**
     * @return Set of tags associated with this object
     */
    public final Set<String> tags() {
        return this.tags;
    }

    /**
     * Adds a tag to the object.
     * @param tag Added tag
     */
    public final void addTag(final String tag) {
        this.tags.add(tag);
    }

    /**
     * Adds tags to the object. It is recommended to call addTag if only one tag is
     * being added.
     * @param addedTags List of added tags.
     */
    public final void addTags(final String... addedTags) {
        this.tags.addAll(List.of(addedTags));
    }

    /**
     * Removes a tag from the object.
     * @param tag Tag to remove
     */
    public final void removeTag(final String tag) {
        this.tags.remove(tag);
    }

    @Override
    public final void delete() {
        for (KObject child: children) {
            child.delete();
        }

        this.children.clear();
        this.deleteSelf();
    }

    protected void deleteSelf() {

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
        if (this.getClass() != o.getClass()) {
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
            this.id,
            this.name
        );
    }
}
