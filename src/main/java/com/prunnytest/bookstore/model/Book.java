package com.prunnytest.bookstore.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "books")
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false,unique = true)
  private String title;

  @Column(nullable = false,unique = true)
  private String description;

  @ManyToOne
  @JoinColumn(name = "author_id")
  private Author author;

  @ManyToOne
  @JoinColumn(name = "genre_id")
  private Genre genre;

}
