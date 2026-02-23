package shared.utils

fun getEnv(key: String, fallback: String): String =
    System.getenv(key) ?: fallback
