pub fn getenv(name: &str, fallback: String) -> String {
    std::env::var(name).unwrap_or(fallback)
}
