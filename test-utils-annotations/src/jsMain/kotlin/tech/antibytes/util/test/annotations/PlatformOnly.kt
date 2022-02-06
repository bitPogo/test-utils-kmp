/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.annotations

import kotlin.test.Ignore

actual typealias AndroidOnly = Ignore
actual typealias JvmOnly = Ignore
actual annotation class JsOnly
