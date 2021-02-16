package com.example.testik;

import com.model.User;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController

public class HelloController {
    @Autowired
    UserRepository userRepository;
    @GetMapping("/users")
    public List<User> getAllNotes() {

        return userRepository.findAll();
    }
    @PutMapping("/users/{id}")
    public User updateNote(@PathVariable (value = "id") Integer userId,
                           @Valid @RequestBody User userDetails) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception());
        user.setName(userDetails.getName());
        user.setAge(userDetails.getAge());

        User updatedUser = userRepository.save(user);
        return updatedUser;
    }
}