package desafio.infrastructure.gateway;

import desafio.domain.gateway.VehicleGateway;
import desafio.domain.model.Vehicle;
import desafio.infrastructure.entity.BrandEntity;
import desafio.infrastructure.entity.VehicleEntity;
import desafio.infrastructure.gateway.repository.VehicleRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class DefaultVehicleGateway implements VehicleGateway {

    private final VehicleRepository repository;

    public DefaultVehicleGateway(VehicleRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Vehicle> findByBrand(String brand) {
        return repository.findByBrand(brand)
                .stream()
                .map(entity -> Vehicle.create(entity.getId(), entity.getName(), entity.getNote(), entity.getBrand().toDomain()))
                .toList();
    }

    @Override
    @Transactional
    public Vehicle findByCode(Long code) {
        return repository.findByCode(code).toDomain();
    }

    @Override
    @Transactional
    public void updateVehicle(Vehicle vehicle) {
        VehicleEntity entity = VehicleEntity.from(
                vehicle.getCode(),
                vehicle.getName(),
                vehicle.getNote(),
                new BrandEntity(vehicle.getBrand().getCode())
        );
        repository.updateVehicle(entity);
    }
}