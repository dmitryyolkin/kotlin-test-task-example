object Versions {
    const val kotlin = "1.4.10"
    const val kotlinJvmTarget = "1.8"

    //Plugins
    const val ktlintPlugin = "9.4.1"

    //Libs
    const val opencsv = "5.3"
    const val kotlinLoggerVersion = "2.0.3"
    const val logbackClassic = "1.2.3"

    // Libs for testing
    const val junit = "5.7.1"
}

object Plugins {
    const val ktlint = "org.jlleitschuh.gradle.ktlint"
}

object Libs {
    const val opencsv = "com.opencsv:opencsv:${Versions.opencsv}"
    const val logger = "io.github.microutils:kotlin-logging:${Versions.kotlinLoggerVersion}"
    const val logbackClassic = "ch.qos.logback:logback-classic:${Versions.logbackClassic}"

    // Test libraries
    const val junit = "org.junit.jupiter:junit-jupiter-api:${Versions.junit}"
    const val junitEngine = "org.junit.jupiter:junit-jupiter-engine:${Versions.junit}"
    const val junitParams = "org.junit.jupiter:junit-jupiter-params:${Versions.junit}"
}
