using AutoMapper;
using DC_Lab1.DTO.Interface;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Migrations.Operations;

namespace Test
{
    [TestClass]
    public class EditorControllerUnitTest
    {

        private readonly EditorController _controller;
        private readonly EditorService _service;
        private readonly LabDbContext _dbContext;
        private readonly IMapper _mapper = new MapperConfiguration(cfg =>
        {
            cfg.AddMaps(["DC_Lab1"]);
        }).CreateMapper();
        public EditorControllerUnitTest()
        { 
            _dbContext = new LabDbContext();
            _service = new EditorService(_mapper, _dbContext);
            _controller = new EditorController(_service)
            { ControllerContext = new ControllerContext { HttpContext = new DefaultHttpContext() } };


        }

        [TestMethod]
        public void GetUpdate()
        {
            var editorRequest = new EditorRequestTo(0,"testLogin", "password", "fname", "lname");
            var editor = _controller.CreateEditor(editorRequest).Result.Value as EditorResponseTo;
            Assert.IsNotNull(editor);

            var newEditor = new EditorRequestTo(editor.Id,"newTestLogin", "newPassword", "newFname", "newLname");
            _dbContext.ChangeTracker.Clear();
            var editorResponce = _controller.UpdateEditor(newEditor).Result.Value as EditorResponseTo;
            Assert.IsNotNull(editorResponce);
            Assert.AreEqual(editorResponce.Login, newEditor.Login);
        }
        [TestMethod]
        public void GetEntById()
        {
            var editorRequest = new EditorRequestTo(0,"testLogin", "password", "fname", "lname");
            var editor = _controller.CreateEditor(editorRequest).Result.Value as EditorResponseTo;
            Assert.IsNotNull(editor);
            _dbContext.ChangeTracker.Clear();
            var foundedEditor = _controller.GetEditorById(editor.Id).Result.Value as EditorResponseTo;
            Assert.IsNotNull(foundedEditor);
            Assert.AreEqual(foundedEditor.Id, editor.Id);
        }
        [TestMethod]
        public async Task GetAllEnt()
        {
            
            var result = _controller.GetEditors();

            Assert.IsNotNull(result);
            Assert.IsInstanceOfType(result.Value, typeof(IEnumerable<IResponseTo>));
        }
        [TestMethod]
        public async Task CreateEnt()
        {
            var editorRequest = new EditorRequestTo(0,"testLogin", "password", "fname", "lname");
            var editorResponse = (await _controller.CreateEditor(editorRequest)).Value as EditorResponseTo;
            Assert.IsNotNull(editorResponse);

            Assert.AreEqual(editorResponse.Login, "testLogin");
        }
        [TestMethod]
        public async Task DeleteEnt()
        {
            var editorRequest = new EditorRequestTo(0, "testDeleteLogin", "password", "fname", "lname");
            var editorResponse = (await _controller.CreateEditor(editorRequest)).Value as EditorResponseTo;
            Assert.IsNotNull(editorResponse);

            _dbContext.ChangeTracker.Clear();

            var result = await _controller.DeleteEditor(editorResponse.Id);

            var expected = typeof(NoContentResult);

            Assert.IsInstanceOfType(result, expected);
        }

    }
}