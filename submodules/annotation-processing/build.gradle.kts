plugins {
    `java-library`
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}
repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":annotation"))
    implementation("com.squareup:javapoet:1.13.0")
}

