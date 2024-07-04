package com.prunnytest.bookstore.dtos;

import lombok.Data;

@Data
public class BookResponseDto {

    private Long id;

    private String title;

    private String description;

    private AuthorResponseDto authorResponseDto;

    private GenreResponseDto genreResponseDto;
}
