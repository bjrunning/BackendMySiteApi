package org.example.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostCreateDTO {

    @NotNull(message = "Автор не должен быть пустым")
    private Long authorId;

    @NotNull(message = "Слаг не должен быть пустым")
    private String slug;

    @NotNull(message = "Название не должно быть пустым")
    private String name;

    @NotNull(message = "Описание поста не должно быть пустым")
    private String body;
}
