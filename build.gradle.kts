plugins {
    id("java")
    id("java-library")
    id("maven-publish")
}

group = "de.Game2D.engine"
version = "pre-alpha.0.1"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("org.lwjgl:lwjgl:3.2.3")
}


tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "de.Game2D.engine.Main"
    }
}

tasks.test {
    useJUnitPlatform()
}