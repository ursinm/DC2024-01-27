using Publisher.Middleware;
using Publisher.Repository.Implementation;
using Publisher.Repository.Interface;
using Publisher.Service.Implementation;
using Publisher.Service.Interface;
using Publisher.Storage.Common;
using Publisher.Storage.SqlDb;
using MyCoolMapper = Publisher.Entity.Common.AutoMapper;

var builder = WebApplication.CreateBuilder(args);
builder.Services.AddControllers();
builder.Services.AddDbContext<DbStorage, PostgresDbContext>();
new PostgresDbContext().Database.EnsureDeleted();
builder.Services.AddControllers().AddNewtonsoftJson();
builder.Services
    .AddScoped<ICreatorService, CreatorService>()
    .AddScoped<IMarkerService, MarkerService>()
    .AddScoped<IPostService, PostService>()
    .AddScoped<INewsService, NewsService>()
    .AddScoped<ICreatorRepository, CreatorRepository>()
    .AddScoped<IMarkerRepository, MarkerRepository>()
    .AddScoped<IPostRepository, PostRepository>()
    .AddScoped<INewsRepository, NewsRepository>();
builder.Services.AddAutoMapper(typeof(MyCoolMapper));

var app = builder.Build();
app.UseURLLog();
app.MapControllers();
app.Run();
