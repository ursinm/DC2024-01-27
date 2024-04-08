using AutoMapper;
using DC_Lab1.DTO;
using DC_Lab1.Models;
using System;

namespace DC_Lab1
{
    public class MappingProfile : Profile
    {
        public MappingProfile()
        {
            CreateMap<EditorRequestTo, Editor>();
            CreateMap<Editor, EditorResponseTo>();
            CreateMap<TweetRequestTo, Tweet>();
            CreateMap<Tweet, TweetResponseTo>();
            CreateMap<PostRequestTo, Post>();
            CreateMap<Post, PostResponseTo>();
            CreateMap<Sticker, StickerResponseTo>();
            CreateMap<StickerRequestTo, Sticker>();
        }
    }
}
