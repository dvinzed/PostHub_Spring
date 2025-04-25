package com.post_hub.iam_service.controller;

import com.post_hub.iam_service.model.constants.ApiLogMessage;
import com.post_hub.iam_service.model.dto.Post.PostDTO;
import com.post_hub.iam_service.model.response.IamResponse;
import com.post_hub.iam_service.repositories.PostRepository;
import com.post_hub.iam_service.service.PostService;
import com.post_hub.iam_service.service.impl.PostServiceImpl;
import com.post_hub.iam_service.utils.ApiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("${end.point.posts}")
public class PostController {

    private final PostServiceImpl postServiceImpl;

    private final PostRepository postRepository;
    private static final Logger log = LoggerFactory.getLogger(PostController.class);

    private final PostService postService;



    @Autowired
    public PostController(PostServiceImpl postServiceImpl, PostRepository postRepository, PostService postService) {
        this.postServiceImpl = postServiceImpl;
        this.postRepository = postRepository;
        this.postService = postService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createPost(@RequestBody Map<String, Object> requestBody){
        String title = (String) requestBody.get("title");
        String content = (String) requestBody.get("content");

        String postContent = "Title: " + title + "\nContent: " + content + "\n";

        postServiceImpl.createPost(postContent);

        return new ResponseEntity<>("Post create with title: " + title, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IamResponse<PostDTO>> getPostById(@PathVariable(name = "id") Integer postId){
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());
        IamResponse<PostDTO> response = postService.getById(postId);
        return ResponseEntity.ok(response);
    }

}
