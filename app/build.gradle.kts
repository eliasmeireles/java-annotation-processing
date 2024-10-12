import com.github.softwareplace.springboot.java.javaMapStruct
import com.github.softwareplace.springboot.java.lombok
import com.github.softwareplace.springboot.java.openapi.javaOpenApiSettings
import com.github.softwareplace.springboot.utils.springBootStartWeb
import com.github.softwareplace.springboot.utils.springJettyApi

plugins {
    id("com.github.softwareplace.springboot.java")
}

group = "com.softwareplace.example"

javaOpenApiSettings()

dependencies {
    implementation(project(":annotation"))
    annotationProcessor(project(":annotation-processing"))

    springBootStartWeb()
    springJettyApi()

    javaMapStruct()
    lombok()
}
