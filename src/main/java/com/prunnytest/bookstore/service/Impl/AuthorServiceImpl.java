package com.prunnytest.bookstore.service.Impl;

import com.prunnytest.bookstore.dtos.AuthorDto;
import com.prunnytest.bookstore.dtos.AuthorResponseDto;
import com.prunnytest.bookstore.dtos.BookDto;
import com.prunnytest.bookstore.dtos.BookResponseDto;
import com.prunnytest.bookstore.exception.AlreadyExistsException;
import com.prunnytest.bookstore.exception.NotFoundException;
import com.prunnytest.bookstore.model.Author;
import com.prunnytest.bookstore.repository.AuthorRepository;
import com.prunnytest.bookstore.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    ModelMapper modelMapper = new ModelMapper();

    @Override
    public AuthorResponseDto saveAuthor(AuthorDto authorDto) throws AlreadyExistsException {

        Optional<Author> optionalAuthor = authorRepository.findByName(authorDto.getName());

        if (optionalAuthor.isPresent()) {
            throw new AlreadyExistsException("USER ALREADY EXISTS");
        }

        Author author = new Author();
        author.setName(authorDto.getName());
        author.setBio(authorDto.getBio());

        authorRepository.save(author);
        log.info("Author successfully saved to database");

        return modelMapper.map(author, AuthorResponseDto.class);
    }

    @Override
    public AuthorResponseDto updateAuthor(Long id, AuthorDto authorDto) throws NotFoundException {

        Optional<Author> optionalAuthor = authorRepository.findById(id);

        if (optionalAuthor.isEmpty()) {
            throw new RuntimeException("USER DOES NOT EXIST");
        }

        Author author = optionalAuthor.get();
        author.setName(authorDto.getName());
        author.setBio(authorDto.getBio());

        authorRepository.save(author);
        log.info("Author successfully updated");

        return modelMapper.map(author, AuthorResponseDto.class);
    }

    public List<AuthorResponseDto> listAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream()
                .map(this::convertToAuthorResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorResponseDto getAuthorById(Long id) throws NotFoundException {

        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author not found"));

        return convertToAuthorResponseDto(author);
       // return new modelMapper.map(author,AuthorResponseDto.class);
    }

    private AuthorResponseDto convertToAuthorResponseDto(Author author) {
        AuthorResponseDto authorResponseDto = modelMapper.map(author, AuthorResponseDto.class);
        authorResponseDto.setBooks(
                author.getBooks().stream()
                        .map(book -> modelMapper.map(book, BookDto.class))
                        .collect(Collectors.toList())
        );
        return authorResponseDto;
    }


    @Override
    public void deleteAuthor(Long id) throws NotFoundException {
        Optional<Author> optionalAuthor = authorRepository.findById(id);

        if (optionalAuthor.isEmpty()) {
            throw new NotFoundException("USER DOES NOT EXIST");
        }

        Author author = optionalAuthor.get();

        authorRepository.delete(author);
    }


}
