package org.example.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostCreateDTO {

    @NotNull
    private Long authorId;

    @NotNull
    private String slug;

    @NotNull
    private String name;

    @NotNull
    private String body;
}
