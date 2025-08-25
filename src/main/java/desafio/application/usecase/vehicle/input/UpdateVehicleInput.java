package desafio.application.usecase.vehicle.input;

public record UpdateVehicleInput(
        Long code,
        String model,
        String note
) {
}
