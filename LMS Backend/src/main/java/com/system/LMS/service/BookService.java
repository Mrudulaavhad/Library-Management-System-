package com.system.LMS.service;


import com.system.LMS.dto.BookDto;
import com.system.LMS.entity.Author;
import com.system.LMS.entity.Book;
import com.system.LMS.entity.User;
import com.system.LMS.repository.BookRepository;
import com.system.LMS.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public Book addBook(Book book) {

        return bookRepository.save(book);
    }




    public List<Book> getAllBooks() {

        List<Book> books =bookRepository.findAll();
        if (books.isEmpty()){
            throw new EntityNotFoundException("No Book available");
        }
        return books;
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book updateBook(Long id, Book updatedBook) {
        Optional<Book> existingBook = bookRepository.findById(id);
        if (existingBook.isPresent()) {
            updatedBook.setId(id);
            return bookRepository.save(updatedBook);
        }
        return null;
        }


        public void deleteBook(Long id) {
            Optional<Book> book = bookRepository.findById(id);
            if (book.isPresent()) {
                bookRepository.deleteById(id);
            } else {
                throw new EntityNotFoundException("Book not found with ID: " + id);
            }
        }

        public List<Book> getBooksByAuthor(Author author) {

            return bookRepository.findByAuthors(author);
         }

    public List<BookDto> getAllBooksWithUsers() {
        List<Book> books = bookRepository.findAll();
        List<BookDto> bookDtos = new ArrayList<>();

        for (Book book : books) {                                      //for each iteration
            BookDto bookDto = new BookDto();
            bookDto.setId(book.getId());
            bookDto.setTitle(book.getTitle());

            List<String> userNames = book.getUsers().stream()
                    .map(User::getName)
                    .collect(Collectors.toList());
            bookDto.setUserNames(userNames);

            bookDtos.add(bookDto);
        }

        return bookDtos;
    }
}













