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
    public Book getBook(@PathVariable(value = "id") Integer bookId,
                           @Valid @RequestBody HttpServletRequest httpServletRequest)
            throws BookNotFoundException, BookAccessException, InterruptedException {
        String currentUserName = httpServletRequest.getUserPrincipal().getName();
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        if (book.getUser().getUserName().equals(currentUserName) || (book.getUser().getUserName() == null) ) {
            book.setUser(userRepository.findByUserName(currentUserName));
            Thread.sleep(10000);
            return bookRepository.save(book);
        }
        else {
            throw new BookAccessException(bookId);
        }
    }

    @Transactional
    @PutMapping("/booklist/lock/{id}")
    public Book LockBook(@PathVariable(value = "id") Integer bookId, HttpServletRequest httpServletRequest)
            throws BookNotFoundException, BookAccessException, InterruptedException {
        String currentUserName = httpServletRequest.getUserPrincipal().getName();
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        if ((book.getUser().getUserName() == null) && (book.getLocker().getUserName() == null)) {
            book.setLocker(userRepository.findByUserName(currentUserName));
            Thread.sleep(10000);
            return bookRepository.save(book);
        }
        else {
            throw new BookAccessException(bookId);
        }
    }


}
