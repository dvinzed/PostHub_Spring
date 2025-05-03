package com.post_hub.iam_service.model.enteties;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="posts")
@Getter
@Setter
public class Post {

    public static final String ID_FIELD = "id";
    public static final String TITLE_NAME_FIELD = "title";
    public static final String CONTENT_NAME_FIELD = "content";
    public static final String LIKES_NAME_FIELD = "likes";
    public static final String DELETED_FIELD = "deleted";

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable=false)
    private String title;

    @Column(nullable=false, length=500)
    private String content;

    @Column(nullable=false, updatable=false)
    @CreationTimestamp
    private LocalDateTime created;


    @Column(nullable=false,columnDefinition = "integer default 0")
    private Integer likes = 0;

    @UpdateTimestamp
    @Column(nullable=false)
    private LocalDateTime updated;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private Boolean deleted = false;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_by")
    private String createdBy;






}
