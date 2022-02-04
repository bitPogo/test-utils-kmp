/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.annotations

import kotlin.test.Test
import kotlin.test.assertEquals

class PlatformOnlySpec {
    @Test
    @AndroidOnly
    fun `Given it is AndroidOnly annotated it runs only on Android`() {
        assertEquals(
            actual = PlatformRunner.androidOnly(),
            expected = "test"
        )
    }

    @Test
    @JvmOnly
    fun `Given it is JvmOnly annotated it runs only on Jvm`() {
        assertEquals(
            actual = PlatformRunner.jvmOnly(),
            expected = "test"
        )
    }
}
