import api.weatherRoutes
import com.fasterxml.jackson.databind.SerializationFeature
import io.github.cdimascio.dotenv.dotenv
import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*

fun main() {
    val dotenv = dotenv {
        ignoreIfMissing = false
    }
    dotenv.entries().forEach { entry ->
        System.setProperty(entry.key, entry.value)
    }

    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            jackson {
                disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            }
        }

        install(SwaggerUI) {
            info {
                title = "Weather Example API"
                version = "1.0"
            }
        }

        routing {
            route("/api/v1") {
                weatherRoutes()
            }
        }
    }.start(wait = true)
}
