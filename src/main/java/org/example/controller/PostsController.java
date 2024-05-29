package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.PostCreateDTO;
import org.example.dto.PostDTO;
import org.example.dto.PostUpdateDTO;
import org.example.exception.ResourceNotFoundException;
import org.example.mapper.PostMapper;
import org.example.repository.PostRepository;
import org.example.service.PostService;
import org.example.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostsController {

    @Autowired
    private PostRepository repository;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserUtils userUtils;

    @Autowired
    private PostService postService;

    @GetMapping("/posts")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<PostDTO>> index() {
        var posts = postService.getAll();

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(posts.size()))
                .body(posts);
    }

    @PostMapping("/posts")
    @ResponseStatus(HttpStatus.CREATED)
    PostDTO create(@Valid @RequestBody PostCreateDTO postData) {
        var post = postMapper.map(postData);
        post.setAuthor(userUtils.getCurrentUser());
        repository.save(post);
        var postDTO = postMapper.map(post);
        return postDTO;
    }

    @GetMapping("/posts/{id}")
    @ResponseStatus(HttpStatus.OK)
    PostDTO show(@PathVariable Long id) {
        var post = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));
        var postDTO = postMapper.map(post);
        return postDTO;
    }

    @PutMapping("/posts/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("@userUtils.isAuthor(#id)")
    PostDTO update(@RequestBody @Valid PostUpdateDTO postData, @PathVariable Long id) {
        var post = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
        postMapper.update(postData, post);
        repository.save(post);
        var postDTO = postMapper.map(post);
        return postDTO;
    }

    @DeleteMapping("/posts/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("@userUtils.isAuthor(#id)")
    void destroy(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
