using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using DC_Lab1;
using DC_Lab1.Models;
using Microsoft.AspNetCore.Http.HttpResults;
using DC_Lab1.Services.Interfaces;
using DC_Lab1.DTO;
using DC_Lab1.DTO.Interface;

namespace DC_Lab1.Controllers
{
    [ApiController]
    [Route("/api/v1.0/editors")]
    public class EditorController(IEditorService EditorService) : Controller
    {
        [HttpGet]
        public JsonResult GetEditors()
        {
            try
            {
                var Editors = EditorService.GetAllEnt();
                return Json(Editors);
            }
            catch 
            {
                Response.StatusCode = 400;
                return Json(BadRequest());

            }

        }

        [HttpGet]
        [Route("{EditorId:int}")]
        public async Task<JsonResult> GetEditorById( int EditorId) 
        {
            try
            {
                var Editor = await EditorService.GetEntById(EditorId);
                return Json(Editor);
            }
            catch 
            {
                Response.StatusCode = 400;
                return Json(BadRequest());
            }
            

        }

        [HttpPost]
        public async Task<JsonResult> CreateEditor(EditorRequestTo EditorTo)
        {
            try
            {
                Response.StatusCode = 201;
                var Editor = await EditorService.CreateEnt(EditorTo);
                return Json(Editor);
            }
            catch
            {
                Response.StatusCode = 403;
                return Json(BadRequest());
            }
           
        }

        [HttpPut]
        public async Task<JsonResult> UpdateEditor(EditorRequestTo EditorTo)
        {
            IResponseTo newEditor;
            try
            {
                newEditor = await EditorService.UpdateEnt(EditorTo);
                Response.StatusCode = 200;
                return Json(newEditor);

            }
            catch
            {
                Response.StatusCode = 400;
                return Json(BadRequest());
            }
        }

        [HttpDelete("{EditorId}")]
        public  async Task<IActionResult> DeleteEditor(int EditorId)
        {
            try
            {
                Response.StatusCode = 204;
                await EditorService.DeleteEnt(EditorId);
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
