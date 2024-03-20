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
	public class PostService(IPostRepository postRepository, 
		IMapper mapper, PostRequestDtoValidator validator) : IPostService
	{
		private readonly IPostRepository _postRepository = postRepository;
		private readonly IMapper _mapper = mapper;
		private readonly PostRequestDtoValidator _validator = validator;

		public async Task<IEnumerable<PostResponseDto>> GetPostsAsync()
		{
			var posts = await _postRepository.GetAllAsync();
			return _mapper.Map<IEnumerable<PostResponseDto>>(posts);
		}

		public async Task<PostResponseDto> GetPostByIdAsync(long id)
		{
			var post = await _postRepository.GetByIdAsync(id)
				?? throw new NotFoundException(ErrorMessages.PostNotFoundMessage(id));
			return _mapper.Map<PostResponseDto>(post);
		}

		public async Task<PostResponseDto> CreatePostAsync(PostRequestDto post)
		{
			_validator.ValidateAndThrow(post);
			var postToCreate = _mapper.Map<Post>(post);
			var createdPost = await _postRepository.CreateAsync(postToCreate);
			return _mapper.Map<PostResponseDto>(createdPost);
		}

		public async Task<PostResponseDto> UpdatePostAsync(PostRequestDto post)
		{
			_validator.ValidateAndThrow(post);
			var postToUpdate = _mapper.Map<Post>(post);
			var updatedPost = await _postRepository.UpdateAsync(postToUpdate)
				?? throw new NotFoundException(ErrorMessages.PostNotFoundMessage(post.Id));
			return _mapper.Map<PostResponseDto>(updatedPost);
		}

		public async Task DeletePostAsync(long id)
		{
			if (!await _postRepository.DeleteAsync(id))
			{
				throw new NotFoundException(ErrorMessages.PostNotFoundMessage(id));
			}
		}
		
	}
}
