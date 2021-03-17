package com.controller;

import com.Dtos.BookDto;
import com.mappers.BookMapping;
import com.model.Book;
import com.repository.BookRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookListController {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    private final String authName = auth.getName();
    private final BookRepository bookRepository;

    public BookListController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/booklist")
    public List<BookDto> getAllBooks() {
        List <Book> books = bookRepository.findAll();
        ArrayList<BookDto> bookDtos = new ArrayList<>();
        for (Book book : books){
            if (!(book.getUser().getUserName().equals(authName))){
                books.removeIf(duplicate -> book.getUser().getUserName().equals(duplicate.getUser().getUserName()) && book.getName().equals(duplicate.getName()));
            }
        }
        for (Book book : books){
            bookDtos.add(BookMapping.map(book));
        }
        return bookDtos;
    }
}
