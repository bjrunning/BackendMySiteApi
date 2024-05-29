package org.example.controller;

import org.example.dto.PostCommentDTO;
import org.example.dto.PostCommentParamsDTO;
import org.example.mapper.PostCommentMapper;
import org.example.repository.PostCommentRepository;
import org.example.specification.PostCommentSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PostsCommentsController {

    @Autowired
    private PostCommentRepository repository;

    @Autowired
    private PostCommentSpecification specBuilder;

    @Autowired
    private PostCommentMapper postCommentMapper;

    @GetMapping("/posts_comments")
    @ResponseStatus(HttpStatus.OK)
    Page<PostCommentDTO> index(PostCommentParamsDTO params, @RequestParam(defaultValue = "1") int page) {
        var spec = specBuilder.build(params);
        var comments = repository.findAll(spec, PageRequest.of(page - 1, 10));
        var result = comments.map(postCommentMapper::map);

        return result;
    }
}
