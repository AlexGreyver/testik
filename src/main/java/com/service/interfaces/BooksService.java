package com.service.interfaces;

import com.Dto.BookDto;
import com.exception.BookNotFoundException;
import com.exception.UserNotFoundException;
import com.model.Book;
import com.repository.BookRepository;
import com.repository.UserRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BooksService {

    /**
     * Получение списка книг
     * @param bookRepository - репозиторий книг
     * @return список книг
     */
    List<BookDto> getAllBooksService(BookRepository bookRepository);

    /**
     * Создать книгу
     * @param book - книга
     * @param userId - айди юзера
     * @param userRepository - репозиторий юзеров
     * @param bookRepository - репозиторий книг
     * @return - новая книга
     * @throws UserNotFoundException - не найден пользователь
     * @throws InterruptedException - прервалось что-то?
     */
    Book createNoteService(Book book, Integer userId, UserRepository userRepository, BookRepository bookRepository)
            throws UserNotFoundException, InterruptedException;

    /**
     * удаление книги
     * @param bookId - айди книги
     * @param bookRepository - репозиторий книг
     * @return обновленная таблица
     * @throws BookNotFoundException - не найдена книга
     * @throws InterruptedException - прервалось что-то?
     */
    ResponseEntity deleteBookService(Integer bookId, BookRepository bookRepository)
            throws BookNotFoundException, InterruptedException;

    /**
     * обновить книгу
     * @param bookId - айди книги
     * @param bookDetails - новые значения
     * @param bookRepository - репозиторий книг
     * @return - измененная книга
     * @throws BookNotFoundException - не найдена книга
     * @throws InterruptedException - прервалось что-то?
     */
    Book updateNoteService(Integer bookId, Book bookDetails, BookRepository bookRepository)
            throws BookNotFoundException, InterruptedException;
}
