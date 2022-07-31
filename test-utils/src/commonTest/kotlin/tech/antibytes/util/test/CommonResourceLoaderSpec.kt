/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import tech.antibytes.util.test.config.TestConfig
import tech.antibytes.util.test.error.FileNotFoundError

class CommonResourceLoaderSpec {
    @Test
    @JsName("Given_exists_is_called_with_a_Path_to_a_Fixture_it_returns_false_if_the_Fixture_in_Common_does_not_exists")
    fun `Given exists is called with a Path to a Fixture it returns false if the Fixture in Common does not exists`() {
        if (isBrowser) {
            return
        }

        // Given
        val path = "/somthing"

        // When
        val result = CommonResourceLoader(TestConfig.projectDir).exists(path)

        // Then
        assertFalse(result)
    }

    @Test
    @JsName("Given_exists_is_called_with_a_Path_to_a_Fixture_and_a_RootPath_it_returns_false_if_the_Fixture_in_the_given_Path_does_not_exists")
    fun `Given exists is called with a Path to a Fixture and a RootPath it returns false if the Fixture in the given Path does not exists`() {
        if (isBrowser) {
            return
        }

        // Given
        val path = "/somthingElse"
        val root = "/ROOT/"

        // When
        val result = CommonResourceLoader(TestConfig.projectDir).exists(path, root)

        // Then
        assertFalse(result)
    }

    @Test
    @JsName("Given_exists_is_called_with_a_Path_to_a_Fixture_returns_true_if_the_Fixture_in_CommonTest_exists")
    fun `Given exists is called with a Path to a Fixture returns true if the Fixture in CommonTest exists`() {
        if (isBrowser) {
            return
        }

        // Given
        val path = "/something.txt"

        // When
        val result = CommonResourceLoader(TestConfig.projectDir).exists(path)

        // Then
        assertTrue(result)
    }

    @Test
    @JsName("Given_exists_is_called_with_a_Path_to_a_Fixture_and_a_RootPath_it_returns_true_if_the_Fixture_in_the_given_Path_exists")
    fun `Given exists is called with a Path to a Fixture and a RootPath it returns true if the Fixture in the given Path exists`() {
        if (isBrowser) {
            return
        }

        // Given
        val root = "./src/commonTest/resources/"
        val path = "/something.txt"

        // When
        val result = CommonResourceLoader(TestConfig.projectDir).exists(path, root)

        // Then
        assertTrue(result)
    }

    // load

    @Test
    @JsName("Given_load_is_called_with_a_Path_to_a_Fixture_it_fails_if_the_Fixture_in_Common_does_not_exists")
    fun `Given load is called with a Path to a Fixture it fails if the Fixture in Common does not exists`() {
        if (isBrowser) {
            return
        }

        // Given
        val path = "/something"

        // Then
        assertFailsWith<FileNotFoundError> {
            // When
            CommonResourceLoader(TestConfig.projectDir).load(path)
        }
    }

    @Test
    @JsName("Given_load_is_called_with_a_Path_to_a_Fixture_it_returns_the_value_of_the_File_as_String")
    fun `Given load is called with a Path to a Fixture it returns the value of the File as String`() {
        if (isBrowser) {
            return
        }

        // Given
        val path = "/something.txt"

        // When
        val result = CommonResourceLoader(TestConfig.projectDir).load(path).trim()

        // Then
        assertEquals(
            actual = result,
            expected = "你好",
        )
    }

    @Test
    @JsName("Given_load_is_called_with_a_Path_to_a_Fixture_and_a_RootPath_it_fails_if_the_Fixture_in_Common_does_not_exists")
    fun `Given load is called with a Path to a Fixture and a RootPath it fails if the Fixture in Common does not exists`() {
        if (isBrowser) {
            return
        }

        // Given
        val path = "/something"
        val root = "/ROOT/"

        // Then
        assertFailsWith<FileNotFoundError> {
            // When
            CommonResourceLoader(TestConfig.projectDir).load(path, root = root)
        }
    }

    @Test
    @JsName("Given_load_is_called_with_a_Path_to_a_Fixture_and_a_RootPath_it_returns_the_value_of_the_File_as_String")
    fun `Given load is called with a Path to a Fixture and a RootPath it returns the value of the File as String`() {
        if (isBrowser) {
            return
        }

        // Given
        val root = "./src/jvmTest/resources/"
        val path = "/somethingElse.txt"

        // When
        val result = CommonResourceLoader(TestConfig.projectDir).load(path, root = root).trim()

        // Then
        assertEquals(
            actual = result,
            expected = "Hello!",
        )
    }

    // loadBytes
    @Test
    @JsName("Given_loadBytes_is_called_with_a_Path_to_a_Fixture_it_fails_if_the_Fixture_in_Common_does_not_exists")
    fun `Given loadBytes is called with a Path to a Fixture it fails if the Fixture in Common does not exists`() {
        if (isBrowser) {
            return
        }

        // Given
        val path = "/something"

        // Then
        assertFailsWith<FileNotFoundError> {
            // When
            CommonResourceLoader(TestConfig.projectDir).loadBytes(path)
        }
    }

    @Test
    @JsName("Given_loadBytes_is_called_with_a_Path_to_a_Fixture_it_returns_the_value_of_the_File_as_String")
    fun `Given loadBytes is called with a Path to a Fixture it returns the value of the File as String`() {
        if (isBrowser) {
            return
        }

        // Given
        val path = "/something.txt"

        // When
        val result = CommonResourceLoader(TestConfig.projectDir).loadBytes(path)

        // Then
        assertTrue(
            result.contentEquals("你好\n".encodeToByteArray()),
        )
    }

    @Test
    @JsName("Given_loadBytes_is_called_with_a_Path_to_a_Fixture_and_a_RootPath_it_fails_if_the_Fixture_in_Common_does_not_exists")
    fun `Given loadBytes is called with a Path to a Fixture and a RootPath it fails if the Fixture in Common does not exists`() {
        if (isBrowser) {
            return
        }

        // Given
        val path = "/somethingElse"
        val root = "/ROOT/"

        // Then
        assertFailsWith<FileNotFoundError> {
            // When
            CommonResourceLoader(TestConfig.projectDir).load(path, root = root)
        }
    }

    @Test
    @JsName("Given_loadBytes_is_called_with_a_Path_to_a_Fixture_and_a_RootPath_it_returns_the_value_of_the_File_as_String")
    fun `Given loadBytes is called with a Path to a Fixture and a RootPath it returns the value of the File as String`() {
        if (isBrowser) {
            return
        }

        // Given
        val root = "./src/jvmTest/resources/"
        val path = "/somethingElse.txt"

        // When
        val result = CommonResourceLoader(TestConfig.projectDir).loadBytes(path, root = root)

        // Then
        assertTrue(
            result.contentEquals("Hello!\n".encodeToByteArray()),
        )
    }
}
