package com.service.interfaces;

import com.Dto.UserDto;
import com.exception.UserNotFoundException;
import com.model.User;
import com.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

public interface UsersService {

    /**
     * получение списка юзеров
     * @param userRepository репозиторий юзеров
     * @return список юзеров
     */
    List<UserDto> getAllUsersService(UserRepository userRepository);

    /**
     * Создание юзера
     * @param user юзер
     * @param userRepository репозиторий юзеров
     * @return новый юзер
     * @throws InterruptedException - прервалось что-то?
     */
    User createNoteService(User user, UserRepository userRepository) throws InterruptedException;

    /**
     * удаление юзера
     * @param userId айди юзера
     * @param userRepository репозиторий юзеров
     * @return обновленная таблица
     * @throws UserNotFoundException юзер не найден
     * @throws InterruptedException - прервалось что-то?
     */
    ResponseEntity deleteUserService(Integer userId, UserRepository userRepository) throws UserNotFoundException,
            InterruptedException;

    /**
     * изменить юзера
     * @param userId айди юзера
     * @param userDetails измененные данные
     * @param userRepository репозиторий юзеров
     * @param passwordEncoder декодер пароля
     * @return обносленный юзер
     * @throws UserNotFoundException юзер не найден
     * @throws InterruptedException  - прервалось что-то?
     */
    User updateNoteService(Integer userId, User userDetails, UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder)
            throws UserNotFoundException, InterruptedException;
}
