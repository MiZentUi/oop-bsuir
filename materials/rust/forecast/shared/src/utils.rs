pub fn getenv<'a>(name: &str, fallback: String) -> String {
    std::env::var(name).unwrap_or(fallback)
}