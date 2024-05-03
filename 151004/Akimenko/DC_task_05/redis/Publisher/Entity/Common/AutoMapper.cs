using AutoMapper;
using Publisher.Entity.Db;
using Publisher.Entity.DTO.RequestTO;
using Publisher.Entity.DTO.ResponseTO;

namespace Publisher.Entity.Common
{
    public class AutoMapper : Profile
    {
        public AutoMapper()
        {
            CreateMap<creatorRequestTO, creator>();
            CreateMap<creator, creatorResponseTO>();

            CreateMap<stickerRequestTO, sticker>();
            CreateMap<sticker, stickerResponseTO>();

            //CreateMap<storyRequestTO, story>();
            CreateMap<storyRequestTO, story>().ForMember(dest => dest.creatorId, opt => opt.MapFrom(src => src.creatorId));

            CreateMap<story, storyResponseTO>();
        }
    }
}
