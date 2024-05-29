package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class PostCommentParamsDTO {

    private String nameCont;

    private Long authorId;

    private Long postId;

    private Date createdAtGt;
}
