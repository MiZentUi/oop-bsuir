use rust_decimal::Decimal;
use serde_derive::{Deserialize, Serialize};

#[derive(Serialize, Deserialize)]
pub struct CurrentWeather {
    pub temperature: Decimal
}