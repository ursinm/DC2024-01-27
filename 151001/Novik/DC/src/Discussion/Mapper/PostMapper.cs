using AutoMapper;
using Discussion.Models.DTOs.Requests;
using Discussion.Models.DTOs.Responses;
using Discussion.Models.Entity;

namespace Discussion.Mapper;

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