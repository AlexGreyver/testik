package com.controller;

import com.Dtos.BookDto;
import com.exception.BookAccessException;
import com.exception.BookNotFoundException;
import com.mappers.BookMapping;
import com.model.Book;
import com.model.User;
import com.repository.BookRepository;
import com.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BookListController {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public BookListController(UserRepository userRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/booklist")
    public List<BookDto> getPermittedBooks(HttpServletRequest httpServletRequest) {
        String currentUserName = httpServletRequest.getUserPrincipal().getName();
        User currentUser = userRepository.findByUserName(currentUserName);
        List <Book> books = bookRepository.findBooksByUser(currentUser);
        ArrayList<BookDto> bookDtos = new ArrayList<>();
        List <User> users = userRepository.findAll();
        for (User user : users){
            if (!((user.getUserName()).equals(currentUserName))){
                books.addAll(bookRepository.uniqueBooksByUser(user.getId()));
            }
        }
        for (Book book : books){
            bookDtos.add(BookMapping.map(book));
        }
        return bookDtos;
    }

    @Transactional
    @PutMapping("/booklist/put/{id}")
    public Book changeUser(@PathVariable(value = "id") Integer bookId,
                           @Valid @RequestBody Book bookDetails, HttpServletRequest httpServletRequest) throws BookNotFoundException, BookAccessException {
        String currentUserName = httpServletRequest.getUserPrincipal().getName();
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        if (book.getUser().getUserName().equals(currentUserName) || (book.getUser().getUserName() == null) ) {
            book.setUser(bookDetails.getUser());
            return bookRepository.save(book);
        }
        else {
            throw new BookAccessException(bookId);
        }
    }
}
