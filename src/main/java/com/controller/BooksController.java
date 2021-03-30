package com.controller;

import com.Dto.BookDto;
import com.exception.BookNotFoundException;
import com.exception.UserNotFoundException;
import com.model.Book;
import com.repository.BookRepository;
import com.repository.UserRepository;
import com.service.interfaces.BooksService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController

public class BooksController {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BooksService booksService;

    public BooksController(UserRepository userRepository, BookRepository bookRepository, BooksService booksService) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.booksService = booksService;
    }


    @GetMapping("/books/get")
    public List<BookDto> getAllBooks() {
        return booksService.getAllBooksService(bookRepository);
    }

    @Transactional
    @PostMapping("/books/post/{userId}")
    public Book createNote(@Valid @RequestBody Book book, @PathVariable(value = "userId") Integer userId)
            throws UserNotFoundException, InterruptedException {
        return booksService.createNoteService(book, userId, userRepository, bookRepository);
    }

    @Transactional
    @DeleteMapping("/books/delete/{id}")
    public ResponseEntity deleteBook(@PathVariable(value = "id") Integer bookId)
            throws BookNotFoundException, InterruptedException {
        return booksService.deleteBookService(bookId, bookRepository);
    }

    @Transactional
    @PutMapping("/books/put/{id}")
    public Book updateNote(@PathVariable (value = "id") Integer bookId,
                           @Valid @RequestBody Book bookDetails) throws BookNotFoundException, InterruptedException {
        return booksService.updateNoteService(bookId, bookDetails, bookRepository);

    }
}
