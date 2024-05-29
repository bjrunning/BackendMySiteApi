package org.example.util;

import lombok.AllArgsConstructor;
import org.example.model.User;
import org.example.repository.PostRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserUtils {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    public User getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        var email = authentication.getName();
        return userRepository.findByEmail(email).get();
    }

    public boolean isAuthor(long postId) {
        var postAuthorEmail = postRepository.findById(postId).get().getAuthor().getEmail();
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return postAuthorEmail.equals(authentication.getName());
    }

    public User getTestUser() {
        return userRepository.findByEmail("bjrunning@example.com")
                .orElseThrow(() -> new RuntimeException("User doesn't exist"));
    }
}
