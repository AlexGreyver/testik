package com.service.impl;

import com.Dto.UserDto;
import com.exception.UserNotFoundException;
import com.mapper.UserMapping;
import com.model.User;
import com.repository.UserRepository;
import com.service.interfaces.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {

    @Override
    public List<UserDto> getAllUsersService(UserRepository userRepository) {
        List <User> users = userRepository.findAll();
        ArrayList<UserDto> userDtos = new ArrayList<>();

        for (User user : users){
            userDtos.add(UserMapping.map(user));
        }
        return userDtos;
    }

    @Override
    public User createNoteService(User user, UserRepository userRepository) throws InterruptedException {
        Thread.sleep(10000);
        return userRepository.save(user);
    }

    @Override
    public ResponseEntity deleteUserService(Integer userId, UserRepository userRepository) throws UserNotFoundException,
            InterruptedException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        userRepository.delete(user);
        Thread.sleep(10000);
        return ResponseEntity.ok().build();
    }

    @Override
    public User updateNoteService(Integer userId, User userDetails, UserRepository userRepository,
                                  BCryptPasswordEncoder passwordEncoder)
            throws UserNotFoundException, InterruptedException {
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
