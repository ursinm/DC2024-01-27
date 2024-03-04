using AutoMapper;
using REST.Models.DTOs.RequestIn;
using REST.Models.DTOs.Responses;
using REST.Models.Entity;

namespace REST.Mapper;

public class LabelMapper:Profile
{
    public LabelMapper()
    {
        CreateMap<Label, LabelResponseTo>();
        CreateMap<LabelRequestTo, Label>();
    }
}