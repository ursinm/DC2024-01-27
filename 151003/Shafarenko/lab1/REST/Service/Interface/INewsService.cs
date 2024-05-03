using REST.Entity.Db;
using REST.Entity.DTO.RequestTO;
using REST.Entity.DTO.ResponseTO;
using REST.Service.Interface.Common;

namespace REST.Service.Interface
{
    public interface INewsService : ICrudService<News, NewsRequestTO, NewsResponseTO>
    {
        Task<NewsResponseTO> GetNewsByParam(IList<string> markerNames, IList<int> markerIds, string authorLogin,
            string title, string content);
    }
}
