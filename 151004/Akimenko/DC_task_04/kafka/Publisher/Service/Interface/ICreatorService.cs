using Publisher.Entity.Db;
using Publisher.Entity.DTO.RequestTO;
using Publisher.Entity.DTO.ResponseTO;
using Publisher.Service.Interface.Common;

namespace Publisher.Service.Interface
{
    public interface IcreatorService : ICrudService<creator, creatorRequestTO, creatorResponseTO>
    {
        Task<creatorResponseTO> GetBystoryID(int storyId);
    }
}
