package com.repository;

import com.model.Book;
import com.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List <Book> findBooksByUser (User user);

    @Query(value = "Select distinct on (name) * from books where user_id = :userId", nativeQuery = true)
    List <Book> uniqueBooksByUser(@Param("userId") Integer userId);
}
