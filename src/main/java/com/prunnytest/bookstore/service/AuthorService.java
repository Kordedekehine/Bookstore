package com.prunnytest.bookstore.service;

import com.prunnytest.bookstore.dtos.AuthorDto;
import com.prunnytest.bookstore.dtos.AuthorResponseDto;
import com.prunnytest.bookstore.exception.AlreadyExistsException;
import com.prunnytest.bookstore.exception.NotFoundException;

import java.util.List;

public interface AuthorService {

    AuthorResponseDto saveAuthor(AuthorDto authorDto) throws AlreadyExistsException;

    AuthorResponseDto updateAuthor(Long id, AuthorDto authorDto) throws NotFoundException;

    List<AuthorResponseDto> listAllAuthors();

    AuthorResponseDto getAuthorById(Long id) throws NotFoundException;

    void deleteAuthor(Long id) throws NotFoundException;


}
