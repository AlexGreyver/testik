package com.exception;


public class BookAccessException extends Exception {
    private Integer book_id;

    public BookAccessException(Integer book_id) {
        super(String.format("Book is not yours with id : '%s'", book_id));
    }
}
