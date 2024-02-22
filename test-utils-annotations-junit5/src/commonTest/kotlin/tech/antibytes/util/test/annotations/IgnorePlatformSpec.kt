/*
 * Copyright (c) 2024 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.annotations

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

class IgnorePlatformSpec {
    @Test
    @IgnoreAndroid
    @JsName("Given_it_is_IgnoreAndroid_annotated_it_ignores_only_Android")
    fun `Given it is IgnoreAndroid annotated it ignores only Android`() {
        assertEquals(
            actual = PlatformRunner.jsAndJvmAnNative(),
            expected = "test",
        )
    }

    @Test
    @IgnoreJvm
    @JsName("Given_it_is_IgnoreJvm_annotated_it_ignores_only_Jvm")
    fun `Given it is IgnoreJvm annotated it ignores only Jvm`() {
        assertEquals(
            actual = PlatformRunner.androidAndJsAndNative(),
            expected = "test",
        )
    }

    @Test
    @IgnoreJs
    @JsName("Given_it_is_IgnoreJs_annotated_it_ignores_only_Js")
    fun `Given it is IgnoreJs annotated it ignores only Js`() {
        assertEquals(
            actual = PlatformRunner.androidAndJvmAndNative(),
            expected = "test",
        )
    }

    @Test
    @IgnoreNative
    @JsName("Given_it_is_IgnoreNative_annotated_it_ignores_only_Native")
    fun `Given it is IgnoreNative annotated it ignores only Native`() {
        assertEquals(
            actual = PlatformRunner.androidAndJsAndJvm(),
            expected = "test",
        )
    }
}
