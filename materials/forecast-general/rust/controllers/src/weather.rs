use std::error::Error;

use clients::WeatherDataClient;
use models::weather::CurrentWeather;
use rust_decimal::Decimal;

#[derive(Clone)]
pub struct CurrentWeatherController<T: WeatherDataClient + Clone> {
    pub client: T,
}

impl<T: WeatherDataClient + Clone> CurrentWeatherController<T> {
    pub fn new(client: T) -> Self {
        CurrentWeatherController { client }
    }
    pub async fn get_current_weather(
        &self,
        lat: Decimal,
        lon: Decimal,
    ) -> Result<CurrentWeather, Box<dyn Error>> {
        Ok(CurrentWeather {
            temperature: self.client.location_current_temperature(lat, lon).await?,
        })
    }
}
