/*
 * Copyright 2025-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License";
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

import io.github.darthakiranihil.konna.test.KExcludeFromGeneratedCoverageReport;

/**
 * Static class of all Konna default tags.
 *
 * @since 0.6.0
 * @author Darth Akira Nihil
 */
// todo: add tags attaching in KObject registry
public final class KDefaultTags extends KUninstantiable {

    @KExcludeFromGeneratedCoverageReport
    private KDefaultTags() {
        super();
    }

    /**
     * Marks that the object is a standard implementation
     * of an internal abstract class or interface.
     */
    public static final String STD = "std";
    /**
     * Marks that the object is related to engine system.
     */
    public static final String SYSTEM = "system";
    /**
     * Marks that the object is a service of an engine component.
     * @since 0.2.0
     */
    public static final String SERVICE = "service";
    /**
     * Marks that the object is used or created in test context.
     */
    public static final String TEST = "test";
    /**
     * Marks that the object is {@link KObjectPool}.
     */
    public static final String POOL = "pool";
    /**
     * Marks that the object is an event.
     * @see io.github.darthakiranihil.konna.core.message.KEvent
     * @see io.github.darthakiranihil.konna.core.message.KSimpleEvent
     */
    public static final String EVENT = "event";
    /**
     * Marks the object is an asset collection.
     * @see io.github.darthakiranihil.konna.core.io.KAssetCollection
     * @since 0.3.0
     */
    public static final String ASSET_COLLECTION = "asset_collection";
    
}
