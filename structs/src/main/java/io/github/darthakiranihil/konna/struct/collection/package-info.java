/**
 * <p>
 *     Provides collections, data containers and other related classes.
 * </p>
 * <p>
 *     While it provides interfaces for data-structure-related abstractions,
 *     it is not possible to make a custom implementation of them as they are sealed.
 *     This design choice is motivated with the maximum control over their implementation
 *     in order to provide as optimized classes as possible.
 * </p>
 * <p>
 *     You cannot directly instantiate a collection class. You <i>must</i> use factory methods
 *     provided by the interfaces. With this you will not have to bother about depending on concrete
 *     collection implementation because you will get the most suitable one eventually.
 * </p>
 * <p>
 *     Also, for increasing system integrity, all collections are split into read-only and mutable
 *     variants so you can specify if you only need to read data from a collection or modify it.
 *     Yes, this has been taken from Kotlin.
 * </p>
 *
 * @since 0.7.0
 * @author Darth Akira Nihil
 */
@NullMarked
package io.github.darthakiranihil.konna.struct.collection;

import org.jspecify.annotations.NullMarked;
