using System.ComponentModel;
using System.Net;
using Forecast.Clients;
using Forecast.Models;
using Forecast.Shared.Responses;
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
        Results<Ok<Success<CurrentWeather>>, BadRequest<Status>, InternalServerError<Status>>
    > WeatherEndpoint(
        [FromServices] IWeatherDataClient client,
        [DefaultValue("18.300231990440125")] string lat,
        [DefaultValue("-64.8251590359234")] string lon
    )
    {
        try
        {
            var latitude = decimal.Parse(lat);
            var longitude = decimal.Parse(lon);

            var result = await client.GetCurrentTemperatureAtLocation(latitude, longitude);
            var weather = new CurrentWeather(result);
            return TypedResults.Ok(Success.Create(200, "success", weather));
        }
        catch (FormatException)
        {
            return TypedResults.BadRequest(Status.Create(400, "invalid coordinates"));
        }
        catch (OverflowException)
        {
            return TypedResults.BadRequest(Status.Create(400, "invalid coordinates"));
        }
        catch (ApiCallException e)
        {
            return TypedResults.InternalServerError(Status.Create(500, e.Message));
        }
    }
}
