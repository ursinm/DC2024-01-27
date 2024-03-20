using AutoMapper;
using REST.Models.DTOs.RequestIn;
using REST.Models.DTOs.Responses;
using REST.Models.Entity;

namespace REST.Mapper;

public class UserMapper : Profile
{
    public UserMapper()
    {
        CreateMap<User, UserResponseTo>()
            .ForMember(dest => dest.id, opt => opt.MapFrom(src => src.id))
            .ForMember(dest => dest.login, opt => opt.MapFrom(src => src.login))
            .ForMember(dest => dest.password, opt => opt.MapFrom(src => src.password))
            .ForMember(dest => dest.firstname, opt => opt.MapFrom(src => src.firstname))
            .ForMember(dest => dest.lastname, opt => opt.MapFrom(src => src.lastname));
        CreateMap<UserRequestTo, User>()
            .ForMember(dest => dest.id, opt => opt.MapFrom(src => src.id))
            .ForMember(dest => dest.login, opt => opt.MapFrom(src => src.login))
            .ForMember(dest => dest.password, opt => opt.MapFrom(src => src.password))
            .ForMember(dest => dest.firstname, opt => opt.MapFrom(src => src.firstname))
            .ForMember(dest => dest.lastname, opt => opt.MapFrom(src => src.lastname));
    }
    
}