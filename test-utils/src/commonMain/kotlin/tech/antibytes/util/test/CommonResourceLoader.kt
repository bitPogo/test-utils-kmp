/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test

import tech.antibytes.util.test.error.FileNotFoundError

internal typealias Path = String
internal typealias AbsolutePath = String

expect class CommonResourceLoader(
    projectDir: AbsolutePath
) {
    fun exists(path: Path, root: Path? = null): Boolean

    @Throws(FileNotFoundError::class)
    fun load(path: Path, root: Path? = null): String

    @Throws(FileNotFoundError::class)
    fun loadBytes(path: Path, root: Path? = null): ByteArray
}

internal object CommonPathResolver {
    private const val commonRoot: Path = "src/commonTest/resources"
    const val UTF_8 = "utf-8"

    fun resolvePath(
        projectDir: AbsolutePath,
        moduleDir: Path? = null,
        target: Path
    ): AbsolutePath {
        return if (moduleDir is Path) {
            "${projectDir.trimEnd('/')}/${moduleDir.trimEnd('/')}/${target.trimStart('/')}"
        } else {
            "${projectDir.trimEnd('/')}/${commonRoot.trim('/')}/${target.trimStart('/')}"
        }
    }
}
