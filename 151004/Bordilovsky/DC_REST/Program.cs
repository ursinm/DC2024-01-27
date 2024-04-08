
using DC_REST.DTOs.Request;
using DC_REST.Entities;
using DC_REST.Repositories;
using DC_REST.Services;
using DC_REST.Services.Interfaces;
using DC_REST.Services.Mappers;
using DC_REST.Validators;

namespace DC_REST
{
	public class Program
	{
		public static void Main(string[] args)
		{
			var builder = WebApplication.CreateBuilder(args);
			builder.WebHost.UseUrls("http://localhost:24110");


			// Add services to the container.

			builder.Services.AddControllers();
			// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
			builder.Services.AddEndpointsApiExplorer();
			builder.Services.AddSwaggerGen();

			builder.Services.AddSingleton<IRepository<Issue>, InMemoryRepository<Issue>>();
			builder.Services.AddTransient<IValidator<IssueRequestTo>, IssueValidator>();
			builder.Services.AddTransient<IIssueService,IssueService>();

			builder.Services.AddSingleton<IRepository<User>, InMemoryRepository<User>>();
			builder.Services.AddTransient<IValidator<UserRequestTo>, UserValidator>();
			builder.Services.AddTransient<IUserService, UserService>();

			builder.Services.AddSingleton<IRepository<Label>, InMemoryRepository<Label>>();
			builder.Services.AddTransient<IValidator<LabelRequestTo>, LabelValidator>();
			builder.Services.AddTransient<ILabelService, LabelService>();


			builder.Services.AddSingleton<IRepository<Note>, InMemoryRepository<Note>>();
			builder.Services.AddTransient<IValidator<NoteRequestTo>, NoteValidator>();
			builder.Services.AddTransient<INoteService, NoteService>();

			builder.Services.AddAutoMapper(typeof(IssueMapper));
			builder.Services.AddAutoMapper(typeof(LabelMapper));
			builder.Services.AddAutoMapper(typeof(NoteMapper));
			builder.Services.AddAutoMapper(typeof(UserMapper));

			var app = builder.Build();

			// Configure the HTTP request pipeline.
			if (app.Environment.IsDevelopment())
			{
				app.UseSwagger();
				app.UseSwaggerUI();
			}

			app.UseHttpsRedirection();

			app.UseAuthorization();


			app.MapControllers();

			app.Run();


		}
	}
}
