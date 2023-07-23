package ch.com.findrealestate

import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.project

fun DependencyHandlerScope.unitTestDependencies(project: Project) {
    val libs = project.versionCatalog()

    testImplementation(project(":testutils"))
    testImplementation(libs.findBundle("unitTestLibs").get())
    kaptTest(libs.findLibrary("hilt-android-compiler").get()) // this is need for robolectric tests
}

fun DependencyHandlerScope.androidTestDependencies(project: Project) {
    val libs = project.versionCatalog()

    androidTestImplementation(project(":testutils"))
    androidTestImplementation(libs.findBundle("androidTestLibs").get())
    kaptAndroidTest(libs.findLibrary("hilt-android-compiler").get()) // this is need for robolectric tests
}


fun DependencyHandler.testImplementation(dependencyNotation: Any): Dependency? =
    add("testImplementation", dependencyNotation)

fun DependencyHandler.kaptTest(dependencyNotation: Any): Dependency? =
    add("kaptTest", dependencyNotation)

fun DependencyHandler.androidTestImplementation(dependencyNotation: Any): Dependency? =
    add("androidTestImplementation", dependencyNotation)


fun DependencyHandler.kaptAndroidTest(dependencyNotation: Any): Dependency? =
    add("kaptAndroidTest", dependencyNotation)

internal fun Project.versionCatalog(): VersionCatalog  =
    this.extensions.getByType<VersionCatalogsExtension>().named("libs")
