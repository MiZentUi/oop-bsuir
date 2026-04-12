plugins {
    kotlin("jvm") version "2.1.0"
}

dependencies {
    implementation(project(":clients"))
    implementation(project(":controllers"))
    implementation(project(":models"))
    implementation(project(":shared"))

    implementation("io.ktor:ktor-server-core:3.1.1")
    implementation("io.ktor:ktor-server-netty:3.1.1")
    implementation("io.ktor:ktor-server-content-negotiation:3.1.1")
    implementation("io.ktor:ktor-serialization-jackson:3.1.1")
    implementation("io.github.smiley4:ktor-swagger-ui:4.1.2")
}
