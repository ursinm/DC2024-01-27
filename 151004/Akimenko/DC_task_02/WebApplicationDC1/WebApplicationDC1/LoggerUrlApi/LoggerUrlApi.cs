namespace WebApplicationDC1.LoggerUrlApi
{
    public class LoggerUrlApi(RequestDelegate Next, ILogger<LoggerUrlApi> Logger)
    {
        public async Task InvokeAsync(HttpContext context)
        {
            Logger.LogInformation("{Context.Request.Path}", context.Request.Path);
            await Next(context);
        }
    }

    public static class LogExtension
    {
        public static IApplicationBuilder UseURLLog(this IApplicationBuilder builder)
        {
            return builder.UseMiddleware<LoggerUrlApi>();
        }
    }
}
