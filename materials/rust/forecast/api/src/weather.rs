use axum::{
    extract::{Query, State, rejection::QueryRejection},
    http::StatusCode,
    response::IntoResponse,
};
use clients::OpenWeatherClient;
use controllers::weather::CurrentWeatherController;
use models::weather::CurrentWeather;
use rust_decimal::Decimal;
use serde::Deserialize;
use shared::{
    responses::{self, StatusResponse, SuccessResponse},
    utils,
};
use utoipa::{IntoParams, ToSchema};
use utoipa_axum::{router::OpenApiRouter, routes};

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

#[derive(Deserialize, IntoParams, ToSchema)]
#[into_params(parameter_in=Query)]
pub struct GetCurrentWeatherQuery {
    /// Latitude
    #[param(default = "18.300231990440125")]
    lat: Decimal,
    /// Longitude
    #[param(default = "-64.8251590359234")]
    lon: Decimal,
}

pub fn routers() -> OpenApiRouter {
    OpenApiRouter::default()
        .routes(routes!(handle_get_current_weather))
        .with_state(WeatherHandler::new())
}

#[utoipa::path(get,
    path = "/weather",
    params(GetCurrentWeatherQuery),
    summary = "Get Current Weather",
    description = "Returns current weather for given coordinates",
    tags = ["weather"],
    responses(
         (status=200, body=SuccessResponse<CurrentWeather>),
         (status=400, body=StatusResponse),
         (status=500, body=StatusResponse)
    ),
)]
pub async fn handle_get_current_weather(
    State(h): State<WeatherHandler>,
    query: Result<Query<GetCurrentWeatherQuery>, QueryRejection>,
) -> axum::response::Response {
    if query.is_err() {
        return responses::StatusResponse {
            code: StatusCode::BAD_REQUEST.as_u16(),
            message: "invalid coordinates".to_string(),
        }
        .into_response();
    }
    let Query(GetCurrentWeatherQuery { lat, lon }) = query.unwrap();
    match h.controller.get_current_weather(lat, lon) {
        Ok(data) => responses::SuccessResponse {
            code: StatusCode::OK.as_u16(),
            message: "Success".to_owned(),
            data,
        }
        .into_response(),
        Err(err) => responses::StatusResponse {
            code: StatusCode::INTERNAL_SERVER_ERROR.as_u16(),
            message: err.to_string(),
        }
        .into_response(),
    }
}
