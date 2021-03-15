package com.exception;


public class BookNotFoundException extends Exception {
    private Integer book_id;

    public BookNotFoundException (Integer book_id) {
        super(String.format("Book is not found with id : '%s'", book_id));
    }
}
