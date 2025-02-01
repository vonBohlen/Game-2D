[release]: https://img.shields.io/badge/Releases-JitPack-5a7b9c
[jitPack]: https://img.shields.io/badge/Snapshots-JitPack-5a7b9c
[license]: https://github.com/vonBohlen/Game-2D/blob/main/LICENSE
[license-shield]: https://img.shields.io/github/license/vonBohlen/Game-2D?color=%239164c4

[ ![release][] ](#download)
[ ![JitPack][] ](#download)
[ ![license-shield][] ][license]

# Game2D
**DISCLAIMER:** NO RELEASES ARE PUBLISHED YET. 
<p>IF YOU WANT TO USE GAME2D ANYWAY, USE THE LATEST SNAPSHOT VERSION.</p>
THIS DOCUMENT WILL BE UPDATED IF THEY ARE ANY REALISES AVAILABLE.

## Summary
* [Roadmap](#roadmap)
* [Download](#download)

## Roadmap
* **Vulcan** based graphics engine _(in progress)_
* **Chunk** based processing _(in progress)_ 
* **AABB** collision _(planed)_

## Download
[ ![release][] ](https://jitpack.io/#vonBohlen/Game-2D)
[ ![JitPack][] ](https://jitpack.io/#vonBohlen/Game-2D)

[Latest GitHub release](https://github.com/vonBohlen/Game-2D/releases/latest)

Replace the **VERSION** placeholder with the release or snapshot you want to use.

**Maven**
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
```xml
<dependency>
    <groupId>com.github.vonBohlen</groupId>
    <artifactId>Game-2D</artifactId>
    <version>VERSION</version>
</dependency>
```

**Gradle**
```groovy
dependencyResolutionManagement { 
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
	repositories {
        mavenCentral()
		maven { url 'https://jitpack.io' }
    }
}
```
```groovy
dependencies {
    implementation 'com.github.vonBohlen:Game-2D:VERSION'
}
```
