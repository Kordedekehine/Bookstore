package com.prunnytest.bookstore.dtos;

import lombok.Data;

@Data
public class BookDto {

    private String title;

    private String description;

    private Long authorId;

    private Long genreId;
}
