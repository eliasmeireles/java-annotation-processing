rootProject.name = "annotation"

buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")
        maven("https://repo.spring.io/milestone")
    }

    dependencies {
        classpath("com.github.softwareplace.springboot:plugins:1.0.14")
    }
}


include(":app")
include(":annotation")
include(":annotation-processing")

project(":annotation").projectDir = file("submodules/annotation")
project(":annotation-processing").projectDir = file("submodules/annotation-processing")



