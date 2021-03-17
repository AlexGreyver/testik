package com.mappers;

import com.Dtos.UserDto;
import com.model.User;

public class UserMapping {


    public static UserDto map(User user){
        UserDto userDto = new UserDto();
        userDto.setAge(user.getAge());
        userDto.setName(user.getName());
        return userDto;
    }
}
