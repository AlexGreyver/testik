package com.exception;


public class UserNotFoundException extends Exception {
    private Integer user_id;

    public UserNotFoundException (Integer user_id) {
        super(String.format("User is not found with id : '%s'", user_id));
    }
}
