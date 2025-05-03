package com.post_hub.iam_service.service;

import com.post_hub.iam_service.model.dto.Post.PostDTO;
import com.post_hub.iam_service.model.dto.Post.PostSearchDTO;
import com.post_hub.iam_service.model.enteties.Post;
import com.post_hub.iam_service.model.request.post.PostRequest;
import com.post_hub.iam_service.model.request.post.PostSearchRequest;
import com.post_hub.iam_service.model.request.post.UpdatePostRequest;
import com.post_hub.iam_service.model.response.IamResponse;
import com.post_hub.iam_service.model.response.PaginationResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;


public interface PostService {
    IamResponse<PostDTO> createPost(@NotNull Long userId,@NotNull PostRequest postRequest);
    IamResponse<PostDTO> getById(@NotNull Integer id);
    IamResponse<PostDTO> updatePost(@NotNull Integer id, @NotNull UpdatePostRequest UpdatePostRequest);
    void softDeletePost(@NotNull Integer id);
    IamResponse<PaginationResponse<PostSearchDTO>> findAllPost(Pageable pageable);
    IamResponse<PaginationResponse<PostSearchDTO>> searchPost(@NotNull PostSearchRequest request, Pageable pageable);
}
