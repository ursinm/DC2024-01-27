using System.Numerics;
using System.Reflection.Metadata;
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

public class LabelService : ILabelService
{
    private readonly ILabelRepository _labelRepository;
    private readonly IMapper _labelMapper;

    public LabelService(ILabelRepository labelRepository,IMapper labelMapper)
    {
        _labelMapper = labelMapper;
        _labelRepository = labelRepository;
    }
    public async Task<IEnumerable<LabelResponseTo>> GetAllAsync()
    {
        var labelEntities = await _labelRepository.GetAllAsync();
        return _labelMapper.Map<IEnumerable<LabelResponseTo>>(labelEntities);
    }

    public async Task<LabelResponseTo?>? GetByIdAsync(long id)
    {
        var labelEntity = await _labelRepository.GetByIdAsync(id);
        if (labelEntity == null)
        {
            throw new KeyNotFoundException($"News with ID {id} not found.");
        }
        return _labelMapper.Map<LabelResponseTo>(labelEntity);
    }

    public async Task<LabelResponseTo> AddAsync(LabelRequestTo labelRequest)
    {
        var labelEntity = _labelMapper.Map<Label>(labelRequest);
        labelEntity = await _labelRepository.AddAsync(labelEntity);
        return _labelMapper.Map<LabelResponseTo>(labelEntity);
    }

    public async Task<LabelResponseTo> UpdateAsync(LabelRequestTo labelRequest)
    {
        var existingLabel = await _labelRepository.GetByIdAsync(labelRequest.id);
        if (existingLabel == null)
        {
            throw new KeyNotFoundException($"User with ID {labelRequest.id} not found.");
        }

        var updatedLabel = _labelMapper.Map<Label>(labelRequest);
        var result = await _labelRepository.UpdateAsync(updatedLabel);
        return _labelMapper.Map <LabelResponseTo>(result);
    }

    public async Task DeleteAsync(long id)
    {
        var existingLabel = await _labelRepository.GetByIdAsync(id);
        if (existingLabel == null)
        {
            throw new KeyNotFoundException($"User with ID {id} not found.");
        }
        await _labelRepository.DeleteAsync(id);
    }
}