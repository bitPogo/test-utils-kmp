/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test

import tech.antibytes.util.test.fixture.fixture
import tech.antibytes.util.test.fixture.kotlinFixture
import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertFailsWith

class AssertionExtensionsSpec {
    private val fixture = kotlinFixture()

    @Test
    @JsName("Given_fulfils_is_called_it_fails_if_the_lefthand_is_not_an_instance_of_the_righthand")
    fun `Given fulfils is called it fails if the lefthand is not an instance of the righthand`() {
        val string: String = fixture.fixture()

        assertFailsWith<AssertionError> {
            string fulfils Int::class
        }
    }

    @Test
    @JsName("Given_fulfils_is_called_it_just_runs_if_lefthand_is_a_instance_of_the_righthand")
    fun `Given fulfils is called it just runs if lefthand is a instance of the righthand`() {
        val string: String = fixture.fixture()

        string fulfils String::class
    }

    @Test
    @JsName("Given_mustBe_is_called_it_fails_if_the_lefthand_equals_not_righthand")
    fun `Given mustBe is called it fails if the lefthand equals not righthand`() {
        val string: String = fixture.fixture()

        assertFailsWith<AssertionError> {
            string mustBe fixture.fixture()
        }
    }

    @Test
    @JsName("Given_mustBe_is_called_it_runs_if_the_lefthand_equals_righthand")
    fun `Given mustBe is called it runs if the lefthand equals righthand`() {
        val string: String = fixture.fixture()

        string mustBe string
    }

    @Test
    @JsName("Given_mustBe_is_called_it_repsects_nullability")
    fun `Given mustBe is called it repsects nullability`() {
        val string: String = fixture.fixture()

        assertFailsWith<AssertionError> {
            string mustBe null
        }
    }

    @Test
    @JsName("Given_sameAs_is_called_it_fails_if_the_lefthand_is_not_the_very_same_as_righthand")
    fun `Given sameAs is called it fails if the lefthand is not the very same as righthand`() {
        val string: String = fixture.fixture()

        assertFailsWith<AssertionError> {
            string sameAs fixture.fixture()
        }
    }

    @Test
    @JsName("Given_sameAs_is_called_it_runs_if_the_lefthand_is_the_very_same_as_righthand")
    fun `Given sameAs is called it runs if the lefthand is the very same as righthand`() {
        val string: String = fixture.fixture()

        string sameAs string
    }

    @Test
    @JsName("Given_sameAs_is_called_it_runs_it_repsects_nullability")
    fun `Given sameAs is called it runs it repsects nullability`() {
        val string: String = fixture.fixture()

        assertFailsWith<AssertionError> {
            string sameAs null
        }
    }

    @Test
    @JsName("Given_isNot_is_called_it_fails_if_the_lefthand_is_equal_to_righthand")
    fun `Given isNot is called it fails if the lefthand is equal to righthand`() {
        val string: String = fixture.fixture()

        assertFailsWith<AssertionError> {
            string isNot string
        }
    }

    @Test
    @JsName("Given_isNot_is_called_it_runs_if_the_lefthand_is_not_equal_righthand")
    fun `Given isNot is called it runs if the lefthand is not equal righthand`() {
        val string: String = fixture.fixture()

        string isNot fixture.fixture()
    }

    @Test
    @JsName("Given_isNot_is_called_it_repsects_nullability")
    fun `Given isNot is called it repsects nullability`() {
        val string: String = fixture.fixture()

        string isNot null
    }
}
