plugins {

    id("java")
    id("java-library")
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version("7.1.2")

}

group = "org.Game2D"
version = "1.0.0"

val lwjglVersion = "3.4.1"
val lwjglNatives = "natives-linux"

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

    implementation(platform("org.lwjgl:lwjgl-bom:$lwjglVersion"))

    implementation("org.lwjgl", "lwjgl")
    implementation("org.lwjgl", "lwjgl-assimp")
    implementation("org.lwjgl", "lwjgl-glfw")
    implementation("org.lwjgl", "lwjgl-nanovg")
    implementation("org.lwjgl", "lwjgl-nuklear")
    implementation("org.lwjgl", "lwjgl-openal")
    implementation("org.lwjgl", "lwjgl-opengl")
    implementation("org.lwjgl", "lwjgl-par")
    implementation("org.lwjgl", "lwjgl-stb")
    implementation("org.lwjgl", "lwjgl-vulkan")
    implementation ("org.lwjgl", "lwjgl", classifier = lwjglNatives)
    implementation ("org.lwjgl", "lwjgl-assimp", classifier = lwjglNatives)
    implementation ("org.lwjgl", "lwjgl-glfw", classifier = lwjglNatives)
    implementation ("org.lwjgl", "lwjgl-nanovg", classifier = lwjglNatives)
    implementation ("org.lwjgl", "lwjgl-nuklear", classifier = lwjglNatives)
    implementation ("org.lwjgl", "lwjgl-openal", classifier = lwjglNatives)
    implementation ("org.lwjgl", "lwjgl-opengl", classifier = lwjglNatives)
    implementation ("org.lwjgl", "lwjgl-par", classifier = lwjglNatives)
    implementation ("org.lwjgl", "lwjgl-stb", classifier = lwjglNatives)

}
