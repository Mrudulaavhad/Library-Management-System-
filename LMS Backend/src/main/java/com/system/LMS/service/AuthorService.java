package com.system.LMS.service;

import com.system.LMS.dto.AuthorDto;
import com.system.LMS.entity.Author;
import com.system.LMS.entity.Book;
import com.system.LMS.repository.AuthorRepository;
import com.system.LMS.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    @Autowired
    public  AuthorRepository authorRepository;


    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Autowired
    private BookRepository bookRepository;

    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }

    public List<AuthorDto> getAllAuthors() {

        List<Author> authors = authorRepository.findAll();
        return authors.stream()
                .map(this::convertToAuthorDto)
                .collect(Collectors.toList());
    }

    public AuthorDto convertToAuthorDto(Author author) {
        AuthorDto dto = new AuthorDto();
        dto.setId(author.getId());
        dto.setAuthorName(author.getAuthorName());
        List<String> bookTitles = bookRepository.findByAuthors(author).stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());

        dto.setBooks(bookTitles);
        return dto;
    }

    public  Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    public Author updateAuthor(Long id, Author updatedAuthor) {
        Optional<Author> existingAuthor = authorRepository.findById(id);
        if (existingAuthor.isPresent()) {
            Author authorToUpdate = existingAuthor.get();
            authorToUpdate.setAuthorName(updatedAuthor.getAuthorName());
            return authorRepository.save(authorToUpdate);
        }
        return null;
    }

    public boolean deleteAuthor(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new EntityNotFoundException("Author not found with ID: " + id);
        }
        authorRepository.deleteById(id);
        return true;
    }

    }



