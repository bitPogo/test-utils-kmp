/*
 * Copyright (c) 2024 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.resourceloader

import kotlinx.browser.window
import org.w3c.xhr.XMLHttpRequest
import tech.antibytes.util.test.resourceloader.error.FileNotFoundError

private val isBrowser = jsTypeOf(window) != "undefined"

actual class CommonResourceLoader actual constructor(
    projectDir: AbsolutePath,
    defaultRoot: Path,
) {
    private val projectPath = projectDir
    private val defaultRoot: Path? = defaultRoot.ifBlank { null }
    private val fs: dynamic = if (!isBrowser) {
        js("require('fs')")
    } else {
        undefined
    }

    private fun request(path: Path, config: (XMLHttpRequest.() -> Unit)? = null) = XMLHttpRequest().apply {
        open("GET", path, false)
        config?.invoke(this)
        send()
    }

    private fun existsNode(path: Path, root: Path?): Boolean {
        val fullPath = CommonPathResolver.resolvePath(
            projectPath,
            root ?: defaultRoot,
            path,
        )

        return try {
            val stats = fs.statSync(fullPath)
            stats.isFile() as Boolean
        } catch (e: Throwable) {
            false
        }
    }

    private fun existsBrowser(path: Path, root: Path?): Boolean {
        val fullPath = CommonPathResolver.resolvePath(
            "",
            root ?: defaultRoot,
            path,
        ).drop(1)

        return request(fullPath).status in 200..299
    }

    actual fun exists(path: Path, root: Path?): Boolean {
        return if (isBrowser) {
            existsBrowser(path, root)
        } else {
            existsNode(path, root)
        }
    }

    private fun readFileNode(path: Path, root: Path?): String {
        val fullPath = CommonPathResolver.resolvePath(
            projectPath,
            root ?: defaultRoot,
            path,
        )

        return fs.readFileSync(path = fullPath, options = "utf8") as String
    }

    private fun readFileBrowser(path: Path, root: Path?): String {
        val fullPath = CommonPathResolver.resolvePath(
            "",
            root ?: defaultRoot,
            path,
        ).drop(1)

        return request(fullPath).responseText
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
                root ?: defaultRoot,
                path,
            )

            if (isBrowser) {
                readFileBrowser(path, root)
            } else {
                readFileNode(path, root)
            }
        }
    }

    actual fun load(
        path: Path,
        root: Path?,
    ): String = loadText(path, root)

    actual fun loadBytes(
        path: Path,
        root: Path?,
    ): ByteArray = loadText(path, root).encodeToByteArray()
}
