package com.controller;

import com.exception.UserNotFoundException;
import com.model.User;
import com.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController

public class HelloController {
    final
    UserRepository userRepository;

    public HelloController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<User> getAllNotes() {

        return userRepository.findAll();
    }
    @PutMapping("/users/{id}")
    public User updateNote(@PathVariable (value = "id") Integer userId,
                           @Valid @RequestBody User userDetails) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        user.setName(userDetails.getName());
        user.setAge(userDetails.getAge());

        return userRepository.save(user);
    }
}