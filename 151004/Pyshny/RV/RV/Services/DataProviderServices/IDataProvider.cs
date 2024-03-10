using RV.Views;
using RV.Views.DTO;

namespace RV.Services.DataProviderServices
{
    public interface IDataProvider : IUserDataProvider, INewsDataProvider, INoteDataProvider, IStickerDataProvider
    {
        public IUserDataProvider userDataProvider { get; }
        public INewsDataProvider newsDataProvider { get; }
        public INoteDataProvider noteDataProvider { get; }
        public IStickerDataProvider stickerDataProvider { get; }

        UserDTO IUserDataProvider.CreateUser(UserAddDTO item)
        {
            return userDataProvider.CreateUser(item);
        }

        List<UserDTO> IUserDataProvider.GetUsers()
        {
            return userDataProvider.GetUsers();
        }

        UserDTO IUserDataProvider.GetUser(int id)
        {
            return userDataProvider.GetUser(id);
        }

        UserDTO IUserDataProvider.UpdateUser(UserUpdateDTO item)
        {
            return userDataProvider.UpdateUser(item);
        }

        int IUserDataProvider.DeleteUser(int id)
        {
            return userDataProvider.DeleteUser(id);
        }

        NewsDTO INewsDataProvider.CreateNews(NewsAddDTO item)
        {
            return newsDataProvider.CreateNews(item);
        }

        List<NewsDTO> INewsDataProvider.GetNews()
        {
            return newsDataProvider.GetNews();
        }

        NewsDTO INewsDataProvider.GetNew(int id)
        {
            return newsDataProvider.GetNew(id);
        }

        NewsDTO INewsDataProvider.UpdateNews(NewsUpdateDTO item)
        {
            return newsDataProvider.UpdateNews(item);
        }

        int INewsDataProvider.DeleteNews(int id)
        {
            return newsDataProvider.DeleteNews(id);
        }

        NoteDTO INoteDataProvider.CreateNote(NoteAddDTO item)
        {
            return noteDataProvider.CreateNote(item);
        }

        List<NoteDTO> INoteDataProvider.GetNotes()
        {
            return noteDataProvider.GetNotes();
        }

        NoteDTO INoteDataProvider.GetNote(int id)
        {
            return noteDataProvider.GetNote(id);
        }

        NoteDTO INoteDataProvider.UpdateNote(NoteUpdateDTO item)
        {
            return noteDataProvider.UpdateNote(item);
        }

        int INoteDataProvider.DeleteNote(int id)
        {
            return noteDataProvider.DeleteNote(id);
        }

        StickerDTO IStickerDataProvider.CreateSticker(StickerAddDTO item)
        {
            return stickerDataProvider.CreateSticker(item);
        }

        List<StickerDTO> IStickerDataProvider.GetStickers()
        {
            return stickerDataProvider.GetStickers();
        }

        StickerDTO IStickerDataProvider.GetSticker(int id)
        {
            return stickerDataProvider.GetSticker(id);
        }

        StickerDTO IStickerDataProvider.UpdateSticker(StickerUpdateDTO item)
        {
            return stickerDataProvider.UpdateSticker(item);
        }

        int IStickerDataProvider.DeleteSticker(int id)
        {
            return stickerDataProvider.DeleteSticker(id);
        }
    }
}
