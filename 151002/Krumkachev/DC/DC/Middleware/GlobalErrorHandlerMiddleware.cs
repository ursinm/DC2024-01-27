using DC.Exceptions;
using FluentValidation;

namespace DC.Middleware
{
	public class GlobalErrorHandlerMiddleware(RequestDelegate next)
	{
		private readonly RequestDelegate _next = next;

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
