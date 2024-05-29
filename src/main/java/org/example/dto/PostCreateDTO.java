package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostCreateDTO {

    @NotBlank(message = "Автор не должен быть пустым")
    private Long authorId;

    @NotBlank(message = "Слаг не должен быть пустым")
    private String slug;

    @NotBlank(message = "Название не должно быть пустым")
    private String name;

    @NotBlank(message = "Описание поста не должно быть пустым")
    private String body;
}
