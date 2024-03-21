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
            CreateMap<CreatorRequestTO, Creator>()
                .ForMember(dst => dst.Id, map => map.MapFrom(src => 1));
            CreateMap<Creator, CreatorResponseTO>();

            CreateMap<MarkerRequestTO, Marker>()
                .ForMember(dst => dst.Id, map => map.MapFrom(src => 1));
            CreateMap<Marker, MarkerResponseTO>();

            CreateMap<CommentRequestTO, Comment>()
                .ForMember(dst => dst.Id, map => map.MapFrom(src => 1));
            CreateMap<Comment, CommentResponseTO>();

            CreateMap<IssueRequestTO, Issue>()
                .ForMember(dst => dst.Creator, map => map.MapFrom(src => new Creator() { Id = src.CreatorId }))
                .ForMember(dst => dst.Id, map => map.MapFrom(src => 1));
            CreateMap<Issue, IssueResponseTO>()
                .ForMember(dst => dst.CreatorId, map => map.MapFrom(src => src.Creator.Id));
        }
    }
}
