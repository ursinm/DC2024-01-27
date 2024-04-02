using WebApplicationDC1.Repositories;
using WebApplicationDC1.Services.Implementations;
using WebApplicationDC1.Services.Interfaces;
using WebApplicationDC1.LoggerUrlApi;
using Microsoft.Extensions.DependencyInjection;
using WebApplicationDC1.Entity.DataModel;
using AutoMapper;
using WebApplicationDC1.Entity.DTO.Requests;
using WebApplicationDC1.Entity.DTO.Responses;

//using (ApplicationContext db = new ApplicationContext())
//{
//    // создаем два объекта User
//    Creator tom = new Creator { Login = "Tom", FirstName = "33" };
//    Creator alice = new Creator { Login = "Alice", FirstName = "26" };

//    // добавляем их в бд
//    db.Users.Add(tom);
//    db.Users.Add(alice);
//    db.SaveChanges();
//    Console.WriteLine("Объекты успешно сохранены");

//    // получаем объекты из бд и выводим на консоль
//    var users = db.Users.ToList();
//    Console.WriteLine("Список объектов:");
//    foreach (Creator u in users)
//    {
//        Console.WriteLine($"{u.Id}.{u.Login} - {u.FirstName}");
//    }
//}




var builder = WebApplication.CreateBuilder(args);

// Add services to the container.

builder.Services.AddControllers();

builder.Services.AddDbContext<ApplicationContext, InMemoryDBContext>();
builder.Services.AddControllers().AddNewtonsoftJson();
builder.Services
.AddScoped<ICreatorService, CreatorService>()
.AddScoped<IStickerService, StickerService>()
.AddScoped<IPostService, PostService>()
.AddScoped<IStoryService, StoryService>();
//builder.Services.AddAutoMapper(typeof(Program));
builder.Services.AddAutoMapper(typeof(MappingProfile));


// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseAuthorization();

app.UseURLLog();

app.MapControllers();

app.Run();


public class MappingProfile : Profile
{
    public MappingProfile()
    {
        CreateMap<StickerRequestTO, Sticker>();
        CreateMap<Sticker, StickerResponseTO>();

        CreateMap<CreatorRequestTO, Creator>();
        CreateMap<Creator, CreatorResponseTO>();

        CreateMap<StoryRequestTO, Story>();
        CreateMap<Story, StoryResponseTO>();

        CreateMap<PostRequestTO, Post>();
        CreateMap<Post, PostResponseTO>();
    }
}