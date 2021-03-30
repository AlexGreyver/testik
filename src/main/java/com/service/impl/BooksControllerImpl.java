package com.service.impl;

import com.Dto.BookDto;
import com.exception.BookNotFoundException;
import com.exception.UserNotFoundException;
import com.mapper.BookMapping;
import com.model.Book;
import com.model.User;
import com.repository.BookRepository;
import com.repository.UserRepository;
import com.service.interfaces.BooksService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BooksControllerImpl implements BooksService {

    @Override
    public List<BookDto> getAllBooksService(BookRepository bookRepository) {
        List <Book> books = bookRepository.findAll();
        ArrayList<BookDto> bookDtos = new ArrayList<>();
        for (Book book : books){
            bookDtos.add(BookMapping.map(book));
        }
        return bookDtos;
    }

    @Override
    public Book createNoteService(Book book, Integer userId, UserRepository userRepository, BookRepository bookRepository)
            throws UserNotFoundException, InterruptedException {
        if (userId != null) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException(userId));
            book.setUser(user);
        }
        Thread.sleep(10000);
        return bookRepository.save(book);
    }

    @Override
    public ResponseEntity deleteBookService(Integer bookId, BookRepository bookRepository)
            throws BookNotFoundException, InterruptedException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));
        bookRepository.delete(book);
        Thread.sleep(10000);
        return ResponseEntity.ok().build();
    }

    @Override
    public Book updateNoteService(Integer bookId, Book bookDetails, BookRepository bookRepository)
            throws BookNotFoundException, InterruptedException {

        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));

        book.setName(bookDetails.getName());
        book.setYear(bookDetails.getYear());
        book.setUser(bookDetails.getUser());
        book.setLocker(bookDetails.getLocker());
        Thread.sleep(10000);
        return bookRepository.save(book);

    }
}
