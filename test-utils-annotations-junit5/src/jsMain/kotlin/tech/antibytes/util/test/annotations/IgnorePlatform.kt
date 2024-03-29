/*
 * Copyright (c) 2024 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.annotations

import kotlin.test.Ignore

actual annotation class IgnoreAndroid
actual annotation class IgnoreJvm
actual typealias IgnoreJs = Ignore
actual annotation class IgnoreNative
