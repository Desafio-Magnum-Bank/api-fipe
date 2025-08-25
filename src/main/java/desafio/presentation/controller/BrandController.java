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
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

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
    @Operation(summary = "Iniciar busca de marcas", description = "Dispara a inicialização da busca de marcas")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Busca iniciada com sucesso",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @APIResponse(
                    responseCode = "401",
                    description = "Usuário não autorizado"
            )
    })
    public Response initialLoad() {
        initialLoadUseCase.execute();
        return Response.ok().entity("{\"message\": \"Busca de marcas iniciada\"}").build();
    }

    @GET
    @RolesAllowed("USER")
    @Operation(summary = "Listar marcas", description = "Retorna todas as marcas cadastradas")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Lista de marcas retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = BrandResponse.class))
            ),
            @APIResponse(
                    responseCode = "401",
                    description = "Usuário não autorizado"
            )
    })
    public Response findBrands() {
        final BrandOutput output = findBrandsUseCase.execute();
        final BrandResponse response = BrandResponse.from(output);
        return Response.ok(response).build();
    }
}
