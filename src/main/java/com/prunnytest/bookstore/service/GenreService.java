package com.prunnytest.bookstore.service;

import com.prunnytest.bookstore.dtos.GenreDto;
import com.prunnytest.bookstore.dtos.GenreResponseDto;
import com.prunnytest.bookstore.exception.AlreadyExistsException;
import com.prunnytest.bookstore.exception.NotFoundException;

import java.util.List;

public interface GenreService {


    GenreResponseDto saveGenre(GenreDto genreDto) throws AlreadyExistsException;

    GenreResponseDto updateGenre(Long id, GenreDto genreDto) throws NotFoundException;

    List<GenreResponseDto> listAllGenres();

    GenreResponseDto getGenreByName(String name) throws NotFoundException;

    void deleteGenre(Long id) throws NotFoundException;
}
