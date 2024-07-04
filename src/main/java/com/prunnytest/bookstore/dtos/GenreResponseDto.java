package com.prunnytest.bookstore.dtos;

import lombok.Data;

@Data
public class GenreResponseDto {

    private Long id;

    private String name;

    private String description;
}
