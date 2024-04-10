using REST.Service.Implementation;
using REST.Service.Interface;
using REST.Storage.Common;
using REST.Storage.InMemoryDb;
using REST.Storage.SqlDb;

var builder = WebApplication.CreateBuilder(args);
builder.Services.AddControllers();
builder.Services.AddDbContext<DbStorage, PostgresDbContext>();
new PostgresDbContext().Database.EnsureDeleted();
builder.Services.AddControllers().AddNewtonsoftJson();
builder.Services
    .AddScoped<ICreatorService, CreatorService>()
    .AddScoped<IMarkerService, MarkerService>()
    .AddScoped<ICommentService, CommentService>()
    .AddScoped<IIssueService, IssueService>();
builder.Services.AddAutoMapper(typeof(Program));

var app = builder.Build();
app.MapControllers();
app.Run();
