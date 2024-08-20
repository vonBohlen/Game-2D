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

}


tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "de.Game2D.engine.Main"
    }
}

