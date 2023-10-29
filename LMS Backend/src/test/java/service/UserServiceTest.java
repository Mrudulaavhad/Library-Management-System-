package service;

import com.system.LMS.LmsApplication;
import com.system.LMS.dto.UserDto;
import com.system.LMS.entity.User;
import com.system.LMS.repository.BookRepository;
import com.system.LMS.repository.UserRepository;
import com.system.LMS.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest(classes = LmsApplication.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;


    @BeforeEach
    public void setup(){
       MockitoAnnotations.initMocks(this);
   }

   @Test
    public void testAddUser(){
       User user = new User();
       user.setId(1L);
       user.setName("John Doe");

       when(userRepository.save(any(User.class))).thenReturn(user);

       User addedUser = userService.addUser(user);

       assertNotNull(addedUser);
       assertEquals(user.getId(), addedUser.getId());
       assertEquals(user.getName(), addedUser.getName());

       verify(userRepository, times(1)).save(any(User.class));
   }

    @Test
    public void testGetAllUsers() {
        User user1 = new User();
        user1.setId(1L);
        user1.setName("John Doe");

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Jane Smith");

        List<User> userList = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(userList, result);

        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetUserById() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setName("John Doe");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(userId);

        assertTrue(result.isPresent());
        assertEquals(userId, result.get().getId());
        assertEquals(user.getName(), result.get().getName());

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testGetAllUsersWithIssuedBooks() {
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        List<UserDto> result = userService.getAllUsersWithIssuedBooks();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(user.getName(), result.get(0).getName());
        assertNotNull(result);

        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateUser() {
        Long userId = 1L;

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setName("John Doe");
        existingUser.setPhoneNumber("1234567890");

        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setName("Jane Smith");
        updatedUser.setPhoneNumber("9876543210");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUser(userId, updatedUser);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals(updatedUser.getName(), result.getName());
        assertEquals(updatedUser.getPhoneNumber(), result.getPhoneNumber());

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testDeleteUser() {
        Long userId = 1L;

        when(userRepository.existsById(userId)).thenReturn(true);

        assertDoesNotThrow(() -> userService.deleteUser(userId));

        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

}
