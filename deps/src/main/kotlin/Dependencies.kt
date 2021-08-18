@file:Suppress("unused", "ClassName", "SpellCheckingInspection")

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.kotlin
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

const val ktlintVersion = "0.40.0"
const val kotlinVersion = "1.4.32"

object appConfig {
    const val compileSdkVersion = 30
    const val buildToolsVersion = "30.0.3"

    const val minSdkVersion = 23
    const val targetSdkVersion = 30
    const val versionCode = 1
    const val versionName = "1.0"
}

object deps {
    object kotlin {
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion"
    }

    object androidx {
        const val appCompat = "androidx.appcompat:appcompat:1.3.1"
        const val coreKtx = "androidx.core:core-ktx:1.6.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.1"
        const val material = "com.google.android.material:material:1.3.0-alpha02"

    }

    object data {
        object room {
            private const val version = "2.3.0"
            const val runtime = "androidx.room:room-runtime:$version"
            const val compiler = "androidx.room:room-compiler:$version"
            const val ktx = "androidx.room:room-ktx:$version"
        }

        object dataStore {
            const val preferences = "androidx.datastore:datastore-preferences:1.0.0-alpha01"
        }

        object parser {
            const val gson = "com.google.code.gson:gson:2.8.6"
            const val jsoup = "org.jsoup:jsoup:1.14.1"
        }
    }

    object compose {
        const val version = "1.0.0"

        const val layout = "androidx.compose.foundation:foundation-layout:$version"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout-compose:1.0.0-beta02"
        const val foundation = "androidx.compose.foundation:foundation:$version"
        const val ui = "androidx.compose.ui:ui:$version"
        const val material = "androidx.compose.material:material:$version"
        const val materialIconsExtended =
            "androidx.compose.material:material-icons-extended:$version"
        const val runtime = "androidx.compose.runtime:runtime:$version"
        const val livedata = "androidx.compose.runtime:runtime-livedata:$version"
        const val tooling = "androidx.compose.ui:ui-tooling:$version"
        const val unit = "androidx.compose.ui:ui-unit:$version"
        const val util = "androidx.compose.ui:ui-util:$version"

        const val activityCompose = "androidx.activity:activity-compose:1.3.0-alpha08"

        //        const val navigation = "androidx.navigation:navigation-compose:2.4.0-alpha06"
        const val navigation = "androidx.navigation:navigation-compose:2.4.0-alpha04"
    }

    object coil {
        const val compose = "io.coil-kt:coil-compose:1.3.2"
    }

    object lifecycle {
        private const val version = "2.3.1"

        const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
        const val liveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
        const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha05"
    }

    object squareup {
        const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
        const val converterMoshi = "com.squareup.retrofit2:converter-moshi:2.9.0"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:4.8.1"
        const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:1.10.0"
    }

    object jetbrains {
        private const val version = "1.5.1"

        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
    }

    object daggerHilt {
        private const val version = "2.38.1"
        const val android = "com.google.dagger:hilt-android:$version"
        const val core = "com.google.dagger:hilt-core:$version"
        const val compiler = "com.google.dagger:hilt-compiler:$version"
        const val gradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$version"

        const val navigation = "androidx.hilt:hilt-navigation-compose:1.0.0-alpha02"
    }

    object accompanist {
        private const val version = "0.16.0"

        const val coil = "com.google.accompanist:accompanist-coil:$version"

        const val swiperefresh = "com.google.accompanist:accompanist-swiperefresh:$version"

        const val pager = "com.google.accompanist:accompanist-pager:$version"
        const val pagerIndicator = "com.google.accompanist:accompanist-pager-indicators:$version"

        const val placeholder = "com.google.accompanist:accompanist-placeholder-material:$version"

        const val systemUI = "com.google.accompanist:accompanist-systemuicontroller:$version"

        const val insets = "com.google.accompanist:accompanist-insets:$version"
        const val insetsUI = "com.google.accompanist:accompanist-insets-ui:$version"

        const val drawablepainter = "com.google.accompanist:accompanist-drawablepainter:$version"
        const val flowlayout = "com.google.accompanist:accompanist-flowlayout:$version"


    }

    object test {
        const val junit = "junit:junit:4.13"
        const val androidxJunit = "androidx.test.ext:junit:1.1.2"
        const val androidXSspresso = "androidx.test.espresso:espresso-core:3.3.0"
    }
}

private typealias PDsS = PluginDependenciesSpec
private typealias PDS = PluginDependencySpec

inline val PDsS.androidApplication: PDS get() = id("com.android.application")
inline val PDsS.androidLib: PDS get() = id("com.android.library")
inline val PDsS.kotlinAndroid: PDS get() = kotlin("android")
inline val PDsS.kotlin: PDS get() = kotlin("jvm")
inline val PDsS.kotlinKapt: PDS get() = kotlin("kapt")
inline val PDsS.daggerHiltAndroid: PDS get() = id("dagger.hilt.android.plugin")


fun DependencyHandler.implementCompose() {
    arrayOf(
        deps.compose.activityCompose,
        deps.compose.layout,
        deps.compose.foundation,
        deps.compose.ui,
        deps.compose.material,
        deps.compose.materialIconsExtended,
        deps.compose.runtime,
        deps.compose.navigation,
        deps.compose.livedata,
        deps.compose.tooling,
        deps.lifecycle.runtimeKtx,
        deps.lifecycle.liveDataKtx,
        deps.lifecycle.viewModelCompose,
        deps.daggerHilt.navigation,
        deps.coil.compose,
    ).forEach { add("implementation", it) }

    add("debugImplementation", deps.compose.tooling)
    add("debugImplementation", deps.kotlin.reflect)
}

fun DependencyHandler.implementRoom() {
    add("kapt", deps.data.room.compiler)
    add("implementation", deps.data.room.runtime)
    add("implementation", deps.data.room.ktx)
}

fun DependencyHandler.implementHilt() {
    add("kapt", deps.daggerHilt.compiler)
    add("implementation", deps.daggerHilt.core)
    add("implementation", deps.daggerHilt.android)
}

fun DependencyHandler.implementSquareup() {
    add("implementation", deps.squareup.retrofit)
    add("implementation", deps.squareup.converterMoshi)
    add("implementation", deps.squareup.moshiKotlin)
    add("implementation", deps.squareup.loggingInterceptor)
}