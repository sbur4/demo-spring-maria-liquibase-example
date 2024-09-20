package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class Controller {

    @Autowired
    private final UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        log.debug("GET users: {}", users);
        return users;
    }

    @PostMapping("/user/{text}")
    public User createUser(@PathVariable String text) {
        User user = userRepository.save(new User(text));
        log.debug("Create user: {}", user);
        return user;
    }

    @PutMapping("/user/{id}")
    public User updateUser(@PathVariable Long id, @RequestParam(value = "name", required = true) String name) {
        User user = userRepository.findById(id).get();
        user.setName(name);

        userRepository.save(user);

        log.debug("Update user: {}", user);
        return user;
    }

    @DeleteMapping("/user/{id}")
    public long deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }

        log.debug("Delete user: {}", id);
        return id;
    }
}
