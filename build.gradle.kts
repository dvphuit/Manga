// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id ("dvp.module.deps")
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.0")
//        classpath(deps.daggerHilt.gradlePlugin)
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.38.1")
        classpath(kotlin("gradle-plugin", version = "1.5.10"))
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}