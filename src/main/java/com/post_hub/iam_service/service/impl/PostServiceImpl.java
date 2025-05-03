package com.post_hub.iam_service.service.impl;

import com.post_hub.iam_service.mapper.PostMapper;
import com.post_hub.iam_service.model.constants.ApiErrorMessage;
import com.post_hub.iam_service.model.dto.Post.PostDTO;
import com.post_hub.iam_service.model.dto.Post.PostSearchDTO;
import com.post_hub.iam_service.model.enteties.Post;
import com.post_hub.iam_service.model.enteties.User;
import com.post_hub.iam_service.model.exception.DataExistException;
import com.post_hub.iam_service.model.exception.NotFoundExceptinon;
import com.post_hub.iam_service.model.request.post.PostRequest;
import com.post_hub.iam_service.model.request.post.PostSearchRequest;
import com.post_hub.iam_service.model.request.post.UpdatePostRequest;
import com.post_hub.iam_service.model.response.IamResponse;
import com.post_hub.iam_service.model.response.PaginationResponse;
import com.post_hub.iam_service.repositories.PostRepository;
import com.post_hub.iam_service.repositories.UserRepository;
import com.post_hub.iam_service.repositories.criteria.PostSearchCriteria;
import com.post_hub.iam_service.service.PostService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    public final PostRepository postRepository;
    private final List<String> posts = new ArrayList<>();
    private final PostMapper postMapper;
    private final UserRepository userRepository;

    @Override
    public IamResponse<PostDTO> createPost(@NotNull Long userId, PostRequest postRequest) {

        if(postRepository.existsByTitle(postRequest.getTitle())){
            throw new DataExistException(ApiErrorMessage.POST_ALREADY_EXISTS.getMessage(postRequest.getTitle()));
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundExceptinon(ApiErrorMessage.USER_NOT_FOUND_BY_ID.getMessage(userId)));

         Post post = postMapper.createPost(postRequest,user);

         Post savedPost = postRepository.save(post);

         PostDTO postDTO = postMapper.toPostDto(savedPost);


        return IamResponse.createSuccessful(postDTO);
    }

    @Override
    public IamResponse<PostDTO> getById(@NotNull Integer id) {
        Post post = postRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundExceptinon(ApiErrorMessage.POST_NOT_FOUND_BY_ID.getMessage(id)));
        PostDTO postDTO = postMapper.toPostDto(post);
        return IamResponse.createSuccessful(postDTO);
    }

    @Override
    public IamResponse<PostDTO> updatePost(@NotNull Integer id,@NotNull UpdatePostRequest UpdatePostRequest) {
        Post post = postRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundExceptinon(ApiErrorMessage.POST_NOT_FOUND_BY_ID.getMessage(id)));
        postMapper.UpdatePost(post,UpdatePostRequest);
        post = postRepository.save(post);

        PostDTO postDTO = postMapper.toPostDto(post);

        return IamResponse.createSuccessful(postDTO);
    }

    @Override
    public void softDeletePost(Integer id) {
        Post post = postRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundExceptinon(ApiErrorMessage.POST_NOT_FOUND_BY_ID.getMessage(id)));

        post.setDeleted(true);
        postRepository.save(post);
    }

    @Override
    public IamResponse<PaginationResponse<PostSearchDTO>> findAllPost(Pageable pageable) {
        Page<PostSearchDTO> posts = postRepository.findAll(pageable)
                .map(postMapper::toPostSearchDto);

        PaginationResponse<PostSearchDTO> paginationResponse = new PaginationResponse<>(
          posts.getContent(),
                new PaginationResponse.Pagination(
                        posts.getTotalElements(),
                        pageable.getPageSize(),
                        posts.getNumber() + 1,
                        posts.getTotalPages()

            )
        );
        return IamResponse.createSuccessful(paginationResponse);

    }

    @Override
    public IamResponse<PaginationResponse<PostSearchDTO>> searchPost(PostSearchRequest request, Pageable pageable) {
        Specification<Post> specification = new PostSearchCriteria(request);
        Page<PostSearchDTO> posts = postRepository.findAll(specification, pageable)
                .map(postMapper::toPostSearchDto);
        PaginationResponse<PostSearchDTO> response = PaginationResponse.<PostSearchDTO>builder()
                .content(posts.getContent())
                .pagination(PaginationResponse.Pagination.builder()
                        .total(posts.getTotalElements())
                        .limit(pageable.getPageSize())
                        .page(posts.getNumber() + 1)
                        .pages(posts.getTotalPages())
                        .build())
                .build();
        return IamResponse.createSuccessful(response);

    }

}
