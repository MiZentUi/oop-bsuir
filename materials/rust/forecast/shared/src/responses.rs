use serde::{Deserialize, Serialize};

// TODO: utoipa ToScheme?
#[derive(Serialize, Deserialize)]
pub struct StatusResponse {
    pub code: u16,
    pub message: String,
}

#[derive(Serialize, Deserialize)]
pub struct SuccessResponse<T>
where
    T: Serialize + 'static
{
    pub code: u16,
    pub message: String,
    pub data: T,
}
