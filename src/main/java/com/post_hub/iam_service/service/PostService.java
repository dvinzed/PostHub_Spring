package com.post_hub.iam_service.service;

import com.post_hub.iam_service.model.dto.Post.PostDTO;
import com.post_hub.iam_service.model.response.IamResponse;
import jakarta.validation.constraints.NotNull;


public interface PostService {
    void createPost(String postContent);
    IamResponse<PostDTO> getById(@NotNull Integer id);
}
