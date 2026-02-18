use rust_decimal::Decimal;

pub trait WeatherDataClient {
    fn location_current_temperature(
        &self,
        lat: Decimal,
        lon: Decimal,
    ) -> Result<Decimal, Box<dyn std::error::Error>>;
}
