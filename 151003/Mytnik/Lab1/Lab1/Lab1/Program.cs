using Microsoft.EntityFrameworkCore;
using Lab1;
using Lab1.Services.Interfaces;
using Lab1.Services;
using AutoMapper;
using Lab1.Models;
using Lab1.DTO;
using Lab1.DB.BaseDBContext;
using Lab1.DB;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.

builder.Services.AddControllers();

builder.Services.AddDbContext<BaseContext, PostgreSQlDbContext>();
builder.Services
    .AddScoped<ICreatorService, CreatorService>()
    .AddScoped<ITweetService, TweetService>()
    .AddScoped<INoteService, NotesService>()
    .AddScoped<IMarkerService, MarkerService>();
builder.Services.AddAutoMapper(typeof(MappingProfile));
var app = builder.Build();

// Configure the HTTP request pipeline.

app.UseHttpsRedirection();

app.UseAuthorization();

app.MapControllers();

app.Run();
