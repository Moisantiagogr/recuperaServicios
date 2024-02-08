package mx.edu.utez.recupera.controller;

import jakarta.validation.Valid;
import mx.edu.utez.recupera.config.ApiResponse;
import mx.edu.utez.recupera.model.User;
import mx.edu.utez.recupera.model.UserRepository;
import mx.edu.utez.recupera.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/User")
@CrossOrigin(origins = {"*"})
public class UserController {

   private final UserService service;

   private  final UserRepository repository;

    public UserController(UserService service, UserRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllUser(){return service.getAll();}

    @PostMapping("/")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto){
        return service.save(
                userDto.toEntity()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return service.delete(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarBook(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {

        Optional<User> foundUser = repository.findById(id);
        if (foundUser.isEmpty()) {

            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, true, "UserNotFound"), HttpStatus.NOT_FOUND);
        }

        userDto.setId(id);


        return service.actualizar(id, userDto.toEntity());
    }


}
