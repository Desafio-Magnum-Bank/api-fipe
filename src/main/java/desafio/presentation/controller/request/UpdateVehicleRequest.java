package desafio.presentation.controller.request;

public record UpdateVehicleRequest(
        String nome,
        String observacao
) {
}