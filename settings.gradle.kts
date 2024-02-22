/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */
import tech.antibytes.gradle.dependency.settings.fullCache

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        val antibytesPlugins = "^tech\\.antibytes\\.[\\.a-z\\-]+"
        gradlePluginPortal()
        google()
        maven {
            setUrl("https://raw.github.com/bitPogo/maven-snapshots/main/snapshots")
            content {
                includeGroupByRegex(antibytesPlugins)
            }
        }
        maven {
            setUrl("https://raw.github.com/bitPogo/maven-rolling-releases/main/rolling")
            content {
                includeGroupByRegex(antibytesPlugins)
            }
        }
        mavenCentral()
    }
}

plugins {
    id("tech.antibytes.gradle.dependency.settings") version "7dc619f"
}

dependencyResolutionManagement {
    versionCatalogs {
        getByName("antibytesCatalog") {
            version("kotlinx-coroutines-core", "1.7.1")
            version("kotlinx-coroutines-test", "1.7.1")
        }
    }
}

includeBuild("setup")

include(
    ":test-utils",
    ":test-utils-annotations-junit4",
    ":test-utils-annotations-junit5",
    ":test-utils-coroutine",
    ":test-utils-ktor",
    ":test-utils-resourceloader",
)

buildCache {
    fullCache(rootDir)
}

rootProject.name = "test-utils-kmp"
