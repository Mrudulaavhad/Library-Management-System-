package controller;

import com.system.LMS.controller.UserController;
import com.system.LMS.dto.UserDto;
import com.system.LMS.entity.User;
import com.system.LMS.service.UserService;
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

public class UserControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddUser(){
        User userToAdd = new User();
        userToAdd.setId(1L);
        userToAdd.setName("testuser");

        when(userService.addUser(userToAdd)).thenReturn(userToAdd);

        ResponseEntity<User> response = userController.addUser(userToAdd);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userToAdd, response.getBody());
    }

    @Test
    public void testGetUserById() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setName("testuser");

        when(userService.getUserById(userId)).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userController.getUserById(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testUpdateUser() {
        Long userId = 1L;
        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setName("updateduser");

        when(userService.updateUser(userId, updatedUser)).thenReturn(updatedUser);

        ResponseEntity<User> response = userController.updateUser(userId, updatedUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedUser, response.getBody());
    }

    @Test
    public void testDeleteUser() {
        Long userId = 1L;

        ResponseEntity<Void> response = userController.deleteUser(userId);

        verify(userService, times(1)).deleteUser(userId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testGetAllUsersWithIssuedBooks() {
        List<UserDto> usersWithIssuedBooks = new ArrayList<>();
        when(userService.getAllUsersWithIssuedBooks()).thenReturn(usersWithIssuedBooks);
        ResponseEntity<List<UserDto>> response = userController.getAllUsersWithIssuedBooks();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usersWithIssuedBooks, response.getBody());
    }
}
