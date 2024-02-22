/*
 * Copyright (c) 2024 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.resourceloader

import java.io.File
import java.nio.charset.Charset
import tech.antibytes.util.test.resourceloader.error.FileNotFoundError

actual class CommonResourceLoader actual constructor(projectDir: AbsolutePath) {
    private val projectPath = projectDir

    actual fun exists(path: Path, root: Path?): Boolean {
        val resource = File(
            CommonPathResolver.resolvePath(
                projectPath,
                root,
                path,
            ),
        )

        return resource.exists()
    }

    @Throws(FileNotFoundError::class)
    private fun resolveFile(path: Path, root: Path?): File {
        val resource = File(
            CommonPathResolver.resolvePath(
                projectPath,
                root,
                path,
            ),
        )

        return if (!resource.exists()) {
            throw FileNotFoundError()
        } else {
            resource
        }
    }

    @Throws(FileNotFoundError::class)
    actual fun load(
        path: Path,
        root: Path?,
    ): String = resolveFile(path, root).readText(Charset.forName(CommonPathResolver.UTF_8))

    @Throws(FileNotFoundError::class)
    actual fun loadBytes(
        path: Path,
        root: Path?,
    ): ByteArray = resolveFile(path, root).readBytes()
}
