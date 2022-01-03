/*
 * Copyright (c) 2021 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by LGPL v2.1
 */

package tech.antibytes.util.test.annotations

import kotlin.reflect.KClass

expect abstract class AbstractRunner
expect abstract class ParentRunner<T> : AbstractRunner
expect class FrameworkMethod
expect open class BlockClassRunner : ParentRunner<FrameworkMethod>
expect open class SandboxTestRunner : BlockClassRunner
expect class RobolectricTestRunner : SandboxTestRunner
expect annotation class RunWithRobolectricTestRunner(val value: KClass<out AbstractRunner>)

expect open class Application
expect annotation class RobolectricConfig(
    val sdk: IntArray,
    val minSdk: Int,
    val maxSdk: Int,
    val manifest: String,
    val application: KClass<out Application>,
    val packageName: String,
    val qualifiers: String,
    val resourceDir: String,
    val assetDir: String,
    val shadows: Array<KClass<*>>,
    val instrumentedPackages: Array<String>,
    val libraries: Array<String>,
)
