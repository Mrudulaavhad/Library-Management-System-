package service;

import com.system.LMS.LmsApplication;
import com.system.LMS.dto.AuthorDto;
import com.system.LMS.entity.Author;
import com.system.LMS.entity.Book;
import com.system.LMS.repository.AuthorRepository;
import com.system.LMS.repository.BookRepository;
import com.system.LMS.service.AuthorService;
import jakarta.persistence.EntityNotFoundException;
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
public class AuthorServiceTest {
    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authorService = new AuthorService(authorRepository, bookRepository);
    }

    @Test
    public void testAddAuthor() {
        Author authorToAdd = new Author();
        authorToAdd.setId(1L);
        authorToAdd.setAuthorName("Test Author");

        when(authorRepository.save(authorToAdd)).thenReturn(authorToAdd);

        Author savedAuthor = authorService.addAuthor(authorToAdd);

        assertNotNull(savedAuthor);
        assertEquals(authorToAdd.getId(), savedAuthor.getId());
        assertEquals(authorToAdd.getAuthorName(), savedAuthor.getAuthorName());
    }

    @Test
    public void testGetAllAuthors() {
        List<Author> authors = new ArrayList<>();

        when(authorRepository.findAll()).thenReturn(authors);

        List<AuthorDto> authorDtos = authorService.getAllAuthors();

        assertNotNull(authorDtos);

    }


    @Test
    public void testGetAuthorById() {
        Long authorId = 1L;
        Author author = new Author();
        author.setId(authorId);
        author.setAuthorName("Test Author");

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));

        Optional<Author> foundAuthor = authorService.getAuthorById(authorId);

        assertTrue(foundAuthor.isPresent());
        assertEquals(authorId, foundAuthor.get().getId());
        assertEquals("Test Author", foundAuthor.get().getAuthorName());
    }

    @Test
    public void testDeleteAuthor(){
        Long authorId = 1L;
        when(authorRepository.existsById(authorId)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> authorService.deleteAuthor(authorId));
        verify(authorRepository, times(1)).existsById(authorId);
    }

    @Test
    public void testConvertToAuthorDto() {
        Author author = new Author();
        author.setId(1L);
        author.setAuthorName("Test Author");

        List<Book> books = new ArrayList<>();
        Book book1 = new Book();
        book1.setTitle("Book 1");
        books.add(book1);
        when(bookRepository.findByAuthors(author)).thenReturn(books);

        AuthorDto authorDto = authorService.convertToAuthorDto(author);

        assertNotNull(authorDto);
        assertEquals(author.getId(), authorDto.getId());
        assertEquals(1, authorDto.getBooks().size());
        assertEquals(author.getAuthorName(), authorDto.getAuthorName());
        assertEquals("Book 1", authorDto.getBooks().get(0));
    }


}
