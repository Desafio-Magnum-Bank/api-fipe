package desafio.presentation.controller.response;

import desafio.domain.model.Vehicle;

public record VehicleResponseItem(
        Long codigo,
        String nome,
        String observacao,
        BrandResponseItem marca
) {
    public static VehicleResponseItem from(Vehicle vehicle) {
        return new VehicleResponseItem(
                vehicle.getCode(),
                vehicle.getName(),
                vehicle.getNote(),
                BrandResponseItem.from(vehicle.getBrand())
        );
    }
}