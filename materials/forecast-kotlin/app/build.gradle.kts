plugins {
    kotlin("jvm") version "2.1.0"
    application
}

application {
    mainClass.set("MainKt")
}

dependencies {
    implementation(project(":api"))
    implementation(project(":shared"))

    implementation("io.ktor:ktor-server-core:3.1.1")
    implementation("io.ktor:ktor-server-netty:3.1.1")
    implementation("io.ktor:ktor-server-content-negotiation:3.1.1")
    implementation("io.ktor:ktor-serialization-jackson:3.1.1")
    implementation("io.github.smiley4:ktor-swagger-ui:4.1.2")
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.2")
}
