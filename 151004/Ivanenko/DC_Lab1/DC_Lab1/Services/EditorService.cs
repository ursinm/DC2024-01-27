using DC_Lab1.DTO;
using DC_Lab1.Services.Interfaces;
using DC_Lab1.Models;
using AutoMapper;
using Microsoft.EntityFrameworkCore;
using DC_Lab1.DTO.Interface;
using Microsoft.Data.Sqlite;

namespace DC_Lab1.Services
{
    public class EditorService(IMapper _mapper, LabDbContext dbContext) : IEditorService
    {
        public async Task<IResponseTo> CreateEnt(IRequestTo Dto)
        {
            var EditorDto = (EditorRequestTo)Dto;

            if (!Validate(EditorDto))
            {
                throw new InvalidDataException("Incorrect data for CREATE editor");

            }
            var Editor = _mapper.Map<Editor>(EditorDto);
            dbContext.Add(Editor);
            await dbContext.SaveChangesAsync();
            var response = _mapper.Map<EditorResponseTo>(Editor);
            return response;




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
                throw new Exception("Deleting editor exception");
            }

        }

        public async Task<IResponseTo> GetEntById(int id)
        {

            var editor = await dbContext.Editors.FindAsync(id);
            return editor is not null ? _mapper.Map<EditorResponseTo>(editor) : throw new ArgumentNullException($"Not found editor: {id}");


        }

        public IEnumerable<IResponseTo> GetAllEnt()
        {
            try
            {
                return dbContext.Editors.Select(_mapper.Map<EditorResponseTo>);

            }
            catch
            {
                throw new Exception("Getting all editors exception");
            }
        }

        public async Task<IResponseTo> UpdateEnt(IRequestTo Dto)
        {
            var EditorDto = (EditorRequestTo)Dto;

            if (!Validate(EditorDto))
            {
                throw new InvalidDataException("Incorrect data for UPDATE");

            }
            var newEditor = _mapper.Map<Editor>(EditorDto);
            dbContext.Editors.Update(newEditor);
            await dbContext.SaveChangesAsync();
            var Editor = _mapper.Map<EditorResponseTo>(await dbContext.Editors.FindAsync(newEditor.Id));
            return Editor;

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
