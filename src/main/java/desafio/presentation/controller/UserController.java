package desafio.presentation.controller;

import desafio.application.usecase.user.AuthUseCase;
import desafio.application.usecase.user.CreateUserUseCase;
import desafio.application.usecase.user.input.CreateUserInput;
import desafio.application.usecase.user.output.CreateUserOutput;
import desafio.application.usecase.user.input.LoginInput;
import desafio.application.usecase.user.output.LoginOutput;
import desafio.presentation.controller.request.LoginRequest;
import desafio.presentation.controller.request.UserRequest;
import desafio.presentation.controller.response.UserResponse;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import java.net.URI;

@RequestScoped
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    private final CreateUserUseCase userUseCase;
    private final AuthUseCase authUseCase;

    public UserController(CreateUserUseCase userUseCase, AuthUseCase authUseCase) {
        this.userUseCase = userUseCase;
        this.authUseCase = authUseCase;
    }

    @POST
    @PermitAll
    @Path("/login")
    @Operation(
            summary = "Login de usuário",
            description = "Realiza o login do usuário com username e senha"
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Login realizado com sucesso",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))
            ),
            @APIResponse(
                    responseCode = "401",
                    description = "Usuário ou senha inválidos"
            )
    })
    public Response login(LoginRequest request) {
        final LoginInput input = LoginInput.from(request.username(), request.password());
        final LoginOutput output = authUseCase.execute(input);

        return Response.ok(
                        UserResponse.from(
                                output.id(),
                                output.name(),
                                output.username()
                        ))
                .header("Authorization", "Bearer " + output.token())
                .build();
    }

    @POST
    @Path("/register")
    @PermitAll
    @Operation(
            summary = "Registrar usuário",
            description = "Cria um novo usuário com nome, username e senha"
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Usuário criado com sucesso",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "Erro de validação"
            )
    })
    public Response register(UserRequest request) {
        final CreateUserInput input = CreateUserInput.from(
                request.username(),
                request.name(),
                request.password()
        );

        final CreateUserOutput output = userUseCase.execute(input);

        return Response
                .created(URI.create("/users/" + output.id()))
                .entity(output)
                .build();
    }

}