package desafio.domain.gateway;

import desafio.domain.model.Vehicle;

import java.util.List;

public interface VehicleGateway {
    List<Vehicle> findByBrand(String brand);

    Vehicle findByCode(Long code);

    void updateVehicle(Vehicle vehicle);
}