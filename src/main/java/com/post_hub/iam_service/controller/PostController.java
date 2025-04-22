package com.post_hub.iam_service.controller;

import com.post_hub.iam_service.model.constants.ApiErrorMessage;
import com.post_hub.iam_service.model.constants.ApiLogMessage;
import com.post_hub.iam_service.model.enteties.Post;
import com.post_hub.iam_service.repositories.PostRepository;
import com.post_hub.iam_service.service.PostServiceImpl;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
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

    @Autowired
    public PostController(PostServiceImpl postServiceImpl, PostRepository postRepository) {
        this.postServiceImpl = postServiceImpl;
        this.postRepository = postRepository;
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
    public ResponseEntity<Post> getPostById(@PathVariable(name = "id") Integer postId){
        log.info(ApiLogMessage.POST_INFO_BY_ID.getMessage(postId));
        return postRepository.findById(postId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.info(ApiErrorMessage.POST_NOT_FOUND_BY_ID.getMessage(postId));
                    return ResponseEntity.notFound().build();
                });
    }

}
