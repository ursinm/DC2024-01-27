package com.luschickij.publisher.controller.crud;

import com.luschickij.publisher.dto.post.PostRequestTo;
import com.luschickij.publisher.dto.post.PostResponseTo;
import com.luschickij.publisher.model.Post;
import com.luschickij.publisher.service.post.PostService;
import com.luschickij.publisher.utils.modelassembler.PostModelAssembler;
import io.netty.util.internal.StringUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/posts")
public class PostController extends CommonRESTController<Post, PostRequestTo, PostResponseTo> {
    public PostController(@Qualifier("cachedPostService") PostService service,
                             PostModelAssembler assembler) {
        super(service, assembler::toModel);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> newEntity(@RequestBody PostRequestTo request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
       String country = httpServletRequest.getLocale().getCountry();
       if (StringUtils.isEmpty(country)){
           country = "locale";
       }
        request.setCountry(country);
        return super.newEntity(request, httpServletRequest, httpServletResponse);
    }
}
