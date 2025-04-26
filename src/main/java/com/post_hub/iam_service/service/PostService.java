package com.post_hub.iam_service.service;

import com.post_hub.iam_service.model.dto.Post.PostDTO;
import com.post_hub.iam_service.model.enteties.Post;
import com.post_hub.iam_service.model.request.post.PostRequest;
import com.post_hub.iam_service.model.response.IamResponse;
import jakarta.validation.constraints.NotNull;


public interface PostService {
    IamResponse<PostDTO> createPost(@NotNull PostRequest postRequest);
    IamResponse<PostDTO> getById(@NotNull Integer id);
}
