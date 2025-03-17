package edu.eci.cvds.ecireserves.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.cvds.ecireserves.dto.UserDTO;
import edu.eci.cvds.ecireserves.exception.EciReservesException;
import edu.eci.cvds.ecireserves.model.ApiResponse;
import edu.eci.cvds.ecireserves.model.User;
import edu.eci.cvds.ecireserves.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuarios obtenidos exitosamente", userService.getAllUsers()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable("id") String id) throws EciReservesException {
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuario con id: " + id, userService.getUserById(id)));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<User>>> getUsersByName(@RequestParam String name) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuarios con nombre: " + name , userService.getUsersByName(name)));
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody UserDTO userDTO) throws EciReservesException {
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuario creado", userService.createUser(userDTO)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable("id") String id, @RequestBody UserDTO userDTO) throws EciReservesException {
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuario actualizado", userService.updateUser(id, userDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable("id") String id) throws EciReservesException {
        userService.deleteUser(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuario eliminado", null));
    }
}