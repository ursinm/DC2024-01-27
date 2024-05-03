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

            CreateMap<MarkerRequestTO, Marker>();
            CreateMap<Marker, MarkerResponseTO>();

            CreateMap<PostRequestTO, Post>();
            CreateMap<Post, PostResponseTO>();

            CreateMap<NewsRequestTO, News>();
            CreateMap<News, NewsResponseTO>();
        }
    }
}
