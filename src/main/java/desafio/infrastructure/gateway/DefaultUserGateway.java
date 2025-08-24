package desafio.infrastructure.gateway;

import desafio.domain.gateway.UserGateway;
import desafio.domain.model.User;
import desafio.infrastructure.entity.UserEntity;
import desafio.infrastructure.gateway.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DefaultUserGateway implements UserGateway {

    private final UserRepository userRepository;

    public DefaultUserGateway(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean validateUserName(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User findByUserFor(String username) {
        final UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity != null) {
            return userEntity.toDomain();
        }
        return null;
    }

    @Override
    @Transactional
    public User createUser(User user) {

        final UserEntity entity = UserEntity.from(user);
        User domain = null;
        try {
            domain = userRepository
                    .save(entity)
                    .toDomain();
        } catch (Exception e) {
            System.err.println("Error creating user: " + e.getMessage());
        }
        return domain;
    }
}