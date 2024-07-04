package com.prunnytest.bookstore.service.Impl;

import com.prunnytest.bookstore.dtos.AuthorResponseDto;
import com.prunnytest.bookstore.dtos.BookDto;
import com.prunnytest.bookstore.dtos.BookResponseDto;
import com.prunnytest.bookstore.dtos.GenreResponseDto;
import com.prunnytest.bookstore.exception.AlreadyExistsException;
import com.prunnytest.bookstore.exception.NotFoundException;
import com.prunnytest.bookstore.model.Author;
import com.prunnytest.bookstore.model.Book;
import com.prunnytest.bookstore.model.Genre;
import com.prunnytest.bookstore.repository.AuthorRepository;
import com.prunnytest.bookstore.repository.BookRepository;
import com.prunnytest.bookstore.repository.GenreRepository;
import com.prunnytest.bookstore.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    ModelMapper modelMapper = new ModelMapper();


    @Override
    public BookResponseDto saveBook(BookDto bookDto) throws AlreadyExistsException, NotFoundException {

        Optional<Book> optionalBook = bookRepository.findBookByTitle(bookDto.getTitle());
        Optional<Author> optionalAuthor = authorRepository.findById(bookDto.getAuthorId());
        Optional<Genre> optionalGenre = genreRepository.findById(bookDto.getGenreId());

        if (optionalBook.isPresent()){
            throw new AlreadyExistsException("Book already exists");
        }

        if (optionalAuthor.isEmpty()){
            throw new NotFoundException("Author not found");
        }

        if (optionalGenre.isEmpty()){
            throw new NotFoundException("Genre not found");
        }

        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setDescription(bookDto.getDescription());
        book.setAuthor(optionalAuthor.get());
        book.setGenre(optionalGenre.get());

        bookRepository.save(book);

       return convertToBookResponseDto(book);

    }

    private BookResponseDto convertToBookResponseDto(Book book) {
        BookResponseDto bookResponseDto = modelMapper.map(book, BookResponseDto.class);
        bookResponseDto.setAuthorResponseDto(modelMapper.map(book.getAuthor(), AuthorResponseDto.class));
        bookResponseDto.setGenreResponseDto(modelMapper.map(book.getGenre(), GenreResponseDto.class));
        return bookResponseDto;
    }

    @Override
    public BookResponseDto updateBook(Long id, BookDto bookDto) {

        Optional<Book> optionalBook = bookRepository.findById(id);
        Optional<Author> optionalAuthor = authorRepository.findById(bookDto.getAuthorId());
        Optional<Genre> optionalGenre = genreRepository.findById(bookDto.getGenreId());

        if (optionalBook.isEmpty()){
            throw new RuntimeException("Book not found");
        }

        if (optionalAuthor.isEmpty()){
            throw new RuntimeException("Author not found");
        }

        if (optionalGenre.isEmpty()){
            throw new RuntimeException("Genre not found");
        }

        Book book = optionalBook.get();
        book.setTitle(bookDto.getTitle());
        book.setDescription(bookDto.getDescription());
        book.setAuthor(optionalAuthor.get());
        book.setGenre(optionalGenre.get());

        bookRepository.save(book);

        return convertToBookResponseDto(book);
    }

    @Override
    public List<BookResponseDto> listAllBooks() {

        List<Book> books = (List<Book>) bookRepository.findAll();

        return books.stream()
                .map(this::convertToBookResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookResponseDto getBookByTitle(String title) throws NotFoundException {

        Book book = bookRepository.findBookByTitle(title)
                .orElseThrow(() -> new NotFoundException("Book not found"));

        return convertToBookResponseDto(book);

    }

    @Override
    public void deleteBook(Long id) {

        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isEmpty()){
            throw new RuntimeException("Book not found");
        }

        Book book = optionalBook.get();

        bookRepository.delete(book);

    }

}
