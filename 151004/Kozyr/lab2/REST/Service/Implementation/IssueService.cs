using AutoMapper;
using Microsoft.AspNetCore.JsonPatch;
using Microsoft.EntityFrameworkCore;
using REST.Entity.Db;
using REST.Entity.DTO.RequestTO;
using REST.Entity.DTO.ResponseTO;
using REST.Service.Interface;
using REST.Storage.Common;

namespace REST.Service.Implementation
{
    public class IssueService(DbStorage dbStorage, IMapper mapper) : IIssueService
    {
        private readonly DbStorage _context = dbStorage;
        private readonly IMapper _mapper = mapper;

        public async Task<IssueResponseTO> Add(IssueRequestTO issue)
        {
            var i = _mapper.Map<Issue>(issue);
            var creator = await _context.Creators.FindAsync(i.Creator.Id) ?? throw new ArgumentNullException($"CREATOR not found {i.Creator.Id}");

            if (!Validate(i))
            {
                throw new InvalidDataException("issue is not valid");
            }

            i.Creator = creator;
            _context.Issues.Add(i);
            await _context.SaveChangesAsync();

            return _mapper.Map<IssueResponseTO>(i);
        }

        public IList<IssueResponseTO> GetAll()
        {
            return _context.Issues.Include(i => i.Creator).Select(_mapper.Map<IssueResponseTO>).ToList();
        }

        public async Task<IssueResponseTO> Patch(int id, JsonPatchDocument<Issue> patch)
        {
            var target = await _context.Issues.Include(i => i.Creator).FirstAsync(i => i.Id == id)
                ?? throw new ArgumentNullException($"issue {id} not found at PATCH {patch}");

            patch.ApplyTo(target);
            await _context.SaveChangesAsync();

            return _mapper.Map<IssueResponseTO>(target);
        }

        public async Task<bool> Remove(int id)
        {
            var target = new Issue() { Id = id };

            _context.Remove(target);
            await _context.SaveChangesAsync();

            return true;
        }

        public async Task<IssueResponseTO> Update(IssueRequestTO issue)
        {
            var i = _mapper.Map<Issue>(issue);

            if (!Validate(i))
            {
                throw new InvalidDataException($"UPDATE invalid data: {issue}");
            }

            _context.Update(i);
            await _context.SaveChangesAsync();

            return _mapper.Map<IssueResponseTO>(i);
        }

        public async Task<IssueResponseTO> GetByID(int id)
        {

            var i = await _context.Issues.Include(i => i.Creator).FirstAsync(i => i.Id == id);

            return i is not null ? _mapper.Map<IssueResponseTO>(i)
                : throw new ArgumentNullException($"Not found ISUUE {id}");
        }

        private static bool Validate(Issue issue)
        {
            var titleLen = issue.Title.Length;
            var contentLen = issue.Content.Length;

            if (titleLen < 2 || titleLen > 64)
            {
                return false;
            }
            if (contentLen < 4 || contentLen > 2048)
            {
                return false;
            }
            if (issue.Modified < issue.Created)
            {
                return false;
            }
            return true;
        }
    }
}
