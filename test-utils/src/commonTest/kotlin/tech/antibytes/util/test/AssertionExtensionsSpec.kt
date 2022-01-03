/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test

import com.appmattus.kotlinfixture.kotlinFixture
import kotlin.test.Test
import kotlin.test.assertFailsWith

class AssertionExtensionsSpec {
    private val fixture = kotlinFixture()

    @Test
    fun `Given fulfils is called it fails if the lefthand is not an instance of the righthand`() {
        val string: String = fixture()

        assertFailsWith<AssertionError> {
            string fulfils Integer::class
        }
    }

    @Test
    fun `Given fulfils is called it just runs if lefthand is a instance of the righthand`() {
        val string: String = fixture()

        string fulfils String::class
    }

    @Test
    fun `Given mustBe is called it fails if the lefthand equals not righthand`() {
        val string: String = fixture()

        assertFailsWith<AssertionError> {
            string mustBe fixture()
        }
    }

    @Test
    fun `Given mustBe is called it runs if the lefthand equals righthand`() {
        val string: String = fixture()

        string mustBe string
    }

    @Test
    fun `Given sameAs is called it fails if the lefthand is not the very same as righthand`() {
        val string: String = fixture()

        assertFailsWith<AssertionError> {
            string sameAs fixture()
        }
    }

    @Test
    fun `Given sameAs is called it runs if the lefthand is the very same as righthand`() {
        val string: String = fixture()

        string sameAs string
    }

    @Test
    fun `Given isNot is called it fails if the lefthand is equal to righthand`() {
        val string: String = fixture()

        assertFailsWith<AssertionError> {
            string isNot string
        }
    }

    @Test
    fun `Given isNot is called it runs if the lefthand is not equal righthand`() {
        val string: String = fixture()

        string isNot fixture()
    }
}
