object Versions {
    const val kotlin = "1.4.10"
    const val kotlinJvmTarget = "11"

    //Plugins
    const val ktlintPlugin = "9.4.1"

    //Libs
    const val jacksonKotlin = "2.11.1"
    const val loggerVersion = "2.0.3"

    // Libs for testing
    const val junit = "5.7.0"
    const val mockitoKotlin = "1.6.0"
}

object Plugins {
    const val ktlint = "org.jlleitschuh.gradle.ktlint"
}

object Libs {
    const val jacksonKotlin = "com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.jacksonKotlin}"
    const val logger = "io.github.microutils:kotlin-logging:${Versions.loggerVersion}"

    // Test libraries
    const val junit = "org.junit.jupiter:junit-jupiter-api:${Versions.junit}"
    const val junitEngine = "org.junit.jupiter:junit-jupiter-engine:${Versions.junit}"
    const val junitParams = "org.junit.jupiter:junit-jupiter-params:${Versions.junit}"
    const val mockitoKotlin = "com.nhaarman:mockito-kotlin:${Versions.mockitoKotlin}"
}
