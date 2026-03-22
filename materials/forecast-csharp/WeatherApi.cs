using Forecast.Clients;
using Forecast.Models;
using Forecast.Utils;
using Microsoft.AspNetCore.Http.HttpResults;
using Microsoft.AspNetCore.Mvc;

namespace Forecast;

static class WeatherApi
{
    public static RouteGroupBuilder MapApiEndpoints(this RouteGroupBuilder groups)
    {
        groups
            .MapGet("weather", WeatherEndpoint)
            .WithName("GetCurrentWeather")
            .WithDisplayName("Get Current Weather")
            .WithTags(["weather"])
            .WithDescription("Returns current weather for given coordinates");

        return groups;
    }

    private static async Task<
        Results<Ok<CurrentWeather>, BadRequest<Error>, InternalServerError<Error>>
    > WeatherEndpoint(string lat, string lon, [FromServices] IWeatherDataClient client)
    {
        try
        {
            var latitude = decimal.Parse(lat);
            var longitude = decimal.Parse(lon);

            var result = await client.GetCurrentTemperatureAtLocation(latitude, longitude);
            var weather = new CurrentWeather(result);
            return TypedResults.Ok(weather);
        }
        catch (FormatException)
        {
            return TypedResults.BadRequest(new Error("Invalid coordinates."));
        }
        catch (OverflowException)
        {
            return TypedResults.BadRequest(new Error("Invalid coordinates."));
        }
        catch (ApiCallException e)
        {
            return TypedResults.InternalServerError(new Error(e.Message));
        }
    }
}

record Error(string Message);