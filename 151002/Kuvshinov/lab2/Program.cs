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
builder.Services.AddValidatorsFromAssemblyContaining<EditorRequestDtoValidator>();

builder.Services.AddDbContext<ApplicationDbContext>(opt =>
    opt.UseNpgsql(builder.Configuration.GetConnectionString("DefaultConnection")));

builder.Services.AddScoped<IEditorRepository, SqlEditorRepository>();
builder.Services.AddScoped<IStoryRepository, SqlStoryRepository>();
builder.Services.AddScoped<ILabelRepository, SqlLabelRepository>();
builder.Services.AddScoped<INoteRepository, SqlNoteRepository>();

builder.Services.AddScoped<IEditorService, EditorService>();
builder.Services.AddScoped<IStoryService, StoryService>();
builder.Services.AddScoped<ILabelService, LabelService>();
builder.Services.AddScoped<INoteService, NoteService>();

var app = builder.Build();

app.UseGlobalErrorHandler();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment()) {
    app.UseSwagger();
    app.UseSwaggerUI();
}

//app.UseHttpsRedirection();

app.UseAuthorization();

app.MapControllers();

app.Run();
