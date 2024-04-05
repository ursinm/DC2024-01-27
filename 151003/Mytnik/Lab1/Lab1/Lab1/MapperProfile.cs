using AutoMapper;
using Lab1.DTO;
using Lab1.Models;
using System;

namespace Lab1
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
