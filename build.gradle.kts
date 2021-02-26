
plugins {
    kotlin("jvm") version Versions.kotlin
    id(Plugins.ktlint) version Versions.ktlintPlugin
}

repositories {
    jcenter()
}

group = "hoolah.yolkin.challenge"

// create a single fat Jar with all dependencies
// according to https://docs.gradle.org/5.1.1/userguide/working_with_files.html#sec:creating_uber_jar_example
// This is one of most simple way in creating fatJar with all dependencies
// Instead of it shadowJar / springBoot can be used in reality
tasks.register<Jar>("fatJar") {
    manifest {
        attributes["Main-Class"] = "hoolah.yolkin.challenge.TransactionAnalyzerKt"
    }

    archiveAppendix.set("fat")

    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}

dependencies {
    implementation(Libs.opencsv)
    implementation(Libs.logger)
    implementation(Libs.logbackClassic)

    testImplementation(Libs.junit)
    testImplementation(Libs.junitEngine)
    testImplementation(Libs.junitParams)
}

tasks {

    // Run tests with jUnit
    test {
        useJUnitPlatform()
    }

    // set compilation targets
    compileKotlin {
        kotlinOptions.jvmTarget = Versions.kotlinJvmTarget
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = Versions.kotlinJvmTarget
    }
}
