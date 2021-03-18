package com.controller;

import com.Dtos.UserDto;
import com.exception.UserNotFoundException;
import com.mappers.UserMapping;
import com.model.User;
import com.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController

public class UsersController {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsersController(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/users/get")
    public List<UserDto> getAllUsers() {
        List <User> users = userRepository.findAll();
        ArrayList<UserDto> userDtos = new ArrayList<>();

        for (User user : users){
            userDtos.add(UserMapping.map(user));
        }
        return userDtos;
    }

    @Transactional
    @PostMapping("/users/post")
    public User createNote(@Valid @RequestBody User user) throws InterruptedException {
        Thread.sleep(10000);
        return userRepository.save(user);
    }

    @Transactional
    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable(value = "id") Integer userId) throws UserNotFoundException, InterruptedException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        userRepository.delete(user);
        Thread.sleep(10000);
        return ResponseEntity.ok().build();
    }

    @Transactional
    @PutMapping("/users/put/{id}")
    public User updateNote(@PathVariable (value = "id") Integer userId,
                           @Valid @RequestBody User userDetails) throws UserNotFoundException, InterruptedException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        user.setName(userDetails.getName());
        user.setAge(userDetails.getAge());
        user.setRole(userDetails.getRole());
        user.setUserName(userDetails.getUserName());
        user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        Thread.sleep(10000);
        return userRepository.save(user);
    }

}
