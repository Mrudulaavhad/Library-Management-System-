package com.system.LMS.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "books")
@EntityListeners(AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "title")
    private String title;

    public Book(long l, String s) {
    }


    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private String createdAt;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private String updatedAt;

    @ManyToMany
    @JoinTable(
            name = "book_author",   // Name of the join table
            joinColumns = @JoinColumn(name = "book_id"),   // Column referring to Book entity
            inverseJoinColumns = @JoinColumn(name = "author_id")   // Column referring to Author entity
    )
    public Set<Author> authors = new HashSet<>();

    public Set<User> getUsers() {
        return users;
    }

    @ManyToMany
    @JoinTable(
            name = "book_user",     // Name of the join table
            joinColumns = @JoinColumn(name = "book_id"),   // Column referring to Book entity
            inverseJoinColumns = @JoinColumn(name = "user_id")   // Column referring to User entity
    )
    public  Set<User> users = new HashSet<>();

}
