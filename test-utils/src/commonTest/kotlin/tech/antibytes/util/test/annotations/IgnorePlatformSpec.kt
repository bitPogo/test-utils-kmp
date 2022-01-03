/*
 * Copyright (c) 2021 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by LGPL v2.1
 */

package tech.antibytes.util.test.annotations

import kotlin.test.Test
import kotlin.test.assertEquals

class IgnorePlatformSpec {
    @Test
    @IgnoreAndroid
    fun `Given it is IgnoreAndroid annotated it ignores only Android`() {
        assertEquals(
            actual = PlatformRunner.jvmOnly(),
            expected = "test"
        )
    }

    @Test
    @IgnoreJvm
    fun `Given it is IgnoreJvm annotated it ignores only Jvm`() {
        assertEquals(
            actual = PlatformRunner.androidOnly(),
            expected = "test"
        )
    }
}
