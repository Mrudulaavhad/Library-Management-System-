package com.system.LMS.service;

import com.system.LMS.dto.UserDto;
import com.system.LMS.entity.Book;
import com.system.LMS.entity.User;
import com.system.LMS.repository.BookRepository;
import com.system.LMS.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    public UserService(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Autowired
    private BookRepository bookRepository;


    @Autowired
    private UserRepository userRepository;

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }


    public User updateUser(Long id, User updatedUser) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User userToUpdate = existingUser.get();
            userToUpdate.setName(updatedUser.getName());
            userToUpdate.setPhoneNumber(updatedUser.getPhoneNumber());
            return userRepository.save(userToUpdate);
        }
        return null;
    }
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }
    public List<UserDto> getAllUsersWithIssuedBooks() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToUserDtoWithIssuedBooks)
                .collect(Collectors.toList());
    }
    private UserDto convertToUserDtoWithIssuedBooks(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());

        List<String> bookTitles = bookRepository.findByUsers(user).stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());

        dto.setBooks(bookTitles);

        return dto;

    }
}



