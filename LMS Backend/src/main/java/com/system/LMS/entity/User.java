package com.system.LMS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phoneNumber;

    private LocalDate issuedOnDate;

    private LocalDate issuedTillDate;

//    @ManyToMany(mappedBy = "users")
   // private Set<Book> issuedBooks = new HashSet<>();     //for issuing book

//    @ManyToMany(mappedBy = "users")                          //for fetching username
//    private Set<Book> books = new HashSet<>();


//    public Set<Book> getIssuedBooks() {
//        return issuedBooks;
//    }
//
//    public void setIssuedBooks(Set<Book> issuedBooks) {
//        this.issuedBooks = issuedBooks;
//    }




}
