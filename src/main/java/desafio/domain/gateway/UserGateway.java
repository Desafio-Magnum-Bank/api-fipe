package desafio.domain.gateway;

import desafio.domain.model.User;

public interface UserGateway {
    boolean validateUserName(String username);

    User findByUserFor(String username);

    User createUser(User user);
}