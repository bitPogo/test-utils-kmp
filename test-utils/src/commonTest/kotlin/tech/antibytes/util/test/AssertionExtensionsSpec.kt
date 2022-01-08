/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test

import tech.antibytes.util.test.fixture.fixture
import tech.antibytes.util.test.fixture.kotlinFixture
import kotlin.test.Test
import kotlin.test.assertFailsWith

class AssertionExtensionsSpec {
    private val fixture = kotlinFixture()

    @Test
    fun `Given fulfils is called it fails if the lefthand is not an instance of the righthand`() {
        val string: String = fixture.fixture()

        assertFailsWith<AssertionError> {
            string fulfils Int::class
        }
    }

    @Test
    fun `Given fulfils is called it just runs if lefthand is a instance of the righthand`() {
        val string: String = fixture.fixture()

        string fulfils String::class
    }

    @Test
    fun `Given mustBe is called it fails if the lefthand equals not righthand`() {
        val string: String = fixture.fixture()

        assertFailsWith<AssertionError> {
            string mustBe fixture.fixture()
        }
    }

    @Test
    fun `Given mustBe is called it runs if the lefthand equals righthand`() {
        val string: String = fixture.fixture()

        string mustBe string
    }

    @Test
    fun `Given mustBe is called it repsects nullability`() {
        val string: String = fixture.fixture()

        assertFailsWith<AssertionError> {
            string mustBe null
        }
    }

    @Test
    fun `Given sameAs is called it fails if the lefthand is not the very same as righthand`() {
        val string: String = fixture.fixture()

        assertFailsWith<AssertionError> {
            string sameAs fixture.fixture()
        }
    }

    @Test
    fun `Given sameAs is called it runs if the lefthand is the very same as righthand`() {
        val string: String = fixture.fixture()

        string sameAs string
    }

    @Test
    fun `Given sameAs is called it runs it repsects nullability`() {
        val string: String = fixture.fixture()

        assertFailsWith<AssertionError> {
            string sameAs null
        }
    }

    @Test
    fun `Given isNot is called it fails if the lefthand is equal to righthand`() {
        val string: String = fixture.fixture()

        assertFailsWith<AssertionError> {
            string isNot string
        }
    }

    @Test
    fun `Given isNot is called it runs if the lefthand is not equal righthand`() {
        val string: String = fixture.fixture()

        string isNot fixture.fixture()
    }

    @Test
    fun `Given isNot is called it repsects nullability`() {
        val string: String = fixture.fixture()

        string isNot null
    }
}
