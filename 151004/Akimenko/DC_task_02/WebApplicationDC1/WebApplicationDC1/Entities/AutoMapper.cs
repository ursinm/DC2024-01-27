using AutoMapper;
using static System.Net.Mime.MediaTypeNames;
using WebApplicationDC1.Entity.DataModel;
using WebApplicationDC1.Entity.DTO.Requests;
using WebApplicationDC1.Entity.DTO.Responses;

namespace WebApplicationDC1.Entities
{
    public class AutoMapper : Profile
    {
        public AutoMapper()
        {
            CreateMap<CreatorRequestTO, Creator>();
            CreateMap<Creator, CreatorResponseTO>();

            CreateMap<StickerRequestTO, Sticker>();
            CreateMap<Sticker, StickerResponseTO>();

            CreateMap<PostRequestTO, Post>();
            CreateMap<Post, PostResponseTO>();

            CreateMap<StoryRequestTO, Story>();
                //.ForMember(dst => dst.Creator, map => map.MapFrom(src => new Creator() { Id = src.CreatorId }));
            CreateMap<Story, StoryResponseTO>();
                //.ForMember(dst => dst.CreatorId, map => map.MapFrom(src => src.Creator.Id));
        }
    }
}
