package desafio.presentation.controller;

import desafio.application.usecase.vehicle.FetchVehiclesUseCase;
import desafio.application.usecase.vehicle.UpdateVehicleUseCase;
import desafio.application.usecase.vehicle.input.UpdateVehicleInput;
import desafio.application.usecase.vehicle.output.VehicleOutput;
import desafio.presentation.controller.request.UpdateVehicleRequest;
import desafio.presentation.controller.response.VehicleResponse;
import io.quarkus.cache.CacheInvalidate;
import io.quarkus.cache.CacheKey;
import io.quarkus.cache.CacheResult;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
    public Response findBrandsAndDetails(@QueryParam("marca") String brand) {
        VehicleOutput output = fetchVehiclesUseCase.execute(brand);
        VehicleResponse response = VehicleResponse.from(output);
        return Response.ok(response).build();
    }


    @PUT
    @RolesAllowed("USER")
    @Path("/{code}")
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
