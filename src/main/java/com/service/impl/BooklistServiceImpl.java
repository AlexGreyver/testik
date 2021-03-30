package com.service.impl;

import com.Dto.BookDto;
import com.exception.BookAccessException;
import com.exception.BookNotFoundException;
import com.mapper.BookMapping;
import com.model.Book;
import com.model.User;
import com.repository.BookRepository;
import com.repository.UserRepository;
import com.service.interfaces.BooklistService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BooklistServiceImpl implements BooklistService {

    @Override
    public List<BookDto> getPermittedBooksService(String currentUserName, List <Book> books,
                                                   List <User> users, BookRepository bookRepository) {


        ArrayList<BookDto> bookDtos = new ArrayList<>();
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

    @Override
    public Book getPermittedBookService(Integer bookId, String currentUserName, BookRepository bookRepository,
                                        UserRepository userRepository)
            throws BookAccessException, InterruptedException, BookNotFoundException {

        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        if (book.getLocker().getUserName().equals(currentUserName) ||
                ((book.getUser() == null) && (book.getLocker() == null)) ) {
            book.setUser(userRepository.findByUserName(currentUserName));
            book.setLocker(null);
            Thread.sleep(10000);
            return bookRepository.save(book);
        }
        else {
            throw new BookAccessException(bookId);
        }
    }

    @Override
    public Book lockPermittedBookService(Integer bookId, String currentUserName, BookRepository bookRepository,
                                         UserRepository userRepository)
            throws BookAccessException, InterruptedException, BookNotFoundException {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        if ((book.getUser() == null) && (book.getLocker() == null)) {
            book.setLocker(userRepository.findByUserName(currentUserName));
            Thread.sleep(10000);
            return bookRepository.save(book);
        } else {
            throw new BookAccessException(bookId);
        }
    }
}
