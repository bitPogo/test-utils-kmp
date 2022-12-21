/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.annotations

import org.junit.jupiter.api.Disabled

actual annotation class AndroidOnly
actual typealias JvmOnly = Disabled
actual typealias JsOnly = Disabled
actual typealias NativeOnly = Disabled
