using System.Text.Json.Serialization;

namespace Forecast.Models;

class CurrentWeather(decimal temperature)
{
    [JsonPropertyName("temperature")]
    public decimal Temperature { get; } = temperature;
}
