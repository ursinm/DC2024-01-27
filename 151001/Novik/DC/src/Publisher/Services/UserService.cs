using AutoMapper;
using Publisher.Models.DTOs.Requests;
using Publisher.Models.DTOs.Responses;
using Publisher.Models.Entity;
using Publisher.Repositories.interfaces;
using Publisher.Services.interfaces;

namespace Publisher.Services;

public class UserService : IUserService
{
    private readonly IUserRepository _userRepository;
    private readonly IMapper _userMapper;

    public UserService(IUserRepository userRepository,IMapper userMapper)
    {
        _userMapper = userMapper;
        _userRepository = userRepository;
    }
    
    public async Task<IEnumerable<UserResponseTo>> GetAllAsync()
    {
        var userEntities = await _userRepository.GetAllAsync();
        return _userMapper.Map<IEnumerable<UserResponseTo>>(userEntities);
    }

    public async Task<UserResponseTo?>? GetByIdAsync(long id)
    {
        var userEntity = await _userRepository.GetByIdAsync(id);
        if (userEntity == null)
        {
            throw new KeyNotFoundException($"User with ID {id} not found.");
        }
        return _userMapper.Map<UserResponseTo>(userEntity);
    }

    public async Task<UserResponseTo> AddAsync(UserRequestTo userRequest)
    {
        var userEntity = _userMapper.Map<User>(userRequest);
        userEntity = await _userRepository.AddAsync(userEntity);
        return _userMapper.Map<UserResponseTo>(userEntity);
    }

    public async Task<UserResponseTo> UpdateAsync(UserRequestTo userRequest)
    {
        var existingUser = await _userRepository.Exists(userRequest.id);
        if (!existingUser)
        {
            throw new KeyNotFoundException($"User with ID {userRequest.id} not found.");
        }
     
        var updatedUser = _userMapper.Map<User>(userRequest);
        return _userMapper.Map<UserResponseTo>(_userRepository.UpdateAsync(updatedUser).Result);
    }

    public async Task DeleteAsync(long id)
    {
        var existingUser = await _userRepository.Exists(id);
        if (!existingUser)
        {
            throw new KeyNotFoundException($"User with ID {id} not found.");
        }
        _userRepository.DeleteAsync(id);
    }
}