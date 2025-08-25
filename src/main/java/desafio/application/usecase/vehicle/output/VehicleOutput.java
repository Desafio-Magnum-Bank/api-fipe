package desafio.application.usecase.vehicle.output;

import desafio.domain.model.Vehicle;

import java.io.Serializable;
import java.util.List;

public record VehicleOutput(
        List<Vehicle> vehicles
) implements Serializable {
    public static VehicleOutput from(List<Vehicle> vehicleList) {
        return new VehicleOutput(vehicleList);
    }
}
