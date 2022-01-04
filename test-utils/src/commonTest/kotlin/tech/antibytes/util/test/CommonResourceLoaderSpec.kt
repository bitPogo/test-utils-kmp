/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test

import tech.antibytes.util.test.config.TestConfig
import tech.antibytes.util.test.error.FileNotFoundError
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CommonResourceLoaderSpec {
    @Test
    fun `Given exists is called with a Path to a Fixture, it returns false if the Fixture in Common does not exists`() {
        // Given
        val path = "/somthing"

        // When
        val result = CommonResourceLoader(TestConfig.projectDir).exists(path)

        // Then
        assertFalse(result)
    }

    @Test
    fun `Given exists is called with a Path to a Fixture and a RootPath, it returns false if the Fixture in the given Path does not exists`() {
        // Given
        val path = "/somthingElse"
        val root = "/ROOT/"

        // When
        val result = CommonResourceLoader(TestConfig.projectDir).exists(path, root)

        // Then
        assertFalse(result)
    }

    @Test
    fun `Given exists is called with a Path to a Fixture, returns true if the Fixture in CommonTest exists`() {
        // Given
        val path = "/something.txt"

        // When
        val result = CommonResourceLoader(TestConfig.projectDir).exists(path)

        // Then
        assertTrue(result)
    }

    @Test
    fun `Given exists is called with a Path to a Fixture and a RootPath, returns true if the Fixture in the given Path exists`() {
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
    fun `Given load is called with a Path to a Fixture, it fails if the Fixture in Common does not exists`() {
        // Given
        val path = "/something"

        // Then
        assertFailsWith<FileNotFoundError> {
            // When
            CommonResourceLoader(TestConfig.projectDir).load(path)
        }
    }

    @Test
    fun `Given load is called with a Path to a Fixture, it returns the value of the File as String`() {
        // Given
        val path = "/something.txt"

        // When
        val result = CommonResourceLoader(TestConfig.projectDir).load(path).trim()

        // Then
        assertEquals(
            actual = result,
            expected = "你好"
        )
    }

    @Test
    fun `Given load is called with a Path to a Fixture and a RootPath, it fails if the Fixture in Common does not exists`() {
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
    fun `Given load is called with a Path to a Fixture and a RootPath, it returns the value of the File as String`() {
        // Given
        val root = "./src/jvmTest/resources/"
        val path = "/somethingElse.txt"

        // When
        val result = CommonResourceLoader(TestConfig.projectDir).load(path, root = root).trim()

        // Then
        assertEquals(
            actual = result,
            expected = "Hello!"
        )
    }

    // loadBytes
    @Test
    fun `Given loadBytes is called with a Path to a Fixture, it fails if the Fixture in Common does not exists`() {
        // Given
        val path = "/something"

        // Then
        assertFailsWith<FileNotFoundError> {
            // When
            CommonResourceLoader(TestConfig.projectDir).loadBytes(path)
        }
    }

    @Test
    fun `Given loadBytes is called with a Path to a Fixture, it returns the value of the File as String`() {
        // Given
        val path = "/something.txt"

        // When
        val result = CommonResourceLoader(TestConfig.projectDir).loadBytes(path)

        // Then
        assertTrue(
            result.contentEquals("你好\n".encodeToByteArray())
        )
    }

    @Test
    fun `Given loadBytes is called with a Path to a Fixture and a RootPath, it fails if the Fixture in Common does not exists`() {
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
    fun `Given loadBytes is called with a Path to a Fixture and a RootPath, it returns the value of the File as String`() {
        // Given
        val root = "./src/jvmTest/resources/"
        val path = "/somethingElse.txt"

        // When
        val result = CommonResourceLoader(TestConfig.projectDir).loadBytes(path, root = root)

        // Then
        assertTrue(
            result.contentEquals("Hello!\n".encodeToByteArray())
        )
    }
}
