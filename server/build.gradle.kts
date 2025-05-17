import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ksp)
}

group = "io.github.rubenquadros"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.bundles.ktor)
    implementation(libs.bundles.ktor.client)

    implementation(libs.bundles.koin)
    implementation(libs.koin.annotation)
    ksp(libs.koin.ksp)

    implementation(libs.coroutines.jvm)

    implementation(libs.kotlinx.datetime)

    implementation(libs.google.client)

    implementation(project(":database"))

    testImplementation(libs.koin.test)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.coroutines.test)
}

ksp {
    arg("KOIN_CONFIG_CHECK","true")
    arg("KOIN_DEFAULT_MODULE","false")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("io.github.rubenquadros.timetowish.server.ApplicationKt")
}

tasks.withType<ShadowJar> {
    mergeServiceFiles()
}

configurations {
    testImplementation.get().exclude("org.jetbrains.kotlin", "kotlin-test-junit")
}

tasks.withType<ShadowJar> {
    mergeServiceFiles()
}