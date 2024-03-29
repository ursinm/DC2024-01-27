using Lab1.DTO.Interface;
using Lab1.DTO;
using Microsoft.EntityFrameworkCore;
using Lab1.Services.Interfaces;
using AutoMapper;
using Lab1.Models;
using Microsoft.AspNetCore.Components.Forms;
using Lab1.DB.BaseDBContext;

namespace Lab1.Services
{
    public class NotesService(IMapper _mapper, BaseContext dbContext) : INoteService
    {
        public async Task<IResponseTo> CreateEnt(IRequestTo Dto)
        {
            var NoteDto = (NoteRequestTo)Dto;

            if (!Validate(NoteDto))
            {
                throw new InvalidDataException("Incorrect data for CREATE Note");

            }
            var Note = _mapper.Map<Note>(NoteDto);
            dbContext.Notes.Add(Note);
            await dbContext.SaveChangesAsync();
            var response = _mapper.Map<NoteResponseTo>(Note);
            return response;





        }

        public async Task DeleteEnt(int id)
        {
            try
            {
                var Note = await dbContext.Notes.FindAsync(id);
                dbContext.Notes.Remove(Note!);
                await dbContext.SaveChangesAsync();
                return;
            }
            catch
            {
                throw new Exception("Deletting Note exception");
            }

        }

        public async Task<IResponseTo> GetEntById(int id)
        {

            var Note = _mapper.Map<NoteResponseTo>(await dbContext.Notes.FindAsync(id));
            return Note is not null ? _mapper.Map<NoteResponseTo>(Note) : throw new ArgumentNullException($"Not found Note: {id}");



        }

        public IEnumerable<IResponseTo> GetAllEnt()
        {
            try
            {
                return dbContext.Notes.Select(_mapper.Map<NoteResponseTo>);

            }
            catch
            {
                throw new Exception("Getting all Notes exception");
            }
        }

        public async Task<IResponseTo> UpdateEnt(IRequestTo Dto)
        {
            var NoteDto = (NoteRequestTo)Dto;

            if (!Validate(NoteDto))

            {
                throw new InvalidDataException("Incorrect data for UPDATE Note");

            }
            var newNote = _mapper.Map<Note>(NoteDto);
            dbContext.Notes.Update(newNote);
            await dbContext.SaveChangesAsync();
            var Note = _mapper.Map<NoteResponseTo>(await dbContext.Notes.FindAsync(newNote.Id));
            return Note;


        }

        public bool Validate(NoteRequestTo NoteDto)
        {
            if (NoteDto?.Content?.Length < 2 || NoteDto?.Content?.Length > 2048)
                return false;

            return true;
        }
    }
}
