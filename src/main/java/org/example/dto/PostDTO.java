package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class PostDTO {

    private Long id;

    private Long authorId;

    private String slug;

    private String name;

    private String body;

    private Date createdAt;
}
