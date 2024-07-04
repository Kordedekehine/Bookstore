package com.prunnytest.bookstore.repository;


import com.prunnytest.bookstore.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

    Optional<Book> findBookByTitle(String title);

    Page<Book> findByAuthor_NameContainingIgnoreCaseOrAuthor(
            String name, String genreName, Pageable pageable);
}

