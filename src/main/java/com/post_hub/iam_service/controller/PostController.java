package com.post_hub.iam_service.controller;

import com.post_hub.iam_service.model.constants.ApiLogMessage;
import com.post_hub.iam_service.model.dto.Post.PostDTO;
import com.post_hub.iam_service.model.dto.Post.PostSearchDTO;
import com.post_hub.iam_service.model.request.post.PostRequest;
import com.post_hub.iam_service.model.request.post.PostSearchRequest;
import com.post_hub.iam_service.model.request.post.UpdatePostRequest;
import com.post_hub.iam_service.model.response.IamResponse;
import com.post_hub.iam_service.model.response.PaginationResponse;
import com.post_hub.iam_service.repositories.PostRepository;
import com.post_hub.iam_service.service.PostService;
import com.post_hub.iam_service.service.impl.PostServiceImpl;
import com.post_hub.iam_service.utils.ApiUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("${end.point.posts}")
public class PostController {


    private final PostRepository postRepository;
    private static final Logger log = LoggerFactory.getLogger(PostController.class);

    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<IamResponse<PostDTO>> getPostById(@PathVariable(name = "id") Integer postId){
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());
        IamResponse<PostDTO> response = postService.getById(postId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("${end.point.create}")
    public ResponseEntity<IamResponse<PostDTO>> createPost(@RequestBody @Valid PostRequest postRequest){
    log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

    long userId = 1;
    IamResponse<PostDTO> response = postService.createPost(userId,postRequest);

    return ResponseEntity.ok(response);

    }

    @PutMapping("/{id}")
    public ResponseEntity<IamResponse<PostDTO>> updatePostByID(
            @PathVariable(name = "id") Integer postId,
            @RequestBody @Valid UpdatePostRequest updatePostRequest){
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        IamResponse<PostDTO> updatedPost = postService.updatePost(postId,updatePostRequest);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeletePostByID(
            @PathVariable(name = "id") Integer postId){
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());
        postService.softDeletePost(postId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("${end.point.all}")
    public ResponseEntity<IamResponse<PaginationResponse<PostSearchDTO>>> getAllPosts(
            @RequestParam(name = "page", defaultValue = "0")int page,
            @RequestParam(name = "limit", defaultValue = "10")int limit){
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());
        Pageable pageable = PageRequest.of(page, limit);
        IamResponse<PaginationResponse<PostSearchDTO>> response = postService.findAllPost(pageable);
        return ResponseEntity.ok(response);

    }

    @PostMapping("${end.point.search}")
    public ResponseEntity<IamResponse<PaginationResponse<PostSearchDTO>>> searchPost(
            @RequestBody @Valid PostSearchRequest request,
            @RequestParam(name = "page", defaultValue = "0")int page,
            @RequestParam(name = "limit", defaultValue = "10")int limit){
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());
        Pageable pageable = PageRequest.of(page, limit);
        IamResponse<PaginationResponse<PostSearchDTO>> response = postService.searchPost(request,pageable);
        return ResponseEntity.ok(response);
    }




}
