package com.post_hub.iam_service.mapper;

import com.post_hub.iam_service.model.dto.Post.PostDTO;
import com.post_hub.iam_service.model.dto.Post.PostSearchDTO;
import com.post_hub.iam_service.model.enteties.Post;
import com.post_hub.iam_service.model.enteties.User;
import com.post_hub.iam_service.model.request.post.PostRequest;
import com.post_hub.iam_service.model.request.post.UpdatePostRequest;
import org.hibernate.type.descriptor.DateTimeUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Objects;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        imports = {DateTimeUtils.class, Objects.class}
)
public interface PostMapper {

     @Mapping(source = "id", target="id")
     @Mapping(source = "title", target="title")
     @Mapping(source = "content", target="content")
     @Mapping(source = "likes", target="likes")
     @Mapping(source = "created", target="created", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    PostDTO toPostDto(Post post);

     @Mapping(target="id", ignore = true)
     @Mapping(target="created", ignore = true)
     @Mapping(target="user", source = "user")
     @Mapping (target = "createdBy", source = "user.username")
     Post createPost(PostRequest postRequest, User user);


    @Mapping(target="id", ignore = true)
    @Mapping(target="created", ignore = true)
    void UpdatePost(@MappingTarget Post post , UpdatePostRequest UpdatepostRequest);

    @Mapping(source = "deleted", target = "isDeleted")
    @Mapping (target = "createdBy", source = "user.username")
    PostSearchDTO toPostSearchDto(Post post);

}
