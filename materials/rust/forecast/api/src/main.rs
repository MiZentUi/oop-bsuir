use std::error::Error;

use api::weather::{WeatherHandler, handle_get_current_weather};
use axum::{Router, routing::get};

fn main() -> Result<(), Box<dyn Error>>{
    dotenvy::dotenv()?;
    tokio::runtime::Builder::new_multi_thread()
        .enable_all()
        .build()?
        .block_on(serve());
    Ok(())
}

async fn serve() {
    let router = Router::new().route("/weather", get(handle_get_current_weather));
    let api = Router::new()
        .nest("/api/v1", router)
        .with_state(WeatherHandler::new());

    let listener = tokio::net::TcpListener::bind("0.0.0.0:3000").await.unwrap();
    axum::serve(listener, api).await.unwrap();
}
