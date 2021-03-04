package com.controller;

import com.exception.UserNotFoundException;
import com.model.User;
import com.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController

public class UsersController {
    final
    UserRepository userRepository;

    public UsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users/get")
    public List<User> getAllNotes() {

        return userRepository.findAll();
    }

    @PostMapping("/users/post")
    public User createNote(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }

    @DeleteMapping("/books/delete/{id}")
    public ResponseEntity deleteBook(@PathVariable(value = "id") Integer userId) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        userRepository.delete(user);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/users/put/{id}")
    public User updateNote(@PathVariable (value = "id") Integer userId,
                           @Valid @RequestBody User userDetails) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        user.setName(userDetails.getName());
        user.setAge(userDetails.getAge());
        user.setRole(userDetails.getRole());
        user.setUserName(userDetails.getUserName());
        user.setPassword(userDetails.getPassword());


        return userRepository.save(user);
    }
}
