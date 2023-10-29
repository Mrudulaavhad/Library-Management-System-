package com.system.LMS.repository;

import com.system.LMS.entity.Author;
import com.system.LMS.entity.Book;
import com.system.LMS.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthors(Author author);
    List<Book> findByUsers(User user);


}


