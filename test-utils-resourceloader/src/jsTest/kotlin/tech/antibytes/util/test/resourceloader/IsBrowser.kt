/*
 * Copyright (c) 2024 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.resourceloader

import kotlinx.browser.window

actual val isBrowser: Boolean = jsTypeOf(window) != "undefined"
