/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.annotations

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

class PlatformOnlySpec {
    @Test
    @AndroidOnly
    @JsName("Given_it_is_AndroidOnly_annotated_it_runs_only_on_Android")
    fun `Given it is AndroidOnly annotated it runs only on Android`() {
        assertEquals(
            actual = PlatformRunner.androidOnly(),
            expected = "test",
        )
    }

    @Test
    @JvmOnly
    @JsName("Given_it_is_JvmOnly_annotated_it_runs_only_on_Jvm")
    fun `Given it is JvmOnly annotated it runs only on Jvm`() {
        assertEquals(
            actual = PlatformRunner.jvmOnly(),
            expected = "test",
        )
    }

    @Test
    @JsOnly
    @JsName("Given_it_is_JsOnly_annotated_it_runs_only_on_Js")
    fun `Given it is JsOnly annotated it runs only on Js`() {
        assertEquals(
            actual = PlatformRunner.jsOnly(),
            expected = "test",
        )
    }

    @Test
    @NativeOnly
    @JsName("Given_it_is_NativeOnly_annotated_it_runs_only_on_Native")
    fun `Given it is NativeOnly annotated it runs only on Native`() {
        assertEquals(
            actual = PlatformRunner.nativeOnly(),
            expected = "test",
        )
    }
}
