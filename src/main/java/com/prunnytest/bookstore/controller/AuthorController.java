package com.prunnytest.bookstore.controller;

import com.prunnytest.bookstore.dtos.AuthorDto;
import com.prunnytest.bookstore.dtos.AuthorResponseDto;
import com.prunnytest.bookstore.exception.AlreadyExistsException;
import com.prunnytest.bookstore.exception.NotFoundException;
import com.prunnytest.bookstore.service.AuthorService;
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

@Tag(name = "Author", description = "Author set of APIs")
@RestController
@RequestMapping("/api/v1/author")
public class AuthorController {

    @Autowired
    private AuthorService authorService;


    @Operation(summary = "Register the Author")
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> registerAuthor(@RequestBody AuthorDto authorDto) throws AlreadyExistsException {

        AuthorResponseDto saveAuthor = authorService.saveAuthor(authorDto);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("statusCode", HttpStatus.CREATED.value());
        response.put("message", AUTHOR_CREATED_SUCCESS);
        response.put("data", saveAuthor);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }


    @Operation(summary = "Retrieve the Author and written books using Author's ID")
    @GetMapping("/{id}")
    ResponseEntity<Map<String, Object>> getAuthorById(@PathVariable("id") Long id)  throws NotFoundException {

        AuthorResponseDto author = authorService.getAuthorById(id);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("statusCode", HttpStatus.OK.value());
        response.put("message", AUTHOR_RETRIEVED_SUCCESS);
        response.put("data", author);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update the Author details using Author's ID")
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateAuthorDetails(@PathVariable("id") Long id, @RequestBody AuthorDto authorDto) throws NotFoundException {
        AuthorResponseDto updateAuthor = authorService.updateAuthor(id, authorDto);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("statusCode", HttpStatus.OK.value());
        response.put("message", AUTHORS_UPDATED_SUCCESS);
        response.put("data", updateAuthor);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete the Author using Author's ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteAuthor(@PathVariable("id") Long id) throws NotFoundException {

        authorService.deleteAuthor(id);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("statusCode", HttpStatus.NO_CONTENT.value());
        response.put("message", AUTHORS_DELETED_SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);

    }

    @Operation(summary = "List all Author's and books associated with them")
    @GetMapping("/listAuthors")
    public ResponseEntity<Map<String, Object>> getAllAuthors() {
        List<AuthorResponseDto> author = authorService.listAllAuthors();
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("statusCode", HttpStatus.OK.value());
        response.put("message", AUTHOR_RETRIEVED_SUCCESS);
        response.put("data", author);
        return ResponseEntity.ok(response);
    }

}
