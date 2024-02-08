package mx.edu.utez.recupera.controller;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.recupera.model.User;

@Setter
@Getter
@NoArgsConstructor
public class UserDto {

    private Long id;
    @NotEmpty
    private String username;


    private String password;


    private boolean estado;

    public User toEntity(){return  new User(username,estado);}
}
