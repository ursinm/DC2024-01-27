using AutoMapper;
using Microsoft.AspNetCore.Http.HttpResults;
using Microsoft.AspNetCore.JsonPatch;
using Microsoft.EntityFrameworkCore;
using WebApplicationDC1.Entity.DataModel;
using WebApplicationDC1.Entity.DTO.Requests;
using WebApplicationDC1.Entity.DTO.Responses;
using WebApplicationDC1.Repositories;
using WebApplicationDC1.Services.Interfaces;
using static System.Net.Mime.MediaTypeNames;

namespace WebApplicationDC1.Services.Implementations
{
    public class StoryService(ApplicationContext applicationContext, IMapper mapper) : IStoryService
    {
        private readonly ApplicationContext _context = applicationContext;
        private readonly IMapper _mapper = mapper;

        public async Task<StoryResponseTO> Add(StoryRequestTO story)
        {
            var creatorId = story.CreatorId;
            var creator = await _context.Creators.FindAsync(creatorId) ?? throw new ArgumentNullException($"Creator not found {creatorId}");

            var newStory = new Story
            {
                Title = story.Title,
                Content = story.Content,
                Created = DateTime.Now,
                Modified = DateTime.Now,
                Creator = creator
            };

            if (!Validate(newStory))
            {
                throw new InvalidDataException("Story is not valid");
            }

            _context.Stories.Add(newStory);
            await _context.SaveChangesAsync();

            return _mapper.Map<StoryResponseTO>(newStory);
        }

        //public async Task<StoryResponseTO> Add(StoryRequestTO Story)
        //{
        //    var t = _mapper.Map<Story>(Story);
        //    var Creator = await _context.Creators.FindAsync(t.Creator.Id) ?? throw new ArgumentNullException($"Creator not found {t.Creator.Id}");

        //    if (!Validate(t))
        //    {
        //        throw new InvalidDataException("Story is not valid");
        //    }

        //    t.Creator = Creator;
        //    _context.Stories.Add(t);
        //    await _context.SaveChangesAsync();

        //    return _mapper.Map<StoryResponseTO>(t);
        //}

        public IList<StoryResponseTO> GetAll()
        {
            return _context.Stories.Include(t => t.Creator).Select(_mapper.Map<StoryResponseTO>).ToList();
        }

        public async Task<StoryResponseTO> Patch(int id, JsonPatchDocument<Story> patch)
        {
            var target = await _context.Stories.Include(t => t.Creator).FirstAsync(t => t.Id == id)
                ?? throw new ArgumentNullException($"Story {id} not found at PATCH {patch}");

            patch.ApplyTo(target);
            await _context.SaveChangesAsync();

            return _mapper.Map<StoryResponseTO>(target);
        }

        public async Task<bool> Remove(int id)
        {
            var target = new Story() { Id = id };

            _context.Remove(target);
            await _context.SaveChangesAsync();

            return true;
        }

        //public async Task<StoryResponseTO> Update(StoryRequestTO Story)
        //{
        //    var existingStory = await _context.Stories.FindAsync(Story.Id);
        //    if (existingStory == null)
        //    {
        //        throw new InvalidOperationException($"Story with id {Story.Id} not found");
        //    }
        //    _mapper.Map(Story, existingStory);

        //    if (!Validate(existingStory))
        //    {
        //        throw new InvalidDataException($"UPDATE invalid data: {Story}");
        //    }

        //    _context.Entry(existingStory).State = EntityState.Modified;
        //    await _context.SaveChangesAsync();

        //    return _mapper.Map<StoryResponseTO>(existingStory);
        //}

        public async Task<StoryResponseTO> Update(StoryRequestTO Story)
        {
            var existingStory = await _context.Stories.Include(s => s.Creator).FirstOrDefaultAsync(s => s.Id == Story.Id);
            if (existingStory == null)
            {
                throw new InvalidOperationException($"Story with id {Story.Id} not found");
            }

            existingStory.Title = Story.Title;
            existingStory.Content = Story.Content;
            existingStory.Modified = DateTime.Now;

            var creatorId = Story.CreatorId;
            var creator = await _context.Creators.FindAsync(creatorId) ?? throw new ArgumentNullException($"Creator not found {creatorId}");
            existingStory.Creator = creator;

            if (!Validate(existingStory))
            {
                throw new InvalidDataException($"UPDATE invalid data: {Story}");
            }

            _context.Entry(existingStory).State = EntityState.Modified;
            await _context.SaveChangesAsync();

            return _mapper.Map<StoryResponseTO>(existingStory);
        }


        //public async Task<StoryResponseTO> Update(StoryRequestTO Story)
        //{
        //    var t = _mapper.Map<Story>(Story);

        //    if (!Validate(t))
        //    {
        //        throw new InvalidDataException($"UPDATE invalid data: {Story}");
        //    }

        //    _context.Update(t);
        //    await _context.SaveChangesAsync();

        //    return _mapper.Map<StoryResponseTO>(t);
        //}

        public Task<StoryResponseTO> GetStoryByParam(IList<string> markerNames, IList<int> markerIds, string CreatorLogin, string title, string content)
        {
            throw new NotImplementedException();
        }

        public async Task<StoryResponseTO> GetByID(int id)
        {

            var a = await _context.Stories.Include(t => t.Creator).FirstAsync(t => t.Id == id);

            return a is not null ? _mapper.Map<StoryResponseTO>(a)
                : throw new ArgumentNullException($"Not found Story {id}");
        }

        private static bool Validate(Story Story)
        {
            Console.WriteLine("Story ID: " + Story.Id);
            Console.WriteLine("Story Content: " + Story.Content);
            Console.WriteLine("Story Content: " + Story.Title);

            var titleLen = Story.Title.Length;
            var contentLen = Story.Content.Length;

            if (titleLen < 2 || titleLen > 64)
            {
                return false;
            }
            if (contentLen < 4 || contentLen > 2048)
            {
                return false;
            }
            if (Story.Modified < Story.Created)
            {
                return false;
            }
            return true;
        }
    }
}
