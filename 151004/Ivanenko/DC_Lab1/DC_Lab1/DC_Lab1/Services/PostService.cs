using DC_Lab1.DTO.Interface;
using DC_Lab1.DTO;
using Microsoft.EntityFrameworkCore;
using DC_Lab1.Services.Interfaces;
using AutoMapper;
using DC_Lab1.Models;

namespace DC_Lab1.Services
{
    public class PostService(IMapper _mapper, LabDbContext dbContext) : IPostService
    {
        public async Task<IResponseTo> CreateEnt(IRequestTo Dto)
        {
            var PostDto = (PostRequestTo)Dto;
            try
            {
                if (Validate(PostDto))
                {
                    var Post = _mapper.Map<Post>(PostDto);
                    dbContext.Posts.Add(Post);
                    await dbContext.SaveChangesAsync();
                    var response = _mapper.Map<PostResponseTo>(Post);
                    return response;

                }
                else
                {
                    throw new ArgumentException();
                }
            }
            catch
            {
                throw new ArgumentException();
            }


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
                throw new ArgumentException();
            }

        }

        public async Task<IResponseTo> GetEntById(int id)
        {
            try
            {
                return _mapper.Map<PostResponseTo>(await dbContext.Posts.FindAsync(id));

            }
            catch
            {
                throw new ArgumentException();
            }
        }

        public IEnumerable<IResponseTo> GetAllEnt()
        {
            try
            {
                return dbContext.Posts.Select(_mapper.Map<PostResponseTo>);

            }
            catch
            {
                throw new ArgumentException();
            }
        }

        public async Task<IResponseTo> UpdateEnt(IRequestTo Dto)
        {
            var PostDto = (PostRequestTo)Dto;
            try
            {
                if (Validate(PostDto))
                {
                    var newPost = _mapper.Map<Post>(PostDto);
                    dbContext.Posts.Update(newPost);
                    await dbContext.SaveChangesAsync();
                    var Post = _mapper.Map<PostResponseTo>(await dbContext.Posts.FindAsync(newPost.Id));
                    return Post;
                }
                else
                {
                    throw new ArgumentException();

                }
            }
            catch
            {

                throw new ArgumentException();
            }

        }

        public bool Validate(PostRequestTo PostDto)
        {
            if (PostDto?.Content?.Length < 2 || PostDto?.Content?.Length > 2048)
                return false;
           
            return true;
        }
    }
}
