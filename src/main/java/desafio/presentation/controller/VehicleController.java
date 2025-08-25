package desafio.presentation.controller;

import desafio.application.usecase.vehicle.FetchVehiclesUseCase;
import desafio.application.usecase.vehicle.UpdateVehicleUseCase;
import desafio.application.usecase.vehicle.input.UpdateVehicleInput;
import desafio.application.usecase.vehicle.output.VehicleOutput;
import desafio.presentation.controller.request.UpdateVehicleRequest;
import desafio.presentation.controller.response.VehicleResponse;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@ApplicationScoped
@Path("/veiculos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VehicleController {

    private final FetchVehiclesUseCase fetchVehiclesUseCase;
    private final UpdateVehicleUseCase updateVehicleUseCase;

    public VehicleController(FetchVehiclesUseCase fetchVehiclesUseCase, UpdateVehicleUseCase updateVehicleUseCase) {
        this.fetchVehiclesUseCase = fetchVehiclesUseCase;
        this.updateVehicleUseCase = updateVehicleUseCase;
    }

    @GET
    @RolesAllowed("USER")
    @Operation(summary = "Buscar veículos por marca", description = "Retorna todos os veículos de uma determinada marca")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Lista de veículos retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = VehicleResponse.class))
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "Marca inválida"
            ),
            @APIResponse(
                    responseCode = "401",
                    description = "Usuário não autorizado"
            ),
            @APIResponse(
                    responseCode = "204",
                    description = "Nenhum veículo encontrado para a marca informada"
            )
    })
    public Response findBrandsAndDetails(@QueryParam("marca") String brand) {
        if (brand == null || brand.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Parâmetro 'marca' é obrigatório\"}")
                    .build();
        }

        VehicleOutput output = fetchVehiclesUseCase.execute(brand);
        VehicleResponse response = VehicleResponse.from(output);
        return Response.ok(response).build();
    }

    @PUT
    @RolesAllowed("USER")
    @Path("/{code}")
    @Operation(summary = "Atualizar veículo", description = "Atualiza o nome e/ou observação de um veículo pelo código")
    @APIResponses({
            @APIResponse(
                    responseCode = "204",
                    description = "Veículo atualizado com sucesso"
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "Dados de atualização inválidos"
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Veículo não encontrado"
            ),
            @APIResponse(
                    responseCode = "401",
                    description = "Usuário não autorizado"
            )
    })
    public Response updateVehicle(UpdateVehicleRequest request, @PathParam("code") Long code) {
        final UpdateVehicleInput input = new UpdateVehicleInput(
                code,
                request.nome(),
                request.observacao()
        );
        updateVehicleUseCase.execute(input);
        return Response.noContent().build();
    }
}
