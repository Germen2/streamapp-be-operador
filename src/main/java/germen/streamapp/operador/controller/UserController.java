package germen.streamapp.operador.controller;

import germen.streamapp.operador.DTO.SignUpDTO;
import germen.streamapp.operador.model.Operation;
import germen.streamapp.operador.model.User;
import germen.streamapp.operador.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // ✅ GET /users → obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

//    // ✅ GET /users/{id} → obtener un usuario por id
//    @GetMapping("/{id}")
//    public ResponseEntity<User> getUser(@PathVariable Long id) {
//        Optional<User> user = userService.get(id);
//        return ResponseEntity.of(user); // devuelve 200 OK o 404 Not Found
//    }

//    @PostMapping("/user")
//    public ResponseEntity<User> getUserOperations(HttpServletRequest request){
//        String authHeader = request.getHeader("Authorization");
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//
//        String token = authHeader.substring(7);
//
//        Optional<User> optionalUser = userService.getUserWithToken(token);
//
//        return optionalUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
//
//    }

    @PostMapping("/user")
    public User getUser(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }

        String token = authHeader.substring(7);

        return userService.getUserWithToken(token);

    }

    // ✅ POST /users → crear un nuevo usuario
//    @PostMapping
//    public ResponseEntity<User> addUser(@RequestBody SignUpDTO signUpDTO) {
//        User saved = userService.saveUser(user);
//        return ResponseEntity.status(201).body(saved); // 201 Created
//    }

    // ✅ DELETE /users/{id} → eliminar un usuario por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // ✅ PUT /users/{id} → actualizar un usuario existente
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updated = userService.updateUser(id, user);
        return ResponseEntity.ok(updated);
    }

}
