plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
rootProject.name = "forecast-kotlin"

include(
    "app",
    "api",
    "clients",
    "controllers",
    "models",
    "shared"
)
