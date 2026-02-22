use axum::{Json, http::StatusCode, response::IntoResponse};
use serde::{Deserialize, Serialize};
use utoipa::{ToResponse, ToSchema};

#[derive(Serialize, Deserialize, ToResponse, ToSchema)]
pub struct StatusResponse {
    #[schema(examples(0))]
    pub code: u16,
    pub message: String,
}

impl IntoResponse for StatusResponse {
    fn into_response(self) -> axum::response::Response {
        (StatusCode::from_u16(self.code).unwrap(), Json(self)).into_response()
    }
}

#[derive(Serialize, Deserialize, ToResponse, ToSchema)]
pub struct SuccessResponse<T>
where
    T: ToSchema + Serialize + 'static,
{
    #[schema(examples(200))]
    pub code: u16,
    pub message: String,
    pub data: T,
}

impl<T> IntoResponse for SuccessResponse<T>
where
    T: ToSchema + Serialize + 'static,
{
    fn into_response(self) -> axum::response::Response {
        (StatusCode::from_u16(self.code).unwrap(), Json(self)).into_response()
    }
}
