using AutoMapper;
using Microsoft.AspNetCore.JsonPatch;
using Microsoft.EntityFrameworkCore;
using WebApplicationDC1.Entity.DataModel;
using WebApplicationDC1.Entity.DTO.Requests;
using WebApplicationDC1.Entity.DTO.Responses;
using WebApplicationDC1.Repositories;
using WebApplicationDC1.Services.Interfaces;

namespace WebApplicationDC1.Services.Implementations
{
    public class PostService(ApplicationContext applicationContext, IMapper mapper) : IPostService
    {
        private readonly ApplicationContext _context = applicationContext;
        private readonly IMapper _mapper = mapper;

        public async Task<PostResponseTO> Add(PostRequestTO post)
        {
            var newPost = new Post
            {
                StoryId = post.StoryId,
                Content = post.Content
            };

            if (!Validate(newPost))
            {
                throw new InvalidDataException("POST is not valid");
            }

            _context.Posts.Add(newPost);
            await _context.SaveChangesAsync();

            return _mapper.Map<PostResponseTO>(newPost);
        }


        //public async Task<PostResponseTO> Add(PostRequestTO post)
        //{
        //    var p = _mapper.Map<Post>(post);

        //    if (!Validate(p))
        //    {
        //        throw new InvalidDataException("POST is not valid");
        //    }

        //    _context.Posts.Add(p);
        //    await _context.SaveChangesAsync();

        //    return _mapper.Map<PostResponseTO>(p);
        //}

        public IList<PostResponseTO> GetAll()
        {
            return _context.Posts.Select(_mapper.Map<PostResponseTO>).ToList();
        }

        public async Task<PostResponseTO> Patch(int id, JsonPatchDocument<Post> patch)
        {
            var target = await _context.FindAsync<Post>(id)
                ?? throw new ArgumentNullException($"POST {id} not found at PATCH {patch}");

            patch.ApplyTo(target);
            await _context.SaveChangesAsync();

            return _mapper.Map<PostResponseTO>(target);
        }

        public async Task<bool> Remove(int id)
        {
            var target = new Post() { Id = id };

            _context.Remove(target);
            await _context.SaveChangesAsync();

            return true;
        }

        public async Task<PostResponseTO> Update(PostRequestTO post)
        {
            var existingPost = await _context.Posts.FindAsync(post.Id);

            if (existingPost == null)
            {
                throw new ArgumentNullException($"POST {post.Id} not found");
            }

            existingPost.StoryId = post.StoryId;
            existingPost.Content = post.Content;

            if (!Validate(existingPost))
            {
                throw new InvalidDataException($"UPDATE invalid data: {post}");
            }

            _context.Entry(existingPost).State = EntityState.Modified;
            await _context.SaveChangesAsync();

            return _mapper.Map<PostResponseTO>(existingPost);
        }


        //public async Task<PostResponseTO> Update(PostRequestTO post)
        //{
        //    var p = _mapper.Map<Post>(post);

        //    if (!Validate(p))
        //    {
        //        throw new InvalidDataException($"UPDATE invalid data: {post}");
        //    }

        //    _context.Update(p);
        //    await _context.SaveChangesAsync();

        //    return _mapper.Map<PostResponseTO>(p);
        //}

        public async Task<PostResponseTO> GetByID(int id)
        {
            var a = await _context.Posts.FindAsync(id);

            return a is not null ? _mapper.Map<PostResponseTO>(a)
                : throw new ArgumentNullException($"Not found POST {id}");
        }

        public Task<IList<Post>> GetByStoryID(int storyId)
        {
            throw new NotImplementedException();
        }

        private static bool Validate(Post post)
        {
            var contentLen = post.Content.Length;

            if (contentLen < 2 || contentLen > 2048)
            {
                return false;
            }
            return true;
        }

        
    }
}
