using AutoMapper;
using DC.DTO.RequestDTO;
using DC.DTO.ResponseDTO;
using DC.Exceptions;
using DC.Infrastructure.Validators;
using DC.Models;
using DC.Repositories.Interfaces;
using DC.Services.Interfaces;
using FluentValidation;

namespace DC.Services.Impementations
{
	public class IssueService(IIssueRepository issueRepository, 
		IMapper mapper, IssueRequestDtoValidator validator) : IIssueService
	{
		private readonly IIssueRepository _issueRepository = issueRepository;
		private readonly IMapper _mapper = mapper;
		private readonly IssueRequestDtoValidator _validator = validator;

		public async Task<IEnumerable<IssueResponseDto>> GetIssuesAsync()
		{
			var issues = await _issueRepository.GetAllAsync();
			return _mapper.Map<IEnumerable<IssueResponseDto>>(issues);
		}

		public async Task<IssueResponseDto> GetIssueByIdAsync(long id)
		{
			var issue = await _issueRepository.GetByIdAsync(id)
				?? throw new NotFoundException(ErrorMessages.IssueNotFoundMessage(id));
			return _mapper.Map<IssueResponseDto>(issue);
		}

		public async Task<IssueResponseDto> CreateIssueAsync(IssueRequestDto issue)
		{
			_validator.ValidateAndThrow(issue);
			var issueToCreate = _mapper.Map<Issue>(issue);
			var createdIssue = await _issueRepository.CreateAsync(issueToCreate);
			return _mapper.Map<IssueResponseDto>(createdIssue);
		}

		public async Task<IssueResponseDto> UpdateIssueAsync(IssueRequestDto issue)
		{
			_validator.ValidateAndThrow(issue);
			var issueToUpdate = _mapper.Map<Issue>(issue);
			var updatedIssue = await _issueRepository.UpdateAsync(issueToUpdate)
				?? throw new NotFoundException(ErrorMessages.IssueNotFoundMessage(issue.Id));
			return _mapper.Map<IssueResponseDto>(updatedIssue);
		}

		public async Task DeleteIssueAsync(long id)
		{
			if (!await _issueRepository.DeleteAsync(id))
			{
				throw new NotFoundException(ErrorMessages.IssueNotFoundMessage(id));
			}
		}
	}
}
