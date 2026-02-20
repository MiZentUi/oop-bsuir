use serde::{Deserialize, Serialize};
use utoipa::ToSchema;

// TODO: utoipa ToScheme?
#[derive(Serialize, Deserialize, ToSchema)]
pub struct StatusResponse {
    pub code: u16,
    pub message: String,
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
