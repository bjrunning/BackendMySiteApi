package org.example.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserUpdateDTO {

    @NotNull(message = "Имя не может быть пустым")
    private String firstName;

    @NotNull(message = "Фамилия не может быть пустой")
    private String lastName;
}
