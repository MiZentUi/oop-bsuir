using Forecast.Clients;
using Forecast.Models.Weather;

namespace Forecast.Controllers;

class CurrentWeatherController(IWeatherDataClient client)
{
    private readonly IWeatherDataClient client = client;

    public async Task<CurrentWeather> GetCurrentWeather(decimal latitude, decimal longitude)
    {
        var temperature = await client.GetCurrentTemperatureAtLocation(latitude, longitude);
        return new(temperature);
    }
}
