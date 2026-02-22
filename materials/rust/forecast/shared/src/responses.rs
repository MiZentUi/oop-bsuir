use axum::{Json, http::StatusCode, response::IntoResponse};
use serde::{Deserialize, Serialize};
use utoipa::ToSchema;

#[derive(Serialize, Deserialize, ToSchema)]
pub struct StatusResponse {
    pub code: u16,
    pub message: String,
}

impl IntoResponse for StatusResponse {
    fn into_response(self) -> axum::response::Response {
        (StatusCode::from_u16(self.code).unwrap(), Json(self)).into_response()
    }
}

#[derive(Serialize, Deserialize, ToSchema)]
pub struct SuccessResponse<T>
where
    T: Serialize + 'static
{
    pub code: u16,
    pub message: String,
    pub data: T,
}

impl<T: Serialize + 'static> IntoResponse for SuccessResponse<T> {
    fn into_response(self) -> axum::response::Response {
        (StatusCode::from_u16(self.code).unwrap(), Json(self)).into_response()
    }
}
