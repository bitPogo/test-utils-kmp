/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.annotations

import org.junit.runners.BlockJUnit4ClassRunner

actual typealias AbstractRunner = org.junit.runner.Runner
actual typealias ParentRunner<T> = org.junit.runners.ParentRunner<T>
actual typealias FrameworkMethod = org.junit.runners.model.FrameworkMethod
actual typealias BlockClassRunner = BlockJUnit4ClassRunner
actual typealias SandboxTestRunner = org.robolectric.internal.SandboxTestRunner
actual typealias RobolectricTestRunner = org.robolectric.RobolectricTestRunner
actual typealias RunWithRobolectricTestRunner = org.junit.runner.RunWith

actual typealias Application = android.app.Application
actual typealias RobolectricConfig = org.robolectric.annotation.Config
