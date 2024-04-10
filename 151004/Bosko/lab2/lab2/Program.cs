using lab2;
using lab2.Services;
using lab2.Services.Interface;
using lab2.DB.BaseDBContext;
using lab2.DB;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.

builder.Services.AddControllers();

builder.Services.AddDbContext<BaseDbContext, PostgreSQLDbContext>();
builder.Services
    .AddScoped<IUserService, UserService>()
    .AddScoped<INewsService, NewsService>()
    .AddScoped<INoteService, NoteService>()
    .AddScoped<ILabelService, LabelService>();
builder.Services.AddAutoMapper(typeof(MappingProfile));
var app = builder.Build();

// Configure the HTTP request pipeline.

app.UseHttpsRedirection();

app.UseAuthorization();

app.MapControllers();

app.Run();