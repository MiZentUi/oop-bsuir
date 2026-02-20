use std::error::Error;

use rust_decimal::Decimal;
use serde::Deserialize;
use ureq::http::StatusCode;

use crate::WeatherDataClient;

#[derive(Deserialize)]
struct Main {
    pub temp: Decimal,
}

#[derive(Deserialize)]
struct OpenWeatherResponse {
    pub main: Main,
}

#[derive(Clone)]
pub struct OpenWeatherClient {
    api_key: String,
    base_url: String,
}

impl OpenWeatherClient {
    // TODO: Box? Rc/Arc?
    pub fn new(api_key: String, base_url: String) -> OpenWeatherClient {
        OpenWeatherClient { api_key, base_url }
    }
    
}

impl WeatherDataClient for OpenWeatherClient {
    fn location_current_temperature(
        &self,
        lat: Decimal,
        lon: Decimal,
    ) -> Result<Decimal, Box<dyn Error>> {
        let url = format!(
            "{}?lat={}&lon={}&appid={}&units=metric",
            self.base_url, lat, lon, self.api_key
        );
        let mut resp = ureq::get(url).call()?;
        if resp.status() != StatusCode::OK {
            // TODO: Use an error?
            panic!(
                "openweather returned bad status: {}",
                resp.status().as_u16()
            );
        }
        let data: OpenWeatherResponse = serde_json::from_reader(resp.body_mut().as_reader())?;
        Ok(data.main.temp)
    }
}
