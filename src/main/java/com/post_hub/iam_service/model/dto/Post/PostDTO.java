package com.post_hub.iam_service.model.dto.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO implements Serializable {
    private Integer id;
    private String title;
    private String content;
    private Integer likes;
    private LocalDateTime created;
    private String createdBy;

}
