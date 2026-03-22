namespace Forecast.Utils;

[Serializable]
public class ApiCallException : System.Exception
{
    public ApiCallException() { }

    public ApiCallException(string message)
        : base(message) { }

    public ApiCallException(string message, System.Exception inner)
        : base(message, inner) { }

    [Obsolete(
        "This API supports obsolete formatter-based serialization. It should not be called or extended by application code."
    )]
    protected ApiCallException(
        System.Runtime.Serialization.SerializationInfo info,
        System.Runtime.Serialization.StreamingContext context
    )
        : base(info, context) { }
}
