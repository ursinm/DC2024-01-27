using AutoMapper;
using REST.Models.DTOs.RequestIn;
using REST.Models.DTOs.Responses;
using REST.Models.Entity;

namespace REST.Mapper;

public class PostMapper : Profile
{

    public PostMapper()
    {
        CreateMap<Post, PostResponseTo>();
        CreateMap<PostRequestTo, Post>();
    }
    
}