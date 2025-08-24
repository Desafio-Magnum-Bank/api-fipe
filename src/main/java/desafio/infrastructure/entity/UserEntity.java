package desafio.infrastructure.entity;

import desafio.domain.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "USUARIO")
public class UserEntity {

    @Id
    @Column(name = "ID_USUARIO", nullable = false, unique = true)
    private String id;

    @Column(name = "USER_NAME", nullable = false, unique = true)
    private String username;

    @Column(name = "NOME", nullable = false)
    private String name;

    @Column(name = "SENHA", nullable = false)
    private String password;

    protected UserEntity() {
    }

    public UserEntity(String id, String username, String name, String password) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.password = password;
    }

    public static UserEntity from(User user) {
        return new UserEntity(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getPassword()
        );
    }

    public User toDomain() {
        return new User(
                this.id,
                this.name,
                this.username,
                this.password
        );
    }
}