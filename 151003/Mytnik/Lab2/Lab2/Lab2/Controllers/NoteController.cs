using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using Lab2;
using Lab2.Models;
using Lab2.Services.Interfaces;
using Lab2.DTO.Interface;
using Lab2.DTO;
using Lab2.Services;
using Microsoft.AspNetCore.Components.Forms;

namespace Lab2.Controllers
{
    [ApiController]
    [Route("/api/v1.0/notes")]
    public class NoteController(INoteService NoteService) : Controller
    {
        [HttpGet]
        public JsonResult GetNotes()
        {
            try
            {
                var Notes = NoteService.GetAllEnt();
                return Json(Notes);
            }
            catch
            {
                return Json(BadRequest());

            }

        }

        [HttpGet]
        [Route("{NoteId:int}")]
        public async Task<JsonResult> GetNoteById(int NoteId)
        {
            try
            {
                var Note = await NoteService.GetEntById(NoteId);
                return Json(Note);
            }
            catch
            {
                return Json(BadRequest());
            }


        }

        [HttpPost]
        public async Task<JsonResult> CreateNote(NoteRequestTo NoteTo)
        {
            try
            {
                Response.StatusCode = 201;
                var Note = await NoteService.CreateEnt(NoteTo);
                return Json(Note);
            }
            catch
            {
                return Json(BadRequest());
            }

        }

        [HttpPut]
        public async Task<JsonResult> UpdateNote(NoteRequestTo NoteTo)
        {
            IResponseTo newNote;
            try
            {
                newNote = await NoteService.UpdateEnt(NoteTo);
                Response.StatusCode = 200;
                return Json(newNote);

            }
            catch
            {
                Response.StatusCode = 400;
                return Json(BadRequest());
            }
        }

        [HttpDelete("{NoteId}")]
        public async Task<IActionResult> DeleteNote(int NoteId)
        {
            try
            {
                Response.StatusCode = 204;
                await NoteService.DeleteEnt(NoteId);
            }
            catch
            {
                Response.StatusCode = 401;
                return BadRequest();
            }

            return NoContent();



        }
    }
}
