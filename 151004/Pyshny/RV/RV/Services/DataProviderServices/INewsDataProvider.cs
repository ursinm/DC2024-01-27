using RV.Views;
using RV.Views.DTO;

namespace RV.Services.DataProviderServices
{
    public interface INewsDataProvider
    {
        NewsDTO CreateNews(NewsAddDTO item);
        List<NewsDTO> GetNews();
        NewsDTO GetNew(int id);
        NewsDTO UpdateNews(NewsUpdateDTO item);
        int DeleteNews(int id);
    }
}
