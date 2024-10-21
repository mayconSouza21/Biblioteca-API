package br.ufpb.Biblioteca_API.Service;

import br.ufpb.Biblioteca_API.Dto.UserDTO;
import br.ufpb.Biblioteca_API.Exception.UserNotFoundException;
import br.ufpb.Biblioteca_API.Models.User;
import br.ufpb.Biblioteca_API.Repositories.UserRepository;
import br.ufpb.Biblioteca_API.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setName("User");
        user.setEmail("user@gmail.com");
        user.setAge(30);
        user.setCpf("12345678901");
        user.setAddress("center");
        user.setCity("Sample City");
        user.setUsername("user123");
        user.setPassword("password123");

        userDTO = new UserDTO();
        userDTO.setName("User Test Updated");
        userDTO.setEmail("user.Test.updated@gmail.com");
        userDTO.setAge(31);
        userDTO.setCpf("10987654321");
        userDTO.setAddress("456 Center");
        userDTO.setCity("Updated City");
        userDTO.setUsername("updatedUser123");
        userDTO.setPassword("newPassword123");
    }

    @Test
    void getAllUsers_Success() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        List<User> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(user.getName(), users.get(0).getName());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById_Success() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        User foundUser = userService.getUserById(1L);

        assertNotNull(foundUser);
        assertEquals("User", foundUser.getName());
        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    void getUserById_NotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.getUserById(1L);
        });

        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    void saveUser_Success() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertNotNull(savedUser);
        assertEquals("User", savedUser.getName());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUser_Success() {
        when(userRepository.existsById(anyLong())).thenReturn(true);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updatedUser = userService.updateUser(1L, userDTO);

        assertNotNull(updatedUser);
        assertEquals(user.getName(), updatedUser.getName());
        verify(userRepository, times(1)).existsById(anyLong());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUser_NotFound() {
        when(userRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> {
            userService.updateUser(1L, userDTO);
        });

        verify(userRepository, times(1)).existsById(anyLong());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void deleteUser_Success() {
        when(userRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(userRepository).deleteById(anyLong());

        userService.deleteUser(1L);

        verify(userRepository, times(1)).existsById(anyLong());
        verify(userRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteUser_NotFound() {
        when(userRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> {
            userService.deleteUser(1L);
        });

        verify(userRepository, times(1)).existsById(anyLong());
        verify(userRepository, never()).deleteById(anyLong());
    }
}
