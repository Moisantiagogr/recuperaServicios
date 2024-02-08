package mx.edu.utez.recupera.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "people")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 80,nullable = false)
    private String nombre;

    @Column(length = 80,nullable = false)
    private String ape1;

    @Column(length = 80,nullable = false)
    private String ape2;

    @Column(length = 120,nullable = false)
    private String direccion;

    @Column(length = 80,nullable = false)
    private String contacto;


    public Person(String nombre, String ape1, String ape2, String direccion, String contacto) {
        this.nombre=nombre;
        this.ape1=ape1;
        this.ape2=ape2;
        this.direccion=direccion;
        this.contacto=contacto;
    }
}
