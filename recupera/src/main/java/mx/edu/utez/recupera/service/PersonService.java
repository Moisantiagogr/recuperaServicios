package mx.edu.utez.recupera.service;

import mx.edu.utez.recupera.config.ApiResponse;
import mx.edu.utez.recupera.model.Person;
import mx.edu.utez.recupera.model.PersonRepository;
import mx.edu.utez.recupera.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional
public class PersonService {

    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> getAll(){
        return  new ResponseEntity<>(new ApiResponse(repository.findAll(), HttpStatus.OK),HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<ApiResponse> save(Person person){
        person = repository.saveAndFlush(person);


        return new ResponseEntity<>(new ApiResponse(person, HttpStatus.OK), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> actualizar(Long id,Person personData) {
        Optional<Person> foundPerson= repository.findById(id);
        if (foundPerson.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, true, "PersonNotFound"), HttpStatus.NOT_FOUND);
        }
        Person person = foundPerson.get();

        person.setNombre(personData.getNombre());
        person.setApe1(personData.getApe1());
        person.setApe2(personData.getApe2());
        person.setDireccion(personData.getDireccion());
        person.setContacto(personData.getContacto());

        person = repository.saveAndFlush(person);

        return new ResponseEntity<>(new ApiResponse(person, HttpStatus.OK), HttpStatus.OK);

    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> delete(Long id) {
        Optional<Person> foundPerson = repository.findById(id);
        if (foundPerson.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, true, "PersonNotFound"), HttpStatus.NOT_FOUND);
        }

        Person personDelete = foundPerson.get();


        repository.delete(personDelete);

        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, false, "PersonDeletedSuccessfully"), HttpStatus.OK);
    }
}
