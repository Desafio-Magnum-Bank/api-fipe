package desafio.presentation.controller;

import desafio.application.InitialLoadUseCase;
import desafio.application.usecase.brand.FindBrandsUseCase;
import desafio.application.usecase.brand.output.BrandOutput;
import desafio.presentation.controller.response.BrandResponse;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Path("/marcas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BrandController {
    private final InitialLoadUseCase initialLoadUseCase;
    private final FindBrandsUseCase findBrandsUseCase;

    public BrandController(InitialLoadUseCase initialLoadUseCase, FindBrandsUseCase findBrandsUseCase) {
        this.initialLoadUseCase = initialLoadUseCase;
        this.findBrandsUseCase = findBrandsUseCase;
    }

    @GET
    @RolesAllowed("USER")
    @Path("/init")
    public Response initialLoad() {
        initialLoadUseCase.execute();
        return Response.ok().entity("{\"message\": \"Busca de marcas iniciada\"}").build();
    }


    @GET
    @RolesAllowed("USER")
    public Response findBrands() {
        final BrandOutput output = findBrandsUseCase.execute();
        final BrandResponse response = BrandResponse.from(output);
        return Response.ok(response).build();
    }
}