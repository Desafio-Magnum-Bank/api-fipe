package desafio.domain.model;

import java.util.UUID;

public class User {
    private String id;
    private String name;
    private String username;
    private String password;

    public User(String id, String name, String username, String password) {
        validateId(id);
        validateName(name);
        validateUsername(username);
        validatePassword(password);

        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }


    public static User create(String name, String username, String password) {
        String id = UUID.randomUUID().toString();
        return new User(id, name, username, password);
    }

    private void validateId(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID não pode ser nulo ou vazio");
        }
    }

    private void validateName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name não pode ser nulo ou vazio");
        }
    }

    private void validateUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username não pode ser nulo ou vazio");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password não pode ser nulo ou vazio");
        }
        if (password.length() < 6) {
            throw new IllegalArgumentException("Password deve ter pelo menos 6 caracteres");
        }
    }

    //TODO: VERIFICAR SE É NECESSÁRIO UM MÉTODO PARA ATUALIZAR A SENHA
    public void updateName(String name) {
        validateName(name);
        this.name = name;
    }

    public void updateUsername(String username) {
        validateUsername(username);
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}