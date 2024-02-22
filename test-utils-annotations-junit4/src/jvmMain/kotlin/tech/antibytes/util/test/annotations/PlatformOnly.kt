/*
 * Copyright (c) 2024 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.annotations

import org.junit.Ignore

actual typealias AndroidOnly = Ignore
actual annotation class JvmOnly
actual typealias JsOnly = Ignore
actual typealias NativeOnly = Ignore
