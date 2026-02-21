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

package io.github.darthakiranihil.konna.core.di.std;

import io.github.darthakiranihil.konna.core.di.KContainerModifier;
import io.github.darthakiranihil.konna.core.di.*;
import io.github.darthakiranihil.konna.core.except.KUnknownException;
import io.github.darthakiranihil.konna.core.object.KActivator;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.core.util.*;

import java.util.*;

/**
 * Standard implementation of {@link KContainerAccessor}.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public final class KStandardContainerAccessor extends KObject implements KContainerAccessor {

    private static final int CLASS_BEFORE_ACTIVATOR = 3;

    private final KIndex index;
    private final KContainer rootContainer;
    private final KImmutableContainer lockedRootContainer;

    /**
     * Standard constructor.
     * On initialization, the resolver collects all indexed packages and creates corresponding
     * internal environment records for them. After that it processes packages that define
     * an environment, then it continues with processing empty environments, the resolver
     * define a new environment for each subpackage of the top-level packages. Then it
     * groups all records by environment name and builds container according to group result.
     * @param index Built system index (must contain complete package and class list)
     */
    public KStandardContainerAccessor(final KIndex index) {
        super(
            "container_accessor",
            KStructUtils.setOfTags(
                KTag.DefaultTags.SYSTEM,
                KTag.DefaultTags.STD
            )
        );

        this.index = index;
        this.rootContainer = this.buildContainer();
        this.lockedRootContainer = new KImmutableContainer(this.rootContainer);

    }

    /**
     * Returns the container according to the caller class. Container resolution
     * is connected with the package environment of the package of the caller class.
     * If caller class cannot be got, the root container is returned. If the caller
     * class is an instance of {@link KActivator}, the container will be resolved for
     * the caller class of the activator.
     * If the caller class does not have {@link KContainerModifier},
     * an {@link KImmutableContainer} will be returned instead of regular {@link KContainer}.
     *
     * @return Container the for caller class
     */
    @Override
    public KContainer getContainer() {
        var stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length == 0) {
            return this.lockedRootContainer;
        }

        try {
            Class<?> callerClass = KClassUtils.getForName(stackTrace[2].getClassName());
            if (callerClass == KActivator.class) {
                callerClass = KClassUtils.getForName(
                    stackTrace[KStandardContainerAccessor.CLASS_BEFORE_ACTIVATOR].getClassName()
                );
            }

            if (!callerClass.isAnnotationPresent(KContainerModifier.class)) {
                return this.lockedRootContainer;
            }

            return this.rootContainer;
        } catch (Throwable e) {
            throw new KUnknownException(e);
        }
    }

    private KContainer buildContainer() {
        KContainer root = new KContainer("root_container");
        List<String> packages = this.collectIndexedPackages();
        KClassUtils
            .getRealClassesInPackages(new HashSet<>(packages))
            .forEach(root::add);
        return root;
    }

    private List<String> collectIndexedPackages() {
        return this
            .index
            .getPackageIndex()
            .stream()
            .map(Package::getName)
            .toList();
    }
}
