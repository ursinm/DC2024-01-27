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
				context.Response.StatusCode = ex switch
				{
                    ValidationException => StatusCodes.Status400BadRequest,
                    NotFoundException => StatusCodes.Status404NotFound,
                    _ => StatusCodes.Status500InternalServerError
				};
				await context.Response.WriteAsJsonAsync(new { ErrorMessage =  ex.Message });
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
