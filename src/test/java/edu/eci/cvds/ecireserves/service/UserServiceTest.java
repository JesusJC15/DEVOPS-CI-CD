package edu.eci.cvds.ecireserves.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.eci.cvds.ecireserves.dto.UserDTO;
import edu.eci.cvds.ecireserves.enums.UserRole;
import edu.eci.cvds.ecireserves.exception.EciReservesException;
import edu.eci.cvds.ecireserves.model.User;
import edu.eci.cvds.ecireserves.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        user = new User("1", "John Doe", "johndoe@example.com", "password123", UserRole.ESTUDIANTE);
        userDTO = new UserDTO("1", "John Doe", "johndoe@example.com", "password123", UserRole.ESTUDIANTE);
    }

    @Test
    void shouldReturnAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> users = userService.getAllUsers();

        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
        assertEquals(user.getId(), users.get(0).getId());
    }

    @Test
    void shouldReturnUserWhenIdExists() throws EciReservesException {
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        User foundUser = userService.getUserById("1");

        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());
    }

    @Test
    void shouldThrowExceptionWhenUserIdDoesNotExist() {
        when(userRepository.findById("2")).thenReturn(Optional.empty());

        EciReservesException exception = assertThrows(EciReservesException.class, () -> userService.getUserById("2"));

        assertEquals(EciReservesException.USER_NOT_FOUND, exception.getMessage());
    }

    @Test
    void shouldReturnUsersWhenNameExists() {
        when(userRepository.findByName("John Doe")).thenReturn(List.of(user));

        List<User> users = userService.getUsersByName("John Doe");

        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
        assertEquals("John Doe", users.get(0).getName());
    }

    @Test
    void shouldReturnUsersWhenRoleExists() {
        when(userRepository.findByRol(UserRole.ESTUDIANTE)).thenReturn(List.of(user));

        List<User> users = userService.getUsersByRol(UserRole.ESTUDIANTE);

        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
        assertEquals(UserRole.ESTUDIANTE, users.get(0).getRol());
    }

    @Test
    void shouldCreateUserWhenIdAndEmailAreUnique() throws EciReservesException {
        when(userRepository.findById("1")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("johndoe@example.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(userDTO);

        assertNotNull(createdUser);
        assertEquals(user.getEmail(), createdUser.getEmail());
    }

    @Test
    void shouldThrowExceptionWhenUserIdAlreadyExists() {
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        EciReservesException exception = assertThrows(EciReservesException.class, () -> userService.createUser(userDTO));

        assertEquals(EciReservesException.USER_ALREADY_EXISTS, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        when(userRepository.findById("2")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("johndoe@example.com")).thenReturn(Optional.of(user));

        UserDTO newUserDTO = new UserDTO("2", "Jane Doe", "johndoe@example.com", "password456", UserRole.PROFESOR);

        EciReservesException exception = assertThrows(EciReservesException.class, () -> userService.createUser(newUserDTO));

        assertEquals(EciReservesException.USER_EMAIL_ALREADY_EXISTS, exception.getMessage());
    }

    @Test
    void shouldUpdateUserWhenDataIsValid() throws EciReservesException {
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(userRepository.findByEmail("newemail@example.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO updatedUserDTO = new UserDTO("1", "John Doe", "newemail@example.com", "newpassword", UserRole.ESTUDIANTE);
        User updatedUser = userService.updateUser("1", updatedUserDTO);

        assertNotNull(updatedUser);
        assertEquals("newemail@example.com", updatedUser.getEmail());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingToExistingEmail() {
        User anotherUser = new User("2", "Jane Doe", "janedoe@example.com", "password123", UserRole.PROFESOR);
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(userRepository.findByEmail("janedoe@example.com")).thenReturn(Optional.of(anotherUser));

        UserDTO updatedUserDTO = new UserDTO("1", "John Doe", "janedoe@example.com", "newpassword", UserRole.ESTUDIANTE);

        EciReservesException exception = assertThrows(EciReservesException.class, () -> userService.updateUser("1", updatedUserDTO));

        assertEquals(EciReservesException.USER_EMAIL_ALREADY_EXISTS, exception.getMessage());
    }

    @Test
    void shouldDeleteUserWhenIdExists() throws EciReservesException {
        when(userRepository.existsById("1")).thenReturn(true);

        userService.deleteUser("1");

        verify(userRepository).deleteById("1"); 
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentUser() {
        when(userRepository.existsById("1")).thenReturn(false);

        EciReservesException exception = assertThrows(EciReservesException.class, () -> userService.deleteUser("1"));

        assertEquals(EciReservesException.USER_NOT_FOUND, exception.getMessage());
    }

}
