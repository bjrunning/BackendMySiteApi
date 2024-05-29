package org.example.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Setter
@Getter
public class PostUpdateDTO {

    @NotNull(message = "Автор не может быть пустым")
    private JsonNullable<Long> authorId;

    @NotNull(message = "Слаг не может быть пустым")
    private JsonNullable<String> slug;

    @NotNull(message = "Название поста не может быть пустым")
    private JsonNullable<String> name;

    @NotNull(message = "Описание поста не может быть пустым")
    private JsonNullable<String> body;
}
