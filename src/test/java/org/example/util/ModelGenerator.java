package org.example.util;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import net.datafaker.Faker;
import org.example.model.Post;
import org.example.model.User;
import org.instancio.Instancio;
import org.instancio.Model;
import org.instancio.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ModelGenerator {

    private Model<Post> postModel;
    private Model<User> userModel;

    @Autowired
    private Faker faker;

    @PostConstruct
    private void init() {
        postModel = Instancio.of(Post.class)
                .ignore(Select.field(Post::getId))
                .supply(Select.field(Post::getName), () -> faker.gameOfThrones().house())
                .supply(Select.field(Post::getBody), () -> faker.gameOfThrones().quote())
                .toModel();

        userModel = Instancio.of(User.class)
                .ignore(Select.field(User::getId))
                .ignore(Select.field(User::getPosts))
                .supply(Select.field(User::getEmail), () -> faker.internet().emailAddress())
                .toModel();
    }
}
