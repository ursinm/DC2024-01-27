using REST.Middleware;
using REST.Service.Implementation;
using REST.Service.Interface;
using REST.Storage.Common;
using REST.Storage.InMemoryDb;

var builder = WebApplication.CreateBuilder(args);
builder.Services.AddControllers();
builder.Services.AddDbContext<DbStorage, InMemoryDbContext>();
builder.Services.AddControllers().AddNewtonsoftJson();
builder.Services
    .AddScoped<ICreatorService, CreatorService>()
    .AddScoped<IMarkerService, MarkerService>()
    .AddScoped<IPostService, PostService>()
    .AddScoped<INewsService, NewsService>();
builder.Services.AddAutoMapper(typeof(Program));
builder.Services.AddSwaggerGen();

var app = builder.Build();

if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseURLLog();
app.MapControllers();
app.Run();
