using System.Net;
using Microsoft.AspNetCore.Http.HttpResults;

namespace Forecast.Shared.Responses;

record Status(ushort Code, string Message)
{
    public static Status Create(HttpStatusCode code, string message) => new((ushort)code, message);

    public static Status Create(ushort code, string message) => new(code, message);
}
