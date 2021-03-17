package com.controller;

import com.Dtos.BookDto;
import com.exception.BookNotFoundException;
import com.exception.UserNotFoundException;
import com.mappers.BookMapping;
import com.model.Book;
import com.model.User;
import com.repository.BookRepository;
import com.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController

public class BooksController {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public BooksController(UserRepository userRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }


    @GetMapping("/books/get")
    public List<BookDto> getAllBooks() {
        List <Book> books = bookRepository.findAll();
        ArrayList <BookDto> bookDtos = new ArrayList<>();
        for (Book book : books){
            bookDtos.add(BookMapping.map(book));
        }
        return bookDtos;
    }



    @PostMapping("/books/post/{userId}")
    public Book createNote(@Valid @RequestBody Book book, @PathVariable(value = "userId") Integer userId) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        book.setUser(user);
        return bookRepository.save(book);
    }

    @DeleteMapping("/books/delete/{id}")
    public ResponseEntity deleteBook(@PathVariable(value = "id") Integer bookId) throws BookNotFoundException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));
        bookRepository.delete(book);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/books/put/{id}")
    public Book updateNote(@PathVariable (value = "id") Integer bookId,
                           @Valid @RequestBody Book bookDetails) throws BookNotFoundException {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        book.setName(bookDetails.getName());
        book.setYear(bookDetails.getYear());
        book.setUser(bookDetails.getUser());
        return bookRepository.save(book);
    }
}
