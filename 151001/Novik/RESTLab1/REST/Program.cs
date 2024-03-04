using REST.Mapper;
using REST.Models.DTOs.RequestIn;
using REST.Models.DTOs.Responses;
using REST.Repositories;
using REST.Repositories.interfaces;
using REST.Repositories.sample;
using REST.Services;
using REST.Services.interfaces;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddControllersWithViews();
builder.Services.AddAutoMapper(typeof(LabelMapper));
builder.Services.AddAutoMapper(typeof(NewsMapper));
builder.Services.AddAutoMapper(typeof(PostMapper));
builder.Services.AddAutoMapper(typeof(UserMapper));

builder.Services.AddSingleton<ILabelRepository, LabelRepository>();
builder.Services.AddSingleton<IUserRepository, UserRepository>();
builder.Services.AddSingleton<INewsRepository, NewsRepository>();
builder.Services.AddSingleton<IPostRepository, PostRepository>();

builder.Services.AddScoped<IUserService,UserService>();
builder.Services.AddScoped<ILabelService,LabelService>();
builder.Services.AddScoped<INewsService,NewsService>();
builder.Services.AddScoped<IPostService,PostService>();
var app = builder.Build();

// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Home/Error");
    // The default HSTS value is 30 days. You may want to change this for production scenarios, see https://aka.ms/aspnetcore-hsts.
    app.UseHsts();
}

app.UseHttpsRedirection();
app.UseStaticFiles();

app.UseRouting();

app.UseAuthorization();

app.MapControllerRoute(
    name: "default",
    pattern: "{controller=Home}/{action=Index}/{id?}");

app.Run();