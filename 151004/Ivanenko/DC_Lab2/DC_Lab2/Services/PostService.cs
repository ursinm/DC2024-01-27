using DC_Lab1.DTO.Interface;
using DC_Lab1.DTO;
using Microsoft.EntityFrameworkCore;
using DC_Lab1.Services.Interfaces;
using AutoMapper;
using DC_Lab1.Models;
using Microsoft.AspNetCore.Components.Forms;
using DC_Lab1.DB.BaseDBContext;

namespace DC_Lab1.Services
{
    public class PostService(IMapper _mapper, BaseDbContext dbContext) : IPostService
    {
        public async Task<IResponseTo> CreateEnt(IRequestTo Dto)
        {
            var PostDto = (PostRequestTo)Dto;

            if (!Validate(PostDto))
            {
                throw new InvalidDataException("Incorrect data for CREATE Post");

            }
            var Post = _mapper.Map<Post>(PostDto);
            dbContext.Posts.Add(Post);
            await dbContext.SaveChangesAsync();
            var response = _mapper.Map<PostResponseTo>(Post);
            return response;





        }

        public async Task DeleteEnt(int id)
        {
            try
            {
                var Post = await dbContext.Posts.FindAsync(id);
                dbContext.Posts.Remove(Post!);
                await dbContext.SaveChangesAsync();
                return;
            }
            catch
            {
                throw new Exception("Deletting Post exception");
            }

        }

        public async Task<IResponseTo> GetEntById(int id)
        {

            var post = _mapper.Map<PostResponseTo>(await dbContext.Posts.FindAsync(id));
            return post is not null ? _mapper.Map<PostResponseTo>(post) : throw new ArgumentNullException($"Not found post: {id}");



        }

        public IEnumerable<IResponseTo> GetAllEnt()
        {
            try
            {
                return dbContext.Posts.Select(_mapper.Map<PostResponseTo>);

            }
            catch
            {
                throw new Exception("Getting all posts exception");
            }
        }

        public async Task<IResponseTo> UpdateEnt(IRequestTo Dto)
        {
            var PostDto = (PostRequestTo)Dto;

            if (!Validate(PostDto))

            {
                throw new InvalidDataException("Incorrect data for UPDATE Post");

            }
            var newPost = _mapper.Map<Post>(PostDto);
            dbContext.Posts.Update(newPost);
            await dbContext.SaveChangesAsync();
            var Post = _mapper.Map<PostResponseTo>(await dbContext.Posts.FindAsync(newPost.Id));
            return Post;


        }

        public bool Validate(PostRequestTo PostDto)
        {
            if (PostDto?.Content?.Length < 2 || PostDto?.Content?.Length > 2048)
                return false;

            return true;
        }
    }
}
