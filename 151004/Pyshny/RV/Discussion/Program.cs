using Cassandra;
using Confluent.Kafka;
using Discussion.Models;
using Discussion.Repositories;
using Discussion.Repositories.SQLRepositories;
using Discussion.Services.DataProviderServices;
using Discussion.Services.DataProviderServices.SQL;
using Discussion.Services.Kafka;
using Discussion.Services.Mappers;
using Microsoft.EntityFrameworkCore;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

builder.Services.AddTransient<IRepository<Note>, NoSQLNoteRepository>();
builder.Services.AddTransient<INoteDataProvider, NoSQLNoteDataProvider>();
builder.Services.AddTransient<IKafkaCore, KafkaCore>();

builder.Services.AddAutoMapper(typeof(NoteMapper));

builder.Services.AddTransient<IDataProvider, DataProvider>();

builder.Services.AddControllers();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseAuthorization();

app.MapControllers();

var kafka = app.Services.GetService<IKafkaCore>();
Thread kafkaThread = new Thread(kafka.StartConsuming);
kafkaThread.Start();
app.Run();
