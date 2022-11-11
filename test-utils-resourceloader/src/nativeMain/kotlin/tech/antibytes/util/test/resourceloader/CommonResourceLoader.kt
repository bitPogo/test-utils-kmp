/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.resourceloader

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toKString
import platform.posix.F_OK
import platform.posix.access
import platform.posix.fclose
import platform.posix.fgets
import platform.posix.fopen
import tech.antibytes.util.test.resourceloader.error.FileNotFoundError

actual class CommonResourceLoader actual constructor(projectDir: AbsolutePath) {
    private val projectPath = projectDir

    actual fun exists(path: Path, root: Path?): Boolean {
        return access(
            CommonPathResolver.resolvePath(
                projectPath,
                root,
                path,
            ),
            F_OK,
        ) == 0
    }

    @Throws(FileNotFoundError::class)
    private fun read(fileName: Path): String {
        val returnBuffer = StringBuilder()
        val file = fopen(fileName, "r") ?: throw FileNotFoundError()

        try {
            memScoped {
                val readBufferLength = 64 * 1024
                val buffer = allocArray<ByteVar>(readBufferLength)
                var line = fgets(buffer, readBufferLength, file)?.toKString()
                while (line != null) {
                    returnBuffer.append(line)
                    line = fgets(buffer, readBufferLength, file)?.toKString()
                }
            }
        } finally {
            fclose(file)
        }

        return returnBuffer.toString()
    }

    @Throws(FileNotFoundError::class)
    private fun readFile(path: Path, root: Path?): String {
        return if (!exists(path, root)) {
            throw FileNotFoundError()
        } else {
            val resource = CommonPathResolver.resolvePath(
                projectPath,
                root,
                path,
            )

            read(resource)
        }
    }

    @Throws(FileNotFoundError::class)
    actual fun load(
        path: Path,
        root: Path?,
    ): String = readFile(path, root)

    @Throws(FileNotFoundError::class)
    actual fun loadBytes(
        path: Path,
        root: Path?,
    ): ByteArray = readFile(path, root).encodeToByteArray()
}
