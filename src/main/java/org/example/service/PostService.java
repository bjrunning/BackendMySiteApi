package org.example.service;

import org.example.dto.PostCreateDTO;
import org.example.dto.PostDTO;
import org.example.dto.PostUpdateDTO;
import org.example.exception.ResourceNotFoundException;
import org.example.mapper.PostMapper;
import org.example.repository.PostRepository;
import org.example.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository repository;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserUtils userUtils;

    public List<PostDTO> getAll() {
        var posts = repository.findAll();
        var result = posts.stream()
                .map(postMapper::map)
                .toList();
        return result;
    }

    public PostDTO create(PostCreateDTO postData) {
        var post = postMapper.map(postData);
        post.setAuthor(userUtils.getCurrentUser());
        repository.save(post);
        var postDTO = postMapper.map(post);
        return postDTO;
    }

    public PostDTO findById(Long id) {
        var post = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Не найдено: " + id));
        var postDTO = postMapper.map(post);
        return postDTO;
    }

    public PostDTO update(PostUpdateDTO postData, Long id) {
        var post = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Не найдено: " + id));
        postMapper.update(postData, post);
        repository.save(post);
        var postDTO = postMapper.map(post);
        return postDTO;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
