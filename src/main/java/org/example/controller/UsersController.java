package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.example.dto.UserCreateDTO;
import org.example.dto.UserDTO;
import org.example.dto.UserUpdateDTO;
import org.example.exception.ResourceNotFoundException;
import org.example.mapper.UserMapper;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UsersController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserMapper userMapper;

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получение списка всех пользователей.")
    @ApiResponse(responseCode = "200", description = "Список всех пользователей.")
    @GetMapping("/users")
    ResponseEntity<List<UserDTO>> index() {
        var users = repository.findAll();
        var result = users.stream()
                .map(userMapper::map)
                .toList();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(users.size()))
                .body(result);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Создание нового пользователя.")
    @ApiResponse(responseCode = "201", description = "Создание пользователя.")
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    UserDTO create(@Parameter(description = "Пользовательские данные для сохранения.")
                   @Valid @RequestBody UserCreateDTO userData) {
        var user = userMapper.map(userData);
        repository.save(user);
        return userMapper.map(user);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Обновление пользователя по его идентификатору.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь обновлен."),
            @ApiResponse(responseCode = "404", description = "Пользователь с таким идентификатором не найден.")
    })
    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    UserDTO update(@Parameter(description = "Данные пользователя для обновления.")
                   @Valid @RequestBody UserUpdateDTO userData,
                   @Parameter(description = "Идентификатор пользователя, которого необходимо обновить.")
                   @PathVariable Long id) {
        var user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Не найдено."));
        userMapper.update(userData, user);
        repository.save(user);
        var userDTO = userMapper.map(user);
        return userDTO;
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получение конкретного пользователя по его идентификатору.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь найден."),
            @ApiResponse(responseCode = "404", description = "Пользователь с таким идентификатором не найден.")
    })
    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    UserDTO show(@Parameter(description = "Идентификатор пользователя, которого нужно найти.")
                  @PathVariable Long id) {
        var user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Не найдено."));
        return userMapper.map(user);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Удаление пользователя по его идентификатору.")
    @ApiResponse(responseCode = "204", description = "Пользователь удален.")
    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @Parameter(description = "Идентификатор пользователя, которого необходимо удалить.")
            @PathVariable Long id) {

        repository.deleteById(id);
    }
}
