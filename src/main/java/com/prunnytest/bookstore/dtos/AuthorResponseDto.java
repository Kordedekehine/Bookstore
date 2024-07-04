package com.prunnytest.bookstore.dtos;

import lombok.Data;

import java.util.List;

@Data
public class AuthorResponseDto {

    private Long id;

    private String name;

    private String Bio;

    private List<BookDto> books;

}
