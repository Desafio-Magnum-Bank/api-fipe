package desafio.presentation.controller;

import desafio.application.InitialLoadUseCase;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Path("/brands")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BrandController {
    private final InitialLoadUseCase initialLoadUseCase;

    public BrandController(InitialLoadUseCase initialLoadUseCase) {
        this.initialLoadUseCase = initialLoadUseCase;
    }

    @GET
    @RolesAllowed("USER")
    @Path("/fetch")
    public Response fetchBrands() {
        initialLoadUseCase.execute();
        return Response.ok().entity("{\"message\": \"Busca de marcas iniciada\"}").build();
    }
}