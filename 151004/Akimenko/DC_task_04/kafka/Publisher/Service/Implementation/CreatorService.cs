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
    public class creatorService(IMapper mapper, IcreatorRepository repository, IDistributedCache cache) :
        AbstractCrudService<creator, creatorRequestTO, creatorResponseTO>(mapper, repository), IcreatorService
    {
        private readonly IMapper _mapper = mapper;

        public override async Task<bool> Remove(int id)
        {
            await cache.RemoveAsync(GetRedisId(id));

            return await base.Remove(id);
        }

        public override async Task<creatorResponseTO> GetByID(int id)
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

            return JsonConvert.DeserializeObject<creatorResponseTO>(cacheResponse)
                ?? throw new Exception("Unable to deserialize creator");
        }

        public override async Task<creatorResponseTO> Add(creatorRequestTO creatorTo)
        {
            if (!Validate(creatorTo))
            {
                throw new InvalidDataException("creator is not valid");
            }

            var res = await base.Add(creatorTo);
            await cache.SetStringAsync(GetRedisId(res.Id), JsonConvert.SerializeObject(res));

            return res;
        }

        public override async Task<creatorResponseTO> Update(creatorRequestTO creatorTo)
        {
            if (!Validate(creatorTo))
            {
                throw new InvalidDataException($"UPDATE invalid data: {creatorTo}");
            }

            var res = await base.Update(creatorTo);
            await cache.RemoveAsync(GetRedisId(res.Id));
            await cache.SetStringAsync(GetRedisId(res.Id), JsonConvert.SerializeObject(res));

            return res;
        }

        private static string GetRedisId(int id) => $"creator:{id}";

        public async Task<creatorResponseTO> GetBystoryID(int storyId)
        {
            var response = await repository.GetBystoryIdAsync(storyId);

            return _mapper.Map<creatorResponseTO>(response.creator);
        }

        private static bool Validate(creatorRequestTO creator)
        {
            var fnameLen = creator.FirstName.Length;
            var lnameLen = creator.LastName.Length;
            var passLen = creator.Password.Length;
            var loginLen = creator.Login.Length;

            if (fnameLen < 2 || fnameLen > 64)
            {
                return false;
            }
            if (lnameLen < 2 || fnameLen > 64)
            {
                return false;
            }
            if (passLen < 8 || passLen > 128)
            {
                return false;
            }
            if (loginLen < 2 || loginLen > 64)
            {
                return false;
            }
            return true;
        }
    }
}
