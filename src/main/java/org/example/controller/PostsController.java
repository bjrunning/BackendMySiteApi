package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.example.dto.PostCreateDTO;
import org.example.dto.PostDTO;
import org.example.dto.PostUpdateDTO;
import org.example.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostsController {

    @Autowired
    private PostService postService;

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получение списка всех постов.")
    @ApiResponse(responseCode = "200", description = "Список всех постов.")
    @GetMapping("/posts")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PostDTO>> index() {
        var posts = postService.getAll();

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(posts.size()))
                .body(posts);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Создание нового поста.")
    @ApiResponse(responseCode = "201", description = "Пост создан.")
    @PostMapping("/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public PostDTO create(@Parameter(description = "Данные поста, которые нужно сохранить.")
                   @Valid @RequestBody PostCreateDTO postData) {
        var postCreate = postService.create(postData);
        return postCreate;
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получение конкретного поста по его идентификатору.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пост найден."),
            @ApiResponse(responseCode = "404", description = "Пост с таким идентификатором не найден.")
    })
    @GetMapping("/posts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostDTO show(@Parameter(description = "Идентификатор поста, которого нужно найти.")
                 @PathVariable Long id) {
        var postId = postService.findById(id);
        return postId;
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Обновление поста по его идентификатору.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пост обновлен."),
            @ApiResponse(responseCode = "404", description = "Пост с таким идентификатором не найден.")
    })
    @PutMapping("/posts/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("@userUtils.isAuthor(#id)")
    public PostDTO update(@Parameter(description = "Данные поста, которые нужно обновить.")
                   @RequestBody @Valid PostUpdateDTO postData,
                   @Parameter(description = "Идентификатор поста, которого нужно обновить.")
                   @PathVariable Long id) {
        var postUpdate = postService.update(postData, id);
        return postUpdate;
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Удаление поста по его идентификатору.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Пост удален."),
            @ApiResponse(responseCode = "404", description = "Пост с таким идентификатором не найден.")
    })
    @DeleteMapping("/posts/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("@userUtils.isAuthor(#id)")
    public void destroy(@Parameter(description = "Идентификатор поста, которого нужно удалить")
                 @PathVariable Long id) {
        postService.delete(id);
    }
}
