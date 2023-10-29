package com.system.LMS.controller;


import com.system.LMS.dto.AuthorDto;
import com.system.LMS.entity.Author;
import com.system.LMS.service.AuthorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/authors")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthorController {

    @Autowired
    private AuthorService authorService;


    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        Author createdAuthor = authorService.addAuthor(author);
        return ResponseEntity.ok(createdAuthor);
    }

    @GetMapping
    public ResponseEntity<List<AuthorDto>> getAllAuthors() {
        List<AuthorDto> authors = authorService.getAllAuthors();
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        Optional<Author> authorOptional = authorService.getAuthorById(id);
        if (authorOptional.isPresent()) {
            return ResponseEntity.ok(authorOptional.get());
        } else {
            throw new EntityNotFoundException("Author not found with Id: "+ id);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @RequestBody Author updatedAuthor) {
        Author author = authorService.updateAuthor(id, updatedAuthor);
        if (author != null) {
            return ResponseEntity.ok(author);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

}
