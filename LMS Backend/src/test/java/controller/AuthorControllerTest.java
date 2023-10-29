package controller;

import com.system.LMS.controller.AuthorController;
import com.system.LMS.dto.AuthorDto;
import com.system.LMS.entity.Author;
import com.system.LMS.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthorControllerTest {
    @InjectMocks
    private AuthorController authorController;

    @Mock
    private AuthorService authorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateAuthor() {
        Author author = new Author();
        when(authorService.addAuthor(author)).thenReturn(author);
        ResponseEntity<Author> response = authorController.createAuthor(author);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(author, response.getBody());
    }

    @Test
    public void testGetAllAuthors() {
        List<AuthorDto> authors = new ArrayList<>();
        when(authorService.getAllAuthors()).thenReturn(authors);
        ResponseEntity<List<AuthorDto>> response = authorController.getAllAuthors();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authors, response.getBody());
    }

    @Test
    public void testGetAuthorById() {
        Long authorId = 1L;
        Author author = new Author();
        Optional<Author> authorOptional = Optional.of(author);
        when(authorService.getAuthorById(authorId)).thenReturn(authorOptional);
        ResponseEntity<Author> response = authorController.getAuthorById(authorId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(author, response.getBody());

    }

    @Test
    public void testUpdateAuthor() {
        Long authorId = 1L;
        Author updatedAuthor = new Author();
        when(authorService.updateAuthor(authorId, updatedAuthor)).thenReturn(updatedAuthor);
        ResponseEntity<Author> response = authorController.updateAuthor(authorId, updatedAuthor);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedAuthor, response.getBody());
    }

    @Test
    public void testDeleteAuthor() {
        Long authorId = 1L;
        ResponseEntity<?> response = authorController.deleteAuthor(authorId);
        verify(authorService,times(1)).deleteAuthor(authorId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}








