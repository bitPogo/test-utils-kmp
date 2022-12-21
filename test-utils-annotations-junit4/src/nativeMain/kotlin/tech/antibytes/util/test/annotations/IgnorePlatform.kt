/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.annotations

import kotlin.test.Ignore

actual annotation class IgnoreAndroid
actual annotation class IgnoreJvm
actual annotation class IgnoreJs
actual typealias IgnoreNative = Ignore
