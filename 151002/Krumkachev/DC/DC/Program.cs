using DC.Data;
using DC.Infrastructure.AutoMapper;
using DC.Infrastructure.Validators;
using DC.Middleware;
using DC.Repositories.Implementations;
using DC.Repositories.Interfaces;
using DC.Services.Impementations;
using DC.Services.Interfaces;
using FluentValidation;
using Microsoft.EntityFrameworkCore;

var builder = WebApplication.CreateBuilder(args);


builder.Services.AddControllers();
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

builder.Services.AddApiVersioning();

builder.Services.AddAutoMapper(typeof(MappingProfile));
builder.Services.AddValidatorsFromAssemblyContaining<CreatorRequestDtoValidator>();

builder.Services.AddDbContext<ApplicationDbContext>(opt => 
	opt.UseNpgsql(builder.Configuration.GetConnectionString("DefaultConnection")));

builder.Services.AddScoped<ICreatorRepository, SqlCreatorRepository>();
builder.Services.AddScoped<IIssueRepository, SqlIssueRepository>();
builder.Services.AddScoped<ILabelRepository, SqlLabelRepository>();
builder.Services.AddScoped<IPostRepository, SqlPostRepository>();


builder.Services.AddScoped<ICreatorService, CreatorService>();
builder.Services.AddScoped<IIssueService, IssueService>();
builder.Services.AddScoped<ILabelService, LabelService>();
builder.Services.AddScoped<IPostService, PostService>();

var app = builder.Build();

app.UseGlobalErrorHandler();

if (app.Environment.IsDevelopment())
{
	app.UseSwagger();
	app.UseSwaggerUI();
}

//app.UseHttpsRedirection();

app.UseAuthorization();

app.MapControllers();

app.Run();
