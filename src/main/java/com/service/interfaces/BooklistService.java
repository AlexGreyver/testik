package com.service.interfaces;

import com.Dto.BookDto;
import com.exception.BookAccessException;
import com.exception.BookNotFoundException;
import com.model.Book;
import com.model.User;
import com.repository.BookRepository;
import com.repository.UserRepository;

import java.util.List;

public interface BooklistService {
    /** Получение списка доступных для чтения книг
     * @param currentUserName - имя текущего пользователя
     * @param books список всех книг
     * @param users список всех юзеров
     * @param bookRepository - репозиторий книг
     * @return - список доступных для чтения книг
     */
    List<BookDto> getPermittedBooksService(String currentUserName, List <Book> books,
                                           List <User> users, BookRepository bookRepository);

    /**
     * забрать доступную книгу
     * @param bookId - id книги
     * @param currentUserName - имя текущего пользователя
     * @param bookRepository - репозиторий книг
     * @param userRepository - репозиторий юзеров
     * @return - изменение имени владельца
     */

    Book getPermittedBookService(Integer bookId, String currentUserName, BookRepository bookRepository, UserRepository
            userRepository) throws BookAccessException, InterruptedException, BookNotFoundException;

    /**
     * забронировать доступную книгу
     * @param bookId - id книги
     * @param currentUserName - имя текущего пользователя
     * @param bookRepository - репозиторий книг
     * @param userRepository - репозиторий юзеров
     * @return - изменение имени бронирующего
     */

    Book lockPermittedBookService(Integer bookId, String currentUserName, BookRepository bookRepository, UserRepository
            userRepository) throws BookAccessException, InterruptedException, BookNotFoundException;
}
