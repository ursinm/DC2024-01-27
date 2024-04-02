using Microsoft.EntityFrameworkCore;
using Lab2;
using Lab2.Services.Interfaces;
using Lab2.Services;
using AutoMapper;
using Lab2.Models;
using Lab2.DTO;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.

builder.Services.AddControllers();

builder.Services.AddDbContext<SqLiteDbContext>();
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
