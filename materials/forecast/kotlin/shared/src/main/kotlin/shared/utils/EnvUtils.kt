package shared.utils

fun getEnv(key: String, fallback: String): String =
    System.getProperty(key)
        ?: System.getenv(key)
        ?: fallback
