using AutoMapper;
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
    public class stickerService(IMapper mapper, IstickerRepository repository, IDistributedCache cache)
        : AbstractCrudService<sticker, stickerRequestTO, stickerResponseTO>(mapper, repository), IstickerService
    {
        public override async Task<bool> Remove(int id)
        {
            await cache.RemoveAsync(GetRedisId(id));

            return await base.Remove(id);
        }

        public override async Task<stickerResponseTO> GetByID(int id)
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

            return JsonConvert.DeserializeObject<stickerResponseTO>(cacheResponse)
                ?? throw new Exception("Unable to deserialize sticker");
        }

        public override async Task<stickerResponseTO> Add(stickerRequestTO stickerTo)
        {
            if (!Validate(stickerTo))
            {
                throw new InvalidDataException("sticker is not valid");
            }

            var res = await base.Add(stickerTo);
            await cache.SetStringAsync(GetRedisId(res.Id), JsonConvert.SerializeObject(res));

            return res;
        }

        public override async Task<stickerResponseTO> Update(stickerRequestTO stickerTo)
        {
            if (!Validate(stickerTo))
            {
                throw new InvalidDataException($"UPDATE invalid data: {stickerTo}");
            }

            var res = await base.Update(stickerTo);
            await cache.RemoveAsync(GetRedisId(res.Id));
            await cache.SetStringAsync(GetRedisId(res.Id), JsonConvert.SerializeObject(res));

            return res;
        }

        private static string GetRedisId(int id) => $"sticker:{id}";

        public Task<IList<stickerResponseTO>> GetBystoryID(int storyId)
        {
            throw new NotImplementedException();
        }

        private static bool Validate(stickerRequestTO sticker)
        {
            var nameLen = sticker.Name.Length;

            if (nameLen < 2 || nameLen > 32)
            {
                return false;
            }
            return true;
        }
    }
}
