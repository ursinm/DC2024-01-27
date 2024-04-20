using DC.Exceptions;
using DC.Services.Interfaces;
using FluentValidation;
using Microsoft.AspNetCore.Http;

namespace DC.Middleware
{
    public class GlobalErrorHandlerMiddleware
    {
        private readonly RequestDelegate _next;

        public GlobalErrorHandlerMiddleware(RequestDelegate next)
        {
            _next = next;
        }

        public async Task InvokeAsync(HttpContext context)
        {
            try
            {
                await _next(context);
            }
            catch (Exception ex)
            {
                string errorCode;
                switch (ex)
                {
                    case NotFoundException:
                        context.Response.StatusCode = StatusCodes.Status404NotFound;
                        errorCode = "01";
                        break;
                    case ValidationException:
                        context.Response.StatusCode = StatusCodes.Status400BadRequest;
                        errorCode = "02";
                        break;
                    case AlreadyExistsException:
                        context.Response.StatusCode = StatusCodes.Status403Forbidden;
                        errorCode = "03";
                        break;
                    default:
                        context.Response.StatusCode = StatusCodes.Status500InternalServerError;
                        errorCode = "00";
                        break;
                }
                await context.Response.WriteAsJsonAsync(new
                {
                    ErrorMessage = ex.Message,
                    ErrorCode = context.Response.StatusCode.ToString() + errorCode,
                });
            }

            /*public async Task InvokeAsync(HttpContext context)
            {
                try
                {
                    await _next(context);
                }
                catch (Exception ex)
                {
                    context.Response.StatusCode = ex switch
                    {
                        ValidationException => StatusCodes.Status400BadRequest,
                        NotFoundException => StatusCodes.Status404NotFound,
                        _ => StatusCodes.Status500InternalServerError
                    };
                    await context.Response.WriteAsJsonAsync(new { ErrorMessage =  ex.Message });
                }
            }*/
        }
    }

    public static class GlobalErrorHandlerMiddlewareExtension
	{
	    public static IApplicationBuilder UseGlobalErrorHandler(this IApplicationBuilder app)
	    {
		    return app.UseMiddleware<GlobalErrorHandlerMiddleware>();
	    }
	}
}
