using AutoMapper;
using REST.Models.DTOs.RequestIn;
using REST.Models.DTOs.Responses;
using REST.Models.Entity;

namespace REST.Mapper;

public class PostMapper : Profile
{

    public PostMapper()
    {
        CreateMap<Post, PostResponseTo>()
            .ForMember(dest => dest.id, opt => opt.MapFrom(src => src.id))
            .ForMember(dest => dest.newsId, opt => opt.MapFrom(src => src.newsId))
            .ForMember(dest => dest.content, opt => opt.MapFrom(src => src.content));
        CreateMap<PostRequestTo, Post>()
            .ForMember(dest => dest.id, opt => opt.MapFrom(src => src.id))
            .ForMember(dest => dest.newsId, opt => opt.MapFrom(src => src.newsId))
            .ForMember(dest => dest.content, opt => opt.MapFrom(src => src.content));
    }
    
}