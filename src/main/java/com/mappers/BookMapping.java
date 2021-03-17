package com.mappers;

import com.Dtos.BookDto;
import com.model.Book;

public class BookMapping {


    public static BookDto map(Book book){
        BookDto bookDto = new BookDto();
        bookDto.setName(book.getName());
        bookDto.setHolderName(book.getUser().getName());
        bookDto.setYear(book.getYear());
        return bookDto;
    }
}
