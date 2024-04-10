using WebApplicationDC1.Repositories;
using WebApplicationDC1.Services.Implementations;
using WebApplicationDC1.Services.Interfaces;
using WebApplicationDC1.LoggerUrlApi;
using Microsoft.Extensions.DependencyInjection;
using WebApplicationDC1.Entity.DataModel;
using AutoMapper;
using WebApplicationDC1.Entity.DTO.Requests;
using WebApplicationDC1.Entity.DTO.Responses;
using CustomMapper = WebApplicationDC1.Entities.AutoMapper;
using Microsoft.EntityFrameworkCore;



var builder = WebApplication.CreateBuilder(args);

// Add services to the container.

builder.Services.AddControllers();

//builder.Services.AddDbContext<ApplicationContext, InMemoryDBContext>();
builder.Services.AddDbContext<ApplicationContext, PostgreDBContext>();

//new PostgreDBContext().Database.EnsureDeleted();

builder.Services.AddControllers().AddNewtonsoftJson();
builder.Services
.AddScoped<ICreatorService, CreatorService>()
.AddScoped<IStickerService, StickerService>()
.AddScoped<IPostService, PostService>()
.AddScoped<IStoryService, StoryService>();
//builder.Services.AddAutoMapper(typeof(Program));
builder.Services.AddAutoMapper(typeof(CustomMapper));


// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseAuthorization();

app.UseURLLog();

app.MapControllers();

app.Run();
