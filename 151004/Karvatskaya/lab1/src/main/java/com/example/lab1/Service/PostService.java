package com.example.lab1.Service;

import com.example.lab1.DAO.PostDao;
import com.example.lab1.DTO.PostRequestTo;
import com.example.lab1.DTO.PostResponseTo;
import com.example.lab1.Exception.NotFoundException;
import com.example.lab1.Mapper.PostListMapper;
import com.example.lab1.Mapper.PostMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class PostService {
    @Autowired
    PostDao postDao;
    @Autowired
    PostMapper postMapper;
    @Autowired
    PostListMapper postListMapper;

    public PostResponseTo create(@Valid PostRequestTo postRequestTo){
        return postMapper.postToPostResponse(postDao.create(postMapper.postRequestToPost(postRequestTo)));
    }
    public PostResponseTo read(@Min(0) int id) throws NotFoundException {
        PostResponseTo postResponseTo = postMapper.postToPostResponse(postDao.read(id));
        if(postResponseTo != null)
            return postResponseTo;
        else
            throw new NotFoundException("Post not found", 404);
    }
    public List<PostResponseTo> readAll(){
        return postListMapper.toPostResponseList(postDao.readAll());
    }
    public PostResponseTo update(@Valid PostRequestTo postRequestTo, @Min(0) int id) throws NotFoundException {
        PostResponseTo postResponseTo = postMapper.postToPostResponse(postDao.update(postMapper.postRequestToPost(postRequestTo),id));
        if(postResponseTo != null)
            return postResponseTo;
        else
            throw new NotFoundException("Post not found", 404);
    }
    public boolean delete(@Min(0) int id) throws NotFoundException {
        boolean isDeleted = postDao.delete(id);
        if(isDeleted)
            return true;
        else
            throw new NotFoundException("Post not found", 404);
    }

}
