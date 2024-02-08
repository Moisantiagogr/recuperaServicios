package mx.edu.utez.recupera.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 80, nullable = false)
    private String username;

    @Column
    private String password;

    @Column(nullable = false)
    private boolean estado;


    public User(String username, boolean estado) {
      this.username=username;
      this.estado=estado;
    }
}
