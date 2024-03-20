using AutoMapper;
using REST.Entity.Db;
using REST.Entity.DTO.RequestTO;
using REST.Entity.DTO.ResponseTO;

namespace REST.Entity.Common
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

            CreateMap<TweetRequestTO, Tweet>()
                .ForMember(dst => dst.Creator, map => map.MapFrom(src => new Creator() { Id = src.CreatorId }));
            CreateMap<Tweet, TweetResponseTO>()
                .ForMember(dst => dst.CreatorId, map => map.MapFrom(src => src.Creator.Id));
        }
    }
}
