using DC_Lab1.DTO;
using DC_Lab1.Services.Interfaces;
using DC_Lab1.Models;
using AutoMapper;
using Microsoft.EntityFrameworkCore;
using DC_Lab1.DTO.Interface;

namespace DC_Lab1.Services
{
    public class EditorService(IMapper _mapper, LabDbContext dbContext) : IEditorService
    {
        public async Task<IResponseTo> CreateEnt(IRequestTo Dto)
        {
            var EditorDto = (EditorRequestTo)Dto;
            try
            {
                if (Validate(EditorDto))
                {
                    var Editor = _mapper.Map<Editor>(EditorDto);
                    dbContext.Editors.Add(Editor);
                    await dbContext.SaveChangesAsync();
                    var response = _mapper.Map<EditorResponseTo>(Editor);
                    return response;

                }
                else
                {
                    throw new ArgumentException();
                }
            }
            catch
            {
                throw new ArgumentException();
            }
            
            
        }

        public async Task DeleteEnt(int id)
        {
            try
            {
                var Editor = await dbContext.Editors.FindAsync(id);
                dbContext.Editors.Remove(Editor!);
                await dbContext.SaveChangesAsync();
                return;
            }
            catch
            {
                throw new ArgumentException();
            }
           
        }

        public async Task<IResponseTo> GetEntById(int id)
        {
            try
            {
                return _mapper.Map<EditorResponseTo>(await dbContext.Editors.FindAsync(id));

            }catch
            {
                throw new ArgumentException();
            }
        }

        public IEnumerable<IResponseTo> GetAllEnt()
        {
            try
            {
                return dbContext.Editors.Select(_mapper.Map<EditorResponseTo>);

            }catch
            {
                throw new ArgumentException();
            }
        }

        public async Task<IResponseTo> UpdateEnt(IRequestTo Dto)
        {
            var EditorDto = (EditorRequestTo)Dto;
            try
            {
                if (Validate(EditorDto))
                {
                    var newEditor = _mapper.Map<Editor>(EditorDto);
                    dbContext.Editors.Update(newEditor);
                    await dbContext.SaveChangesAsync();
                    var Editor = _mapper.Map<EditorResponseTo>(await dbContext.Editors.FindAsync(newEditor.Id));
                    return Editor;
                }else
                {
                    throw new ArgumentException();

                }
            }
            catch 
            {

                throw new ArgumentException();
            }
            
        }

        public bool Validate(EditorRequestTo EditorDto)
        {
            if (EditorDto?.Login?.Length < 2 || EditorDto?.Login?.Length > 64)
                return false;
            if (EditorDto?.Lastname?.Length < 2 && EditorDto?.Lastname?.Length > 64)
                return false;
            if (EditorDto?.Firstname?.Length < 2 && EditorDto?.Firstname?.Length > 64)
                return false;
            if (EditorDto?.Password?.Length < 8 && EditorDto?.Password?.Length > 128)
                return false;
            return true;
        }

    }
}
