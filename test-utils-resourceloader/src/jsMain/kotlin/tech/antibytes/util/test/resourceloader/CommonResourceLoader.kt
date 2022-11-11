/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.resourceloader

import kotlinx.browser.window
import tech.antibytes.util.test.resourceloader.error.FileNotFoundError

private val isBrowser = jsTypeOf(window) != "undefined"

actual class CommonResourceLoader actual constructor(projectDir: AbsolutePath) {
    private val projectPath = projectDir
    private val fs: dynamic = if (!isBrowser) {
        js("require('fs')")
    } else {
        undefined
    }

    private fun existsNode(fullPath: Path): Boolean {
        return try {
            val stats = fs.statSync(fullPath)
            stats.isFile() as Boolean
        } catch (e: Throwable) {
            false
        }
    }

    private fun existsBrowser(fullPath: Path): Boolean {
        TODO("JSBrowser does not support a meaningful way to load resources")
    }

    actual fun exists(path: Path, root: Path?): Boolean {
        val resource = CommonPathResolver.resolvePath(
            projectPath,
            root,
            path,
        )

        return if (isBrowser) {
            existsBrowser(resource)
        } else {
            existsNode(resource)
        }
    }

    private fun readFileNode(fullPath: Path): String {
        return fs.readFileSync(path = fullPath, options = "utf8") as String
    }

    private fun readFileBrowser(fullPath: Path): String {
        TODO("JSBrowser does not support a meaningful way to load resources")
    }

    private fun loadText(
        path: Path,
        root: Path?,
    ): String {
        return if (!exists(path, root)) {
            throw FileNotFoundError()
        } else {
            val resource = CommonPathResolver.resolvePath(
                projectPath,
                root,
                path,
            )

            if (isBrowser) {
                readFileBrowser(resource)
            } else {
                readFileNode(resource)
            }
        }
    }

    actual fun load(
        path: Path,
        root: Path?,
    ): String {
        return loadText(path, root)
    }

    actual fun loadBytes(
        path: Path,
        root: Path?,
    ): ByteArray = loadText(path, root).encodeToByteArray()
}
