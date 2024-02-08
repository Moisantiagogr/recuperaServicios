package mx.edu.utez.recupera.service;

import mx.edu.utez.recupera.config.ApiResponse;
import mx.edu.utez.recupera.model.User;
import mx.edu.utez.recupera.model.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> getAll(){
        return  new ResponseEntity<>(new ApiResponse(repository.findAll(), HttpStatus.OK),HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<ApiResponse> save(User user) {

        String randomPassword = generateRandomPassword();


        user.setPassword(randomPassword);

        user = repository.saveAndFlush(user);


        return new ResponseEntity<>(new ApiResponse(user, HttpStatus.OK), HttpStatus.OK);
    }

    private String generateRandomPassword() {

        String lowerCaseChars = "abcdefghijklmnopqrstuvwxyz";
        String upperCaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        String specialChars = "!@#$%^&*()-_+=";
        String allChars = lowerCaseChars + upperCaseChars + numbers + specialChars;


        int length = 8;

        Random random = new Random();
        StringBuilder password = new StringBuilder(length);
        password.append(getRandomChar(lowerCaseChars, random));
        password.append(getRandomChar(upperCaseChars, random));
        password.append(getRandomChar(numbers, random));
        password.append(getRandomChar(specialChars, random));
        for (int i = 4; i < length; i++) {
            password.append(getRandomChar(allChars, random));
        }


        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(length);
            char temp = password.charAt(i);
            password.setCharAt(i, password.charAt(randomIndex));
            password.setCharAt(randomIndex, temp);
        }

        return password.toString();
    }

    private char getRandomChar(String characterSet, Random random) {
        int randomIndex = random.nextInt(characterSet.length());
        return characterSet.charAt(randomIndex);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> actualizar(Long id,User userData) {
        Optional<User> foundUser = repository.findById(id);
        if (foundUser.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, true, "UserNotFound"), HttpStatus.NOT_FOUND);
        }
        User user = foundUser.get();
        String randomPassword = generateRandomPassword();


        user.setPassword(randomPassword);


        user.setUsername(userData.getUsername());


        user = repository.saveAndFlush(user);

        return new ResponseEntity<>(new ApiResponse(user, HttpStatus.OK), HttpStatus.OK);

    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> delete(Long id) {
        Optional<User> foundBook = repository.findById(id);
        if (foundBook.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, true, "UserNotFound"), HttpStatus.NOT_FOUND);
        }

        User userDelete = foundBook.get();


        repository.delete(userDelete);

        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, false, "UserDeletedSuccessfully"), HttpStatus.OK);
    }


    }
