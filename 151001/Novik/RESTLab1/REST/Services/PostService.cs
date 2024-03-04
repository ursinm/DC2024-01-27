using System.Numerics;
using AutoMapper;
using REST.Mapper;
using REST.Models.DTOs.RequestIn;
using REST.Models.DTOs.Responses;
using REST.Models.Entity;
using REST.Repositories;
using REST.Repositories.interfaces;
using REST.Repositories.sample;
using REST.Services.interfaces;

namespace REST.Services;

public class PostService : IPostService
{
    private readonly IPostRepository _postRepository;
    private readonly IMapper _postMapper;

    public PostService(IPostRepository postRepository,IMapper postMapper)
    {
        _postMapper = postMapper;
        _postRepository = postRepository;
    }
    
    public async Task<IEnumerable<PostResponseTo>> GetAllAsync()
    {
        var postEntities = await _postRepository.GetAllAsync();
        return _postMapper.Map<IEnumerable<PostResponseTo>>(postEntities);
    }

    public async Task<PostResponseTo?>? GetByIdAsync(long id)
    {
        var postEntity = await _postRepository.GetByIdAsync(id);
        if (postEntity == null)
        {
            throw new KeyNotFoundException($"User with ID {id} not found.");
        }
        return _postMapper.Map<PostResponseTo>(postEntity);
    }

    public async Task<PostResponseTo> AddAsync(PostRequestTo postRequest)
    {
        var postEntity = _postMapper.Map<Post>(postRequest);
        postEntity = await _postRepository.AddAsync(postEntity);
        return _postMapper.Map<PostResponseTo>(postEntity);
    }

    public async Task<PostResponseTo> UpdateAsync(PostRequestTo postRequest)
    {
        var existingpost = await _postRepository.GetByIdAsync(postRequest.id);
        if (existingpost == null)
        {
            throw new KeyNotFoundException($"User with ID {postRequest.id} not found.");
        }

        var updatedpost = _postMapper.Map<Post>(postRequest);
        return _postMapper.Map<PostResponseTo>(await _postRepository.UpdateAsync(updatedpost));
    }

    public async Task DeleteAsync(long id)
    {
        var existingpost = await _postRepository.GetByIdAsync(id);
        if (existingpost == null)
        {
            throw new KeyNotFoundException($"User with ID {id} not found.");
        }
        await _postRepository.DeleteAsync(id);
    }
}