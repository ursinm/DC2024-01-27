using AutoMapper;
using lab2.DTO;
using lab2.DTO.Interface;
using lab2.Models;
using lab2.Services.Interface;
using lab2.DB.BaseDBContext;

namespace lab2.Services
{
    public class LabelService(IMapper _mapper, BaseDbContext dbContext) : ILabelService
    {
        public async Task<IResponseTo> CreateEntity(IRequestTo RequestDTO)
        {
            var LabelDTO = (LabelRequestTo)RequestDTO;

            if (!Validate(LabelDTO))
            {
                throw new InvalidDataException("Incorrect data for CREATE label");
            }

            var Label = _mapper.Map<Label>(LabelDTO);
            dbContext.Add(Label);
            await dbContext.SaveChangesAsync();
            var response = _mapper.Map<LabelResponseTo>(Label);
            return response;
        }

        public async Task DeleteEntity(int id)
        {
            try
            {
                var Label = await dbContext.Labels.FindAsync(id);
                dbContext.Labels.Remove(Label!);
                await dbContext.SaveChangesAsync();
                return;
            }
            catch
            {
                throw new Exception("Deleting label exception");
            }
        }

        public IEnumerable<IResponseTo> GetAllEntity()
        {
            try
            {
                return dbContext.Labels.Select(_mapper.Map<LabelResponseTo>);
            }
            catch
            {
                throw new Exception("Getting all label exception");
            }
        }

        public async Task<IResponseTo> GetEntityById(int id)
        {
            var Label = await dbContext.Labels.FindAsync(id);
            return (Label is not null ? _mapper.Map<LabelResponseTo>(Label) : throw new ArgumentNullException($"Not found label: {id}"));
        }

        public async Task<IResponseTo> UpdateEntity(IRequestTo RequestDTO)
        {
            var LabelDTO = (LabelRequestTo)RequestDTO;

            if (!Validate(LabelDTO))
            {
                throw new InvalidDataException("Incorrect data for UPDATE label");

            }
            var newLabel = _mapper.Map<Label>(LabelDTO);
            dbContext.Labels.Update(newLabel);
            await dbContext.SaveChangesAsync();
            var Label = _mapper.Map<LabelResponseTo>(await dbContext.Labels.FindAsync(newLabel.Id));
            return Label;
        }

        private bool Validate(LabelRequestTo LabelDTO)
        {
            if (LabelDTO.Name?.Length < 2 || LabelDTO.Name?.Length > 32)
                return false;
            return true;
        }
    }
}
