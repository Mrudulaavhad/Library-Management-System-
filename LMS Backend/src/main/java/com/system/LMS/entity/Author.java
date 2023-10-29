package com.system.LMS.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "authors")
@Data
@AllArgsConstructor
@NoArgsConstructor


public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String authorName;

    @ManyToMany(mappedBy = "authors")
    private Set<Book> books = new HashSet<>();


}
