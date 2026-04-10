use rust_decimal::Decimal;

pub trait WeatherDataClient {
    fn location_current_temperature(
        &self,
        lat: Decimal,
        lon: Decimal,
    ) -> impl std::future::Future<Output = Result<Decimal, Box<dyn std::error::Error>>> + Send;
}
