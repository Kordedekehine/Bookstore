package com.prunnytest.bookstore.service;

import com.prunnytest.bookstore.dtos.BookDto;
import com.prunnytest.bookstore.dtos.BookResponseDto;
import com.prunnytest.bookstore.exception.AlreadyExistsException;
import com.prunnytest.bookstore.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {

    BookResponseDto saveBook(BookDto bookDto) throws AlreadyExistsException, NotFoundException;

    BookResponseDto updateBook(Long id, BookDto bookDto);

    List<BookResponseDto> listAllBooks();

    BookResponseDto getBookByTitle(String title) throws NotFoundException;

    void deleteBook(Long id);



}
