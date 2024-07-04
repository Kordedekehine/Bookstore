package com.prunnytest.bookstore.repository;


import com.prunnytest.bookstore.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre,Long> {

   Optional<Genre> findGenreByName(String name);
}
