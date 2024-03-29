using Api.Infrastructure.Mapper;
using Api.Infrastructure.Validators;
using Api.Repositories.Implementations;
using Api.Repositories.Interfaces;
using Api.Services.Impementations;
using Api.Services.Interfaces;
using FluentValidation;

namespace Api.Extensions
{
    public static class ServiceProviderExtensions
    {
        public static IServiceCollection AddRepositories(this IServiceCollection services)
        {
            services.AddSingleton<ICreatorRepository, InMemoryCreatorRepository>();
            services.AddSingleton<INewsRepository, InMemoryNewsRepository>();
            services.AddSingleton<IStickerRepository, InMemoryStickerRepository>();
            services.AddSingleton<INoteRepository, InMemoryNoteRepository>();

            return services;
        }

        public static IServiceCollection AddServices(this IServiceCollection services)
        {
            services.AddScoped<ICreatorService, CreatorService>();
            services.AddScoped<INewsService, NewsService>();
            services.AddScoped<IStickerService, StickerService>();
            services.AddScoped<INoteService, NoteService>();

            return services;
        }

        public static IServiceCollection AddInfrastructure(this IServiceCollection services)
        {
            services.AddAutoMapper(typeof(MappingProfile));
            services.AddValidatorsFromAssemblyContaining<CreatorRequestDtoValidator>();

            return services;
        }
    }
}
