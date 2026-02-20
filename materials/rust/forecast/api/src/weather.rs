
use axum::{
    Json,
    extract::{Query, State},
    http::{StatusCode}, response::IntoResponse,
};
use clients::OpenWeatherClient;
use controllers::weather::CurrentWeatherController;
use rust_decimal::Decimal;
use serde::Deserialize;
use shared::{responses, utils};

#[derive(Clone)]
pub struct WeatherHandler {
    pub controller: CurrentWeatherController<OpenWeatherClient>,
}

impl WeatherHandler {
    pub fn new() -> WeatherHandler {
        WeatherHandler {
            controller: controllers::weather::CurrentWeatherController::new(
                OpenWeatherClient::new(
                    utils::getenv("OPENWEATHER_API_KEY", String::new()),
                    utils::getenv("OPENWEATHER_BASE_URL", String::new()),
                ),
            ),
        }
    }
}

// TODO: implement ToSchema for Decimal

#[derive(Deserialize)]
pub struct GetCurrentWeatherParams {
    lat: Decimal,
    lon: Decimal,
}

pub async fn handle_get_current_weather(
    State(h): State<WeatherHandler>,
    Query(GetCurrentWeatherParams { lat, lon }): Query<GetCurrentWeatherParams>,
) -> impl IntoResponse {
    match h.controller.get_current_weather(lat, lon)  {
        Ok(data) => (
            StatusCode::OK,
            Json(responses::SuccessResponse {
                code: StatusCode::OK.as_u16(),
                message: "Success".to_owned(),
                data,
            }),
        ).into_response(),
        Err(err) => (
            StatusCode::INTERNAL_SERVER_ERROR,
            Json(responses::StatusResponse {
                code: StatusCode::INTERNAL_SERVER_ERROR.as_u16(),
                message: err.to_string()
            })
        ).into_response(),
    }
}
