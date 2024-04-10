using lab2.DTO;
using lab2.DTO.Interface;
using lab2.Services.Interface;
using Microsoft.AspNetCore.Mvc;

namespace lab2.Controllers
{
    [ApiController]
    [Route("/api/v1.0/users")]
    public class UserController(IUserService UserService) : Controller
    {
        [HttpGet]
        public JsonResult GetUsers()
        {
            try
            {
                var Users = UserService.GetAllEntity();
                return Json(Users);
            }
            catch
            {
                Response.StatusCode = 400;
                return Json(BadRequest());
            }
        }

        [HttpGet]
        [Route("{UserId:int}")]
        public async Task<JsonResult> GetUserById(int UserId)
        {
            try
            {
                var User = await UserService.GetEntityById(UserId);
                return Json(User);
            }
            catch
            {
                Response.StatusCode = 400;
                return Json(BadRequest());
            }
        }

        [HttpPost]
        public async Task<JsonResult> CreateUser(UserRequestTo UserTo)
        {
            try
            {
                Response.StatusCode = 201;
                var User = await UserService.CreateEntity(UserTo);
                return Json(User);
            }
            catch
            {
                Response.StatusCode = 403;
                return Json(BadRequest());
            }

        }

        [HttpPut]
        public async Task<JsonResult> UpdateUser(UserRequestTo UserTo)
        {
            IResponseTo newUser;
            try
            {
                newUser = await UserService.UpdateEntity(UserTo);
                Response.StatusCode = 200;
                return Json(newUser);
            }
            catch
            {
                Response.StatusCode = 400;
                return Json(BadRequest());
            }
        }

        [HttpDelete("{UserId}")]
        public async Task<IActionResult> DeleteUser(int UserId)
        {
            try
            {
                Response.StatusCode = 204;
                await UserService.DeleteEntity(UserId);
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
