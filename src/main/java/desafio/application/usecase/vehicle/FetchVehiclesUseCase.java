package desafio.application.usecase.vehicle;

import desafio.application.UseCase;
import desafio.application.usecase.vehicle.output.VehicleOutput;
import desafio.domain.gateway.VehicleGateway;
import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FetchVehiclesUseCase extends UseCase<String, VehicleOutput> {

    private final VehicleGateway vehicleGateway;

    public FetchVehiclesUseCase(VehicleGateway vehicleGateway) {
        this.vehicleGateway = vehicleGateway;
    }

    @Override
    @CacheResult(cacheName = "vehicles-cache")
    public VehicleOutput execute(String brand) {
        validate(brand);
        return VehicleOutput.from(vehicleGateway.findByBrand(brand));
    }

    private void validate(String brand) {
        if (brand == null || brand.isBlank()) {
            throw new IllegalArgumentException("Brand cannot be null or empty");
        }
    }
}