using AutoMapper;
using REST.Models.DTOs.RequestIn;
using REST.Models.DTOs.Responses;
using REST.Models.Entity;

namespace REST.Mapper;

public class UserMapper : Profile
{
    public UserMapper()
    {
        CreateMap<User, UserResponseTo>();
        CreateMap<UserRequestTo, User>();
    }
    
}