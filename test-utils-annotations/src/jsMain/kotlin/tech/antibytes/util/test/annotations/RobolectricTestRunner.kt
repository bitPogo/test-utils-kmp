/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.annotations

import kotlin.reflect.KClass

abstract class AbstractNoop
abstract class NoopParent<T> : AbstractNoop()
class NoopFramework
open class NoopBlock : NoopParent<NoopFramework>()
open class NoopSandBox : NoopBlock()
class NoopRunner : NoopSandBox()
annotation class NoopRunWith(val value: KClass<out AbstractRunner>)

actual typealias AbstractRunner = AbstractNoop
actual typealias FrameworkMethod = NoopFramework
actual typealias ParentRunner<T> = NoopParent<T>
actual typealias BlockClassRunner = NoopBlock
actual typealias SandboxTestRunner = NoopSandBox
actual typealias RobolectricTestRunner = NoopRunner
actual typealias RunWithRobolectricTestRunner = NoopRunWith

open class NoApplication
annotation class NoopConfig(
    val sdk: IntArray = [],
    val minSdk: Int = -1,
    val maxSdk: Int = -1,
    val manifest: String = "",
    val application: KClass<out Application> = NoApplication::class,
    val packageName: String = "",
    val qualifiers: String = "",
    val resourceDir: String = "",
    val assetDir: String = "",
    val shadows: Array<KClass<*>> = [],
    val instrumentedPackages: Array<String> = [],
    val libraries: Array<String> = []
)

actual typealias Application = NoApplication
actual typealias RobolectricConfig = NoopConfig
