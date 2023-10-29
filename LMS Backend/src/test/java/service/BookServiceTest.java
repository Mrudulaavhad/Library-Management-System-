package service;

import com.system.LMS.LmsApplication;
import com.system.LMS.entity.Book;
import com.system.LMS.repository.BookRepository;
import com.system.LMS.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = LmsApplication.class)

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddBook() {

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book 1");
        when(bookRepository.save(book)).thenReturn(book);  //repo behaviour of mock
        Book savedBook = bookService.addBook(book);
        assertNotNull(savedBook);
        assertEquals(1L, savedBook.getId());
        assertEquals("Book 1", savedBook.getTitle());
    }

    @Test
    public void testGetAllBooks() {
        List<Book> books = new ArrayList<>();

        when(bookRepository.findAll()).thenReturn(books);

        List<Book> retrievedBooks = bookService.getAllBooks();

        assertNotNull(retrievedBooks);

    }

    @Test
    public void testGetBookById() {
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        book.setTitle("Test Book");

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        Optional<Book> foundBook = bookService.getBookById(bookId);
        assertNotNull(foundBook.get().getId());
        assertTrue(foundBook.isPresent());
        assertEquals(bookId, foundBook.get().getId());
        assertEquals("Test Book", foundBook.get().getTitle());
    }

    @Test
    public void testDeleteBook() {
        Long bookId = 1L;
        Book bookToDelete = new Book();
        bookToDelete.setId(bookId);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(bookToDelete));
        bookService.deleteBook(bookId);
        verify(bookRepository, times(1)).deleteById(bookId);
    }



}









