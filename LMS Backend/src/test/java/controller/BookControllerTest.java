package controller;

import com.system.LMS.controller.BookController;
import com.system.LMS.dto.BookDto;
import com.system.LMS.entity.Author;
import com.system.LMS.entity.Book;
import com.system.LMS.service.AuthorService;
import com.system.LMS.service.BookService;
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
import static org.mockito.Mockito.when;

public class BookControllerTest{
    @InjectMocks
    private BookController bookController;
    @Mock
    private BookService bookService;

    @Mock
    private AuthorService authorService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddBook(){
        Book book = new Book();
        when (bookService.addBook(book)).thenReturn(book);
        ResponseEntity<Book>response = bookController.addBook(book);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(book, response.getBody());
    }

    @Test
    public void testGetAllBooksWithUsers(){
        List<BookDto> books = new ArrayList<>();
        when (bookService.getAllBooksWithUsers()).thenReturn(books);
         ResponseEntity<List<BookDto>> response = bookController.getAllBooksWithUsers();

         assertEquals(HttpStatus.OK, response.getStatusCode());
         assertEquals(books, response.getBody());
    }

    @Test
    public void testGetAllBooks() {
        Long authorId = 1L;
        Author author = new Author();
        author.setId(authorId);

        List<Book> booksByAuthor = new ArrayList<>();
        when(authorService.getAuthorById(authorId)).thenReturn(Optional.of(author));
        when(bookService.getBooksByAuthor(author)).thenReturn(booksByAuthor);

        ResponseEntity<List<Book>> response = bookController. getAllBooks(authorId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(booksByAuthor, response.getBody());
    }

    @Test
    public void testUpdateBook(){
        Long bookId = 1L;
        Book updatedBook = new Book();
        updatedBook.setId(bookId);
        updatedBook.setTitle("Updated Book");

        when(bookService.updateBook(bookId, updatedBook)).thenReturn(updatedBook);

        ResponseEntity<Book> response = bookController.updateBook(bookId, updatedBook);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedBook, response.getBody());
    }

    @Test
    public void testDeleteBook() {
        Long bookId = 1L;
        Optional<Book> bookOptional = Optional.of(new Book());

        when(bookService.getBookById(bookId)).thenReturn(bookOptional);

        ResponseEntity<?> response = bookController.deleteBook(bookId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book deleted successfully", response.getBody());
    }
}