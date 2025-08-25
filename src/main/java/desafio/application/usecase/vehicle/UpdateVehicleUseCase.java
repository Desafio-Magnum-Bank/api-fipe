package desafio.application.usecase.vehicle;

import desafio.application.UseCase;
import desafio.application.usecase.vehicle.input.UpdateVehicleInput;
import desafio.domain.gateway.VehicleGateway;
import desafio.domain.model.Vehicle;
import io.quarkus.cache.Cache;
import io.quarkus.cache.CacheName;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class UpdateVehicleUseCase extends UseCase<UpdateVehicleInput, Optional<Void>> {

    private final VehicleGateway vehicleGateway;
    private final Cache cache;

    public UpdateVehicleUseCase(VehicleGateway vehicleGateway, @CacheName("vehicles-cache") Cache cache) {
        this.vehicleGateway = vehicleGateway;
        this.cache = cache;
    }

    @Override
    public Optional<Void> execute(UpdateVehicleInput input) {
        validate(input);

        final Vehicle domain = vehicleGateway.findByCode(input.code());
        if (domain == null) {
            throw new IllegalArgumentException("Veiculo nao encontrado");
        }

        if (input.note() != null || !input.note().isBlank()) {
            domain.setNote(input.note());
        }
        if (input.model() != null || !input.model().isBlank()) {
            domain.setName(input.model());
        }

        clearCache(domain.getBrand().getName());

        vehicleGateway.updateVehicle(domain);

        return Optional.empty();
    }

    private void clearCache(String brand) {
        cache.invalidate(brand).await().indefinitely();
    }

    private void validate(UpdateVehicleInput input) {
        if (input.code() == null || input.code() <= 0) {
            throw new IllegalArgumentException("Codigo invalido");
        }
    }
}