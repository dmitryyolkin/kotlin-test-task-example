
plugins {
    kotlin("jvm") version Versions.kotlin
    id(Plugins.ktlint) version Versions.ktlintPlugin
}

repositories {
    jcenter()
}

group = "hoolah.yolkin.challenge"

dependencies {
    implementation(Libs.logger)
    implementation(Libs.jacksonKotlin)

    testImplementation(Libs.junit)
    testImplementation(Libs.junitEngine)
    testImplementation(Libs.junitParams)
    testImplementation(Libs.mockitoKotlin)
}

tasks.test {
    useJUnitPlatform()
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = Versions.kotlinJvmTarget
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = Versions.kotlinJvmTarget
    }
}
