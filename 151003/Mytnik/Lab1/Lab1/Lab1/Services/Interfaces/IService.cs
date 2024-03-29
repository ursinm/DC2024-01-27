using Lab1.DTO;
using Lab1.DTO.Interface;

namespace Lab1.Services.Interfaces
{
    public interface IService
    {
        public IEnumerable<IResponseTo> GetAllEnt();
        public Task<IResponseTo> GetEntById(int id);
        public Task<IResponseTo> CreateEnt(IRequestTo RequestDto);

        public Task<IResponseTo> UpdateEnt(IRequestTo RequestDto);
        public Task DeleteEnt(int id);
    }
}
