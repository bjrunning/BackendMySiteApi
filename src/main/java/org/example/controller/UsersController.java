package org.example.controller;

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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UsersController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserMapper userMapper;

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

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    UserDTO create(@Valid @RequestBody UserCreateDTO userData) {
        var user = userMapper.map(userData);
        repository.save(user);
        return userMapper.map(user);
    }

    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    UserDTO update(@RequestBody UserUpdateDTO userData, @PathVariable Long id) {
        var user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
        userMapper.update(userData, user);
        repository.save(user);
        var userDTO = userMapper.map(user);
        return userDTO;
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    UserDTO show(@PathVariable Long id) {
        var user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
        return userMapper.map(user);
    }
}
