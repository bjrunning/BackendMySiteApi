package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class PostCommentDTO {

    private Long id;

    private Long authorId;

    private Long postId;

    private String body;

    private Date createdAt;
}
