package mx.edu.utez.recupera.controller;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.recupera.model.Person;

@Setter
@Getter
@NoArgsConstructor
public class PersonDto {
    private Long id;

    @NotEmpty
    private String nombre;

    @NotEmpty

    private String ape1;


    private String ape2;


    private String direccion;


    private String contacto;

    public Person toEntity(){return  new Person(nombre,ape1,ape2,direccion,contacto);}
}
