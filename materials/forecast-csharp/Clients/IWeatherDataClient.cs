namespace Forecast.Clients;

interface IWeatherDataClient
{
    Task<decimal> GetCurrentTemperatureAtLocation(decimal latitude, decimal longitude);
}
