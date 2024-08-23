plugins {

    id("java")
    id("java-library")
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version("7.1.2")

}

group = "org.Game2D"
version = "1.0.0"

java {

    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17

}

repositories {
    mavenCentral()
}

dependencies {

    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")

    implementation("org.jetbrains:annotations:24.1.0")
    implementation("org.reflections:reflections:0.10.2")

    implementation("org.lwjgl:lwjgl:3.3.1")
    implementation("org.lwjgl:lwjgl-glfw:3.3.1")
    implementation("org.lwjgl:lwjgl-opengl:3.3.1")
    //implementation("org.lwjgl:lwjgl-stb:3.3.1")

    //runtimeOnly("org.lwjgl:lwjgl:3.3.1:natives-macos")
    //runtimeOnly("org.lwjgl:lwjgl:3.3.1:natives-windows")
    runtimeOnly("org.lwjgl:lwjgl:3.3.1:natives-linux")

}


tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "org.Game2D.rendertest.Test"
    }
}

