using AutoMapper;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Caching.Distributed;
using Newtonsoft.Json;
using Publisher.Entity.Db;
using Publisher.Entity.DTO.RequestTO;
using Publisher.Entity.DTO.ResponseTO;
using Publisher.Repository.Interface;
using Publisher.Service.Implementation.Common;
using Publisher.Service.Interface;

namespace Publisher.Service.Implementation
{
    public class storyService(IMapper mapper, IstoryRepository repository, IDistributedCache cache)
        : AbstractCrudService<story, storyRequestTO, storyResponseTO>(mapper, repository), IstoryService
    {
       

        public override async Task<bool> Remove(int id)
        {
            await cache.RemoveAsync(GetRedisId(id));

            return await base.Remove(id);
        }

        public override async Task<storyResponseTO> GetByID(int id)
        {
            var cacheResponse = await cache.GetStringAsync(GetRedisId(id));

            if (cacheResponse is null)
            {
                var response = await base.GetByID(id);
                await cache.SetStringAsync(GetRedisId(id), JsonConvert.SerializeObject(response));

                return response;
            }

            _ = Task.Run(async () =>
            {
                var res = await base.GetByID(id);
                await cache.SetStringAsync(GetRedisId(id), JsonConvert.SerializeObject(res));
            });

            return JsonConvert.DeserializeObject<storyResponseTO>(cacheResponse)
                ?? throw new Exception("Unable to deserialize story");
        }

        public override async Task<storyResponseTO> Add(storyRequestTO storyTo)
        {

            if (!Validate(storyTo))
            {
                throw new InvalidDataException("story is not valid");
            }

            var res = await base.Add(storyTo);
            await cache.SetStringAsync(GetRedisId(res.Id), JsonConvert.SerializeObject(res));

            return res;
        }

        public override async Task<storyResponseTO> Update(storyRequestTO storyTo)
        {
            if (!Validate(storyTo))
            {
                throw new InvalidDataException($"UPDATE invalid data: {storyTo}");
            }

            var res = await base.Update(storyTo);
            await cache.RemoveAsync(GetRedisId(res.Id));
            await cache.SetStringAsync(GetRedisId(res.Id), JsonConvert.SerializeObject(res));

            return res;
        }

        private static string GetRedisId(int id) => $"story:{id}";

        public Task<storyResponseTO> GetstoryByParam(IList<string> stickerNames, IList<int> stickerIds, string creatorLogin,
            string title, string content)
        {
            throw new NotImplementedException();
        }

        private static bool Validate(storyRequestTO story)
        {
            var titleLen = story.Title.Length;
            var contentLen = story.Content.Length;

            if (titleLen < 2 || titleLen > 64)
            {
                return false;
            }
            if (contentLen < 4 || contentLen > 2048)
            {
                return false;
            }
            if (story.Modified < story.Created)
            {
                return false;
            }
            return true;
        }
    }
}
