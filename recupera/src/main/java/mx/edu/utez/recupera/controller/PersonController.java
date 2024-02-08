package mx.edu.utez.recupera.controller;

import jakarta.validation.Valid;
import mx.edu.utez.recupera.config.ApiResponse;
import mx.edu.utez.recupera.model.Person;
import mx.edu.utez.recupera.model.PersonRepository;
import mx.edu.utez.recupera.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/Person")
@CrossOrigin(origins = {"*"})
public class PersonController {
    private  final PersonService service;
    private  final PersonRepository repository;

    public PersonController(PersonService service, PersonRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllUser(){return service.getAll();}

    @PostMapping("/")
    public ResponseEntity<?> createPerson(@Valid @RequestBody PersonDto personDto){
        return service.save(
                personDto.toEntity()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePerson(@PathVariable Long id) {
        return service.delete(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPerson(@PathVariable Long id, @Valid @RequestBody PersonDto personDto) {

        Optional<Person> foundPerson = repository.findById(id);
        if (foundPerson.isEmpty()) {

            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, true, "UserNotFound"), HttpStatus.NOT_FOUND);
        }

        personDto.setId(id);


        return service.actualizar(id, personDto.toEntity());
    }


}
