package com.system.LMS.controller;

import com.system.LMS.dto.BookDto;
import com.system.LMS.entity.Author;
import com.system.LMS.entity.Book;
import com.system.LMS.service.AuthorService;
import com.system.LMS.service.BookService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
@Slf4j
@Data
@CrossOrigin(origins = "http://localhost:3000")
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {         //creation of new bk takes bk object and returns saved bk
        Book savedBook = bookService.addBook(book);
        return ResponseEntity.status(HttpStatus.OK).body(savedBook);
    }

    @GetMapping("/books-with-users")
    public ResponseEntity<List<BookDto>> getAllBooksWithUsers() {
        List<BookDto> bookDtos = bookService.getAllBooksWithUsers();
        return ResponseEntity.ok(bookDtos);
    }


    @GetMapping("/filter")
    public ResponseEntity<List<Book>> getAllBooks(@RequestParam(value = "authorId") Long authorId) {
        List<Book> books;
        if (authorId != null) {
            // Filtering books by author
            Optional<Author> author = authorService.getAuthorById(authorId);
            if (author.isPresent()) {
                books = bookService.getBooksByAuthor(author.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
           return ResponseEntity.badRequest().build();

        }
        return ResponseEntity.ok(books);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        Book book = bookService.updateBook(id, updatedBook);
        if (book != null) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {                           //? to add custom response
       Optional<Book> bookOptional = bookService.getBookById(id);
        if (bookOptional.isPresent()) {
            bookService.deleteBook(id);
            return ResponseEntity.ok("Book deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
        }

    }




}

