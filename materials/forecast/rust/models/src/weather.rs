use rust_decimal::Decimal;
use serde_derive::{Deserialize, Serialize};
use utoipa::ToSchema;

#[derive(Serialize, Deserialize, ToSchema)]
pub struct CurrentWeather {
    #[schema(examples("0.0"))]
    pub temperature: Decimal,
}
