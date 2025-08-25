package desafio.presentation.controller.response;

import desafio.application.usecase.vehicle.output.VehicleOutput;

import java.util.List;

public record VehicleResponse(
        List<VehicleResponseItem> veiculos
) {
    public static VehicleResponse from(VehicleOutput output) {
        return new VehicleResponse(output.vehicles()
                .stream()
                .map(VehicleResponseItem::from)
                .toList());
    }
}