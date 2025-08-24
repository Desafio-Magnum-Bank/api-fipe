package desafio.infrastructure.gateway.repository;

import desafio.infrastructure.entity.UserEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class UserRepository {
    @PersistenceContext
    EntityManager em;

    public boolean existsByUsername(String username) {
        Long count = em.createQuery(
                        "SELECT COUNT(u) FROM UserEntity u WHERE u.username = :username", Long.class)
                .setParameter("username", username)
                .getSingleResult();
        return count > 0;
    }

    public UserEntity save(UserEntity entity) {
        em.persist(entity);
        return entity;
    }

    public UserEntity findByUsername(String username) {
        return em.createQuery(
                        "SELECT u FROM UserEntity u WHERE u.username = :username",
                        UserEntity.class)
                .setParameter("username", username)
                .getSingleResult();
    }
}