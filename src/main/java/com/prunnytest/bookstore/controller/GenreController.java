package com.prunnytest.bookstore.controller;

import com.prunnytest.bookstore.dtos.AuthorDto;
import com.prunnytest.bookstore.dtos.AuthorResponseDto;
import com.prunnytest.bookstore.dtos.GenreDto;
import com.prunnytest.bookstore.dtos.GenreResponseDto;
import com.prunnytest.bookstore.exception.AlreadyExistsException;
import com.prunnytest.bookstore.exception.NotFoundException;
import com.prunnytest.bookstore.service.GenreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.prunnytest.bookstore.util.Constants.*;

@Tag(name = "Genre", description = "Genres set of APIS")
@RestController
@RequestMapping("/api/v1/genre")
public class GenreController {


    @Autowired
    private GenreService genreService;

    @Operation(summary = "Register the Genres")
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createGenre(@RequestBody GenreDto genreDto) throws AlreadyExistsException {

        GenreResponseDto saveGenre = genreService.saveGenre(genreDto);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("statusCode", HttpStatus.CREATED.value());
        response.put("message", GENRE_CREATED_SUCCESS);
        response.put("data", saveGenre);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Retrieve Genre by Title")
    @GetMapping("/{title}")
    ResponseEntity<Map<String, Object>> getGenreByTitle(@PathVariable("title") String title) throws NotFoundException {

        GenreResponseDto genre = genreService.getGenreByName(title);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("statusCode", HttpStatus.OK.value());
        response.put("message", GENRE_RETRIEVED_SUCCESS);
        response.put("data", genre);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update Genres using Genre's ID")
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateGenreDetails(@PathVariable("id") Long id, @RequestBody GenreDto genreDto) throws NotFoundException {
        GenreResponseDto updateGenre = genreService.updateGenre(id, genreDto);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("statusCode", HttpStatus.OK.value());
        response.put("message", GENRE_UPDATED_SUCCESS);
        response.put("data", updateGenre);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "List All Genres")
    @GetMapping("/listGenres")
    public ResponseEntity<Map<String, Object>> getAllGenres() {
        List<GenreResponseDto> genre = genreService.listAllGenres();
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("statusCode", HttpStatus.OK.value());
        response.put("message", GENRE_RETRIEVED_SUCCESS);
        response.put("data", genre);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete Genre using Genre's ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteGenre(@PathVariable("id") Long id) throws NotFoundException {

        genreService.deleteGenre(id);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("statusCode", HttpStatus.NO_CONTENT.value());
        response.put("message", GENRE_DELETED_SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);

    }

}
