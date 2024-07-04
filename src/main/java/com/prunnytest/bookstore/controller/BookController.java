package com.prunnytest.bookstore.controller;

import com.prunnytest.bookstore.dtos.AuthorDto;
import com.prunnytest.bookstore.dtos.AuthorResponseDto;
import com.prunnytest.bookstore.dtos.BookDto;
import com.prunnytest.bookstore.dtos.BookResponseDto;
import com.prunnytest.bookstore.exception.AlreadyExistsException;
import com.prunnytest.bookstore.exception.NotFoundException;
import com.prunnytest.bookstore.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.prunnytest.bookstore.util.Constants.*;

@Tag(name = "Book", description = "Book set of APIs")
@RestController
@RequestMapping("/api/v1/book")
public class BookController {

    @Autowired
    private BookService bookService;


    @Operation(summary = "Register book using the Author and Genre reference ID's")
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> registerBook(@RequestBody BookDto bookDto) throws AlreadyExistsException, NotFoundException {

        BookResponseDto saveBook = bookService.saveBook(bookDto);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("statusCode", HttpStatus.CREATED.value());
        response.put("message", BOOK_CREATED_SUCCESS);
        response.put("data", saveBook);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @Operation(summary = "Retrieve the Book using Book Title - Author and Genre of the book also comes as a response")
    @GetMapping("/{title}")
    ResponseEntity<Map<String, Object>> getBookByTitle(@PathVariable("title") String title)  throws NotFoundException {

        BookResponseDto book = bookService.getBookByTitle(title);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("statusCode", HttpStatus.OK.value());
        response.put("message", BOOK_RETRIEVED_SUCCESS);
        response.put("data", book);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update the Book using the book ID")
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateBookDetails(@PathVariable("id") Long id, @RequestBody BookDto bookDto) {
        BookResponseDto updateBook = bookService.updateBook(id, bookDto);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("statusCode", HttpStatus.OK.value());
        response.put("message", BOOK_UPDATED_SUCCESS);
        response.put("data", updateBook);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Delete the Book using the book ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteBooks(@PathVariable("id") Long id) {

        bookService.deleteBook(id);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("statusCode", HttpStatus.NO_CONTENT.value());
        response.put("message", BOOK_DELETED_SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);

    }

    @Operation(summary = "List all the books in catalogue with authors and genre details")
    @GetMapping("/listBooks")
    public ResponseEntity<Map<String, Object>> getAllBooks() {
        List<BookResponseDto> bookList = bookService.listAllBooks();

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("statusCode", HttpStatus.OK.value());
        response.put("message", BOOK_RETRIEVED_SUCCESS);
        response.put("data", bookList);
        return ResponseEntity.ok(response);
    }



}
