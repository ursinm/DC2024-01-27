using Microsoft.AspNetCore.Mvc;
using RV.Services.DataProviderServices;
using RV.Views.DTO;
using Microsoft.EntityFrameworkCore;
using Npgsql;

namespace RV.Controllers
{
    [Route("api/v1.0/notes")]
    [ApiController]
    public class NotesController : ControllerBase
    {
        private readonly IDataProvider _context;

        public NotesController(IDataProvider context)
        {
            _context = context;
        }

        [HttpGet]
        public IActionResult GetNotes()
        {
            return StatusCode(200, _context.GetNotes());
        }

        [HttpGet("{id}")]
        public IActionResult GetNote(int id)
        {
            return StatusCode(200, _context.GetNote(id));
        }

        [HttpPut]
        public IActionResult UpdateNote([FromBody] NoteUpdateDTO note)
        {
            try
            {
                return StatusCode(200, _context.UpdateNote(note));
            }
            catch (DbUpdateException ex)
            {
                string errorCode = "400";
                string hash = ex.Message.GetHashCode().ToString();
                errorCode += hash[0];
                errorCode += hash[1];
                Dictionary<string, string> error = new Dictionary<string, string>() {
                    { "errorMeassage", ex.Message},
                    { "errorCode", errorCode}
                };
                return StatusCode(400, error);
            }
        }

        [HttpPost]
        public IActionResult PostNote([FromBody] NoteAddDTO note)
        {
            try
            { 
                return StatusCode(201, _context.CreateNote(note));
            }
            catch (DbUpdateException ex)
            {
                int statusCode = 400;
                if (ex.InnerException is PostgresException postgresException)
                {
                    if (postgresException.SqlState == "23505")
                    {
                        statusCode = 403;
                    }
                }
                string hash = ex.Message.GetHashCode().ToString();
                string errorCode = statusCode.ToString();
                errorCode += hash[0];
                errorCode += hash[1];
                Dictionary<string, string> error = new Dictionary<string, string>() {
                    { "errorMeassage", ex.Message},
                    { "errorCode", errorCode}
                };
                return StatusCode(statusCode, error);
            }
        }

        [HttpDelete("{id}")]
        public IActionResult DeleteNote(int id)
        {
            int res = _context.DeleteNote(id);
            if (res == 0)
                return StatusCode(400);
            else
                return StatusCode(204);
        }
    }
}
