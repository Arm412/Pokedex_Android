plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("org.jetbrains.kotlin.jvm") version "1.9.0"
    alias(libs.plugins.android.ksp) apply false
    alias(libs.plugins.serialization) apply false
}

buildscript {
    dependencies {
        classpath(libs.hilt.android.gradle.plugin)
    }
}
