repositories {
    mavenCentral()
}

plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

gradlePlugin {
    plugins.register("dvp.module.deps") {
        id = "dvp.module.deps"
        implementationClass = "ClassLoaderPlugin"
    }
}