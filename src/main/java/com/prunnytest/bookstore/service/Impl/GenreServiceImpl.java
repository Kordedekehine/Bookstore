package com.prunnytest.bookstore.service.Impl;

import com.prunnytest.bookstore.dtos.GenreDto;
import com.prunnytest.bookstore.dtos.GenreResponseDto;
import com.prunnytest.bookstore.exception.AlreadyExistsException;
import com.prunnytest.bookstore.exception.NotFoundException;
import com.prunnytest.bookstore.model.Genre;
import com.prunnytest.bookstore.repository.GenreRepository;
import com.prunnytest.bookstore.service.GenreService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class GenreServiceImpl implements GenreService {

    @Autowired
    private GenreRepository genreRepository;

    ModelMapper modelMapper = new ModelMapper();

    @Override
    public GenreResponseDto saveGenre(GenreDto genreDto) throws AlreadyExistsException {

        Optional<Genre> optionalGenre = genreRepository.findGenreByName(genreDto.getName());

        if (optionalGenre.isPresent()){
            throw new AlreadyExistsException("Genre already exists");
        }

        Genre genre = new Genre();
        genre.setName(genreDto.getName());
        genre.setDescription(genreDto.getDescription());

        genreRepository.save(genre);
        log.info("Genre successfully saved in the database");

        return new ModelMapper().map(genre,GenreResponseDto.class);
    }

    @Override
    public GenreResponseDto updateGenre(Long id, GenreDto genreDto) throws NotFoundException {

        Optional<Genre> optionalGenre = genreRepository.findById(id);

        if (optionalGenre.isEmpty()){
            throw new NotFoundException("Genre does not exist");

        }

        Genre genre = optionalGenre.get();
        genre.setName(genreDto.getName());
        genre.setDescription(genreDto.getDescription());

        genreRepository.save(genre);

        return new ModelMapper().map(genre,GenreResponseDto.class);
    }

    @Override
    public List<GenreResponseDto> listAllGenres() {

        List<Genre> genres = (List<Genre>) genreRepository.findAll();
        Type listType = new TypeToken<List<GenreResponseDto>>() {}.getType();

        return new ModelMapper().map(genres, listType);
    }

    @Override
    public GenreResponseDto getGenreByName(String name) throws NotFoundException {

        Genre genre = genreRepository.findGenreByName(name)
                .orElseThrow(() -> new NotFoundException("Genre not found"));

        return new ModelMapper().map(genre,GenreResponseDto.class);
    }

    @Override
    public void deleteGenre(Long id) throws NotFoundException {

        Optional<Genre> optionalGenre = genreRepository.findById(id);

        if (optionalGenre.isEmpty()){
            throw new NotFoundException("Genre does not exist");
        }

        Genre genre = optionalGenre.get();

        genreRepository.delete(genre);

    }
}
