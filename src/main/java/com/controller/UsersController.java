package com.controller;

import com.Dto.UserDto;
import com.exception.UserNotFoundException;
import com.model.User;
import com.repository.UserRepository;
import com.service.interfaces.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController

public class UsersController {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UsersService usersService;

    public UsersController(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, UsersService usersService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.usersService = usersService;
    }

    @GetMapping("/users/get")
    public List<UserDto> getAllUsers() {
        return usersService.getAllUsersService(userRepository);
    }

    @Transactional
    @PostMapping("/users/post")
    public User createNote(@Valid @RequestBody User user) throws InterruptedException {
        return usersService.createNoteService(user, userRepository);
    }

    @Transactional
    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable(value = "id") Integer userId) throws UserNotFoundException,
            InterruptedException {
        return usersService.deleteUserService(userId, userRepository);
    }

    @Transactional
    @PutMapping("/users/put/{id}")
    public User updateNote(@PathVariable (value = "id") Integer userId,
                           @Valid @RequestBody User userDetails) throws UserNotFoundException, InterruptedException {
        return usersService.updateNoteService(userId, userDetails, userRepository, passwordEncoder);
    }

}
