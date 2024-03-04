using AutoMapper;
using REST.Models.DTOs.RequestIn;
using REST.Models.DTOs.Responses;
using REST.Models.Entity;

namespace REST.Mapper;

public class NewsMapper : Profile
{
    public NewsMapper()
    {
        CreateMap<News, NewsResponseTo>();
        CreateMap<NewsRequestTo, News>();
    }
    
}