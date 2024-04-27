using AutoMapper;
using lab2.DTO;
using lab2.Models;

namespace lab2
{
    public class MappingProfile : Profile
    {
        public MappingProfile()
        {
            CreateMap<UserRequestTo, User>();
            CreateMap<User, UserResponseTo>();
            CreateMap<NewsRequestTo, News>();
            CreateMap<News, NewsResponseTo>();
            CreateMap<NoteRequestTo, Note>();
            CreateMap<Note, NoteResponseTo>();
            CreateMap<Label, LabelResponseTo>();
            CreateMap<LabelRequestTo, Label>();
        }
    }
}
