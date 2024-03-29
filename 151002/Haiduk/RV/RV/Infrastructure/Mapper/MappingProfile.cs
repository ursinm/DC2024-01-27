using Api.DTO.RequestDTO;
using Api.DTO.ResponseDTO;
using Api.Models;
using AutoMapper;

namespace Api.Infrastructure.Mapper
{
    public class MappingProfile : Profile
    {
        public MappingProfile()
        {
            CreateMap<Creator, CreatorResponseDto>();
            CreateMap<CreatorRequestDto, Creator>();

            CreateMap<News, NewsResponseDto>();
            CreateMap<NewsRequestDto, News>();

            CreateMap<Sticker, StickerResponseDto>();
            CreateMap<StickerRequestDto, Sticker>();

            CreateMap<Note, NoteResponseDto>();
            CreateMap<NoteRequestDto, Note>();
        }
    }
}
