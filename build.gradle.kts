val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    application
    kotlin("jvm") version "1.7.20"
    id("io.ktor.plugin") version "2.1.3"
    kotlin("plugin.serialization") version "1.6.20"
}

group = "ru.preis"
version = "0.0.1"
application {

    mainClass.set("ru.preis.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

val exposed_version: String by project
val h2_version: String by project


dependencies {


    implementation("io.ktor:ktor-server-resources:$ktor_version")

    /*DB dependencies*/
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")

    implementation("com.h2database:h2:$h2_version")

    implementation("com.zaxxer:HikariCP:3.4.2")
    /*DB dependencies*/


    /*serialization dependencies*/
//    implementation("io.ktor:ktor-server-data-conversion:$ktor_version")

    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:$exposed_version")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("io.ktor:ktor-server-status-pages:$ktor_version")
    implementation("io.ktor:ktor-server-request-validation:$ktor_version")

    /*serialization dependencies*/


    implementation("io.ktor:ktor-server-auth:$ktor_version")


    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-http-redirect-jvm:2.1.3")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.20")

}