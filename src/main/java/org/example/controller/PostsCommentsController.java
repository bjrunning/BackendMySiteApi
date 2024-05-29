package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.example.dto.PostCommentDTO;
import org.example.dto.PostCommentParamsDTO;
import org.example.mapper.PostCommentMapper;
import org.example.repository.PostCommentRepository;
import org.example.specification.PostCommentSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PostsCommentsController {

    @Autowired
    private PostCommentRepository repository;

    @Autowired
    private PostCommentSpecification specBuilder;

    @Autowired
    private PostCommentMapper postCommentMapper;

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получение всех комментариев.")
    @ApiResponse(responseCode = "200", description = "Все комментарии.")
    @GetMapping("/posts_comments")
    @ResponseStatus(HttpStatus.OK)
    Page<PostCommentDTO> index(@Parameter(description = "Данные комментариев, которые нужно сохранить.")
                               PostCommentParamsDTO params, @RequestParam(defaultValue = "1") int page) {
        var spec = specBuilder.build(params);
        var comments = repository.findAll(spec, PageRequest.of(page - 1, 10));
        var result = comments.map(postCommentMapper::map);

        return result;
    }
}
