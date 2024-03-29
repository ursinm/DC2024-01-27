using REST.Middleware;
using REST.Service.Implementation;
using REST.Service.Interface;
using REST.Storage.Common;
using REST.Storage.InMemoryDb;
using REST.Storage.SqlDb;
using MyCoolMapper = REST.Entity.Common.AutoMapper;

var builder = WebApplication.CreateBuilder(args);
builder.Services.AddControllers();
builder.Services.AddDbContext<DbStorage, InMemoryDbContext>();
//new PostgresDbContext().Database.EnsureDeleted();
builder.Services.AddControllers().AddNewtonsoftJson();
builder.Services
    .AddScoped<ICreatorService, CreatorService>()
    .AddScoped<IStickerService, StickerService>()
    .AddScoped<IPostService, PostService>()
    .AddScoped<ITweetService, TweetService>();
builder.Services.AddAutoMapper(typeof(MyCoolMapper));

var app = builder.Build();
app.UseURLLog();
app.MapControllers();
app.Run();
