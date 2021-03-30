package com.controller;

import com.Dto.BookDto;
import com.exception.BookAccessException;
import com.exception.BookNotFoundException;
import com.model.Book;
import com.model.User;
import com.repository.BookRepository;
import com.repository.UserRepository;
import com.service.interfaces.BooklistService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
public class BookListController {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BooklistService booklistService;

    public BookListController(UserRepository userRepository, BookRepository bookRepository, BooklistService booklistService) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.booklistService = booklistService;
    }

    @GetMapping("/booklist")
    public List<BookDto> getPermittedBooks(HttpServletRequest httpServletRequest) {
        String currentUserName = httpServletRequest.getUserPrincipal().getName();
        User currentUser = userRepository.findByUserName(currentUserName);
        List <Book> books = bookRepository.findBooksByUser(currentUser);
        List <User> users = userRepository.findAll();
        return booklistService.getPermittedBooksService(currentUserName, books, users, bookRepository);
    }

    @Transactional
    @PutMapping("/booklist/put/{id}")
    public Book getPermittedBook(@PathVariable(value = "id") Integer bookId,
                           @Valid @RequestBody HttpServletRequest httpServletRequest)
            throws BookAccessException, InterruptedException, BookNotFoundException {
        String currentUserName = httpServletRequest.getUserPrincipal().getName();
        return booklistService.getPermittedBookService(bookId, currentUserName,bookRepository,userRepository);
    }

    @Transactional
    @PutMapping("/booklist/lock/{id}")
    public Book lockPermittedBook(@PathVariable(value = "id") Integer bookId, HttpServletRequest httpServletRequest)
            throws BookNotFoundException, BookAccessException, InterruptedException {
        String currentUserName = httpServletRequest.getUserPrincipal().getName();
        return booklistService.lockPermittedBookService(bookId, currentUserName,bookRepository,userRepository);
    }
}
