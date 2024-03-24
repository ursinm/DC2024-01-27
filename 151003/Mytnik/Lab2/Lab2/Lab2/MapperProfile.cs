using AutoMapper;
using Lab2.DTO;
using Lab2.Models;
using System;

namespace Lab2
{
    public class MappingProfile : Profile
    {
        public MappingProfile()
        {
            CreateMap<CreatorRequestTo, Creator>();
            CreateMap<Creator, CreatorResponseTo>();
            CreateMap<TweetRequestTo, Tweet>();
            CreateMap<Tweet, TweetResponseTo>();
            CreateMap<NoteRequestTo, Note>();
            CreateMap<Note, NoteResponseTo>();
            CreateMap<Marker, MarkerResponseTo>();
            CreateMap<MarkerRequestTo, Marker>();
        }
    }
}
