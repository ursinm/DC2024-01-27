using AutoMapper;
using Microsoft.AspNetCore.JsonPatch;
using REST.Entity.Db;
using REST.Entity.DTO.RequestTO;
using REST.Entity.DTO.ResponseTO;
using REST.Service.Interface;
using REST.Storage.Common;

namespace REST.Service.Implementation
{
    public class CommentService(DbStorage dbStorage, IMapper mapper) : ICommentService
    {
        private readonly DbStorage _context = dbStorage;
        private readonly IMapper _mapper = mapper;

        public async Task<CommentResponseTO> Add(CommentRequestTO comment)
        {
            var c = _mapper.Map<Comment>(comment);

            if (!Validate(c))
            {
                throw new InvalidDataException("COMMENT is not valid");
            }

            _context.Comments.Add(c);
            await _context.SaveChangesAsync();

            return _mapper.Map<CommentResponseTO>(c);
        }

        public IList<CommentResponseTO> GetAll()
        {
            return _context.Comments.Select(_mapper.Map<CommentResponseTO>).ToList();
        }

        public async Task<CommentResponseTO> Patch(int id, JsonPatchDocument<Comment> patch)
        {
            var target = await _context.FindAsync<Comment>(id)
                ?? throw new ArgumentNullException($"COMMENT {id} not found at PATCH {patch}");

            patch.ApplyTo(target);
            await _context.SaveChangesAsync();

            return _mapper.Map<CommentResponseTO>(target);
        }

        public async Task<bool> Remove(int id)
        {
            var target = new Comment() { Id = id };

            _context.Remove(target);
            await _context.SaveChangesAsync();

            return true;
        }

        public async Task<CommentResponseTO> Update(CommentRequestTO comment)
        {
            var c = _mapper.Map<Comment>(comment);

            if (!Validate(c))
            {
                throw new InvalidDataException($"UPDATE invalid data: {comment}");
            }

            _context.Update(c);
            await _context.SaveChangesAsync();

            return _mapper.Map<CommentResponseTO>(c);
        }

        public async Task<CommentResponseTO> GetByID(int id)
        {
            var c = await _context.Comments.FindAsync(id);

            return c is not null ? _mapper.Map<CommentResponseTO>(c)
                : throw new ArgumentNullException($"Not found COMMENT {id}");
        }

        private static bool Validate(Comment comment)
        {
            var contentLen = comment.Content.Length;

            if (contentLen < 2 || contentLen > 2048)
            {
                return false;
            }
            return true;
        }
    }
}
