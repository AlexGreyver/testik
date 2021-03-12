package com.controller;

import com.exception.UserNotFoundException;
import com.model.Book;
import com.model.User;
import com.repository.BookRepository;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController

public class UsersController {
    final
    UserRepository userRepository;

    final
    BookRepository bookRepository;

    public UsersController(UserRepository userRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/users/get")
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    @GetMapping("/books/get")
    public List<Book> getAllBooks() {

        return bookRepository.findAll();
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

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PutMapping("/users/put/{id}")
    public User updateNote(@PathVariable (value = "id") Integer userId,
                           @Valid @RequestBody User userDetails) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        user.setName(userDetails.getName());
        user.setAge(userDetails.getAge());
        user.setRole(userDetails.getRole());
        user.setUserName(userDetails.getUserName());
        user.setPassword(passwordEncoder.encode(userDetails.getPassword()));


        return userRepository.save(user);
    }
}
