package desafio.infrastructure.gateway.repository;

import desafio.infrastructure.entity.VehicleEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class VehicleRepository {

    @PersistenceContext
    EntityManager em;

    public List<VehicleEntity> findByBrand(String brand) {
        return em.createQuery(
                        "SELECT v FROM VehicleEntity v " +
                                "WHERE v.brand.name = :brand " +
                                "ORDER BY v.id",
                        VehicleEntity.class
                )
                .setParameter("brand", brand)
                .getResultList();
    }


    public VehicleEntity findByCode(Long code) {
        return em.createQuery("SELECT v FROM VehicleEntity v WHERE v.id = :code", VehicleEntity.class)
                .setParameter("code", code)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    public void updateVehicle(VehicleEntity entity) {
        em.merge(entity);
    }
}