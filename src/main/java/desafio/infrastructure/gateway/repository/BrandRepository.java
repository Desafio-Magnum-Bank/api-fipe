package desafio.infrastructure.gateway.repository;

import desafio.infrastructure.entity.BrandEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@ApplicationScoped
public class BrandRepository {
    @PersistenceContext
    EntityManager em;

    public List<BrandEntity> findAll() {
        return em.createQuery("SELECT b FROM BrandEntity b", BrandEntity.class).getResultList();
    }
}