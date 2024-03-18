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
    .AddScoped<ICommentService, CommentService>()
    .AddScoped<IIssueService, IssueService>();
builder.Services.AddAutoMapper(typeof(Program));

var app = builder.Build();
app.MapControllers();
app.Run();
