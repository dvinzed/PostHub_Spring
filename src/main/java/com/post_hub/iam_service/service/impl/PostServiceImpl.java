package com.post_hub.iam_service.service.impl;

import com.post_hub.iam_service.mapper.PostMapper;
import com.post_hub.iam_service.model.constants.ApiErrorMessage;
import com.post_hub.iam_service.model.dto.Post.PostDTO;
import com.post_hub.iam_service.model.enteties.Post;
import com.post_hub.iam_service.model.exception.NotFoundExceptinon;
import com.post_hub.iam_service.model.request.post.PostRequest;
import com.post_hub.iam_service.model.response.IamResponse;
import com.post_hub.iam_service.repositories.PostRepository;
import com.post_hub.iam_service.service.PostService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    public final PostRepository postRepository;
    private final List<String> posts = new ArrayList<>();
    private final PostMapper postMapper;

    @Override
    public IamResponse<PostDTO> createPost(@NotNull PostRequest postRequest) {
         Post post = postMapper.createPost(postRequest);

         Post savedPost = postRepository.save(post);

         PostDTO postDTO = postMapper.toPostDto(savedPost);


        return IamResponse.createSuccessful(postDTO);
    }

    @Override
    public IamResponse<PostDTO> getById(@NotNull Integer id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptinon(ApiErrorMessage.POST_NOT_FOUND_BY_ID.getMessage(id)));
        PostDTO postDTO = postMapper.toPostDto(post);
        return IamResponse.createSuccessful(postDTO);
    }

}
