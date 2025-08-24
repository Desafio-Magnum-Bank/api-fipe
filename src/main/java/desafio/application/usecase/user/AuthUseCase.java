package desafio.application.usecase.user;

import desafio.application.UseCase;
import desafio.application.usecase.user.input.LoginInput;
import desafio.application.usecase.user.output.LoginOutput;
import desafio.domain.gateway.UserGateway;
import desafio.domain.model.User;
import desafio.infrastructure.securiry.JwtProvider;
import io.quarkus.security.UnauthorizedException;
import jakarta.enterprise.context.ApplicationScoped;
import org.mindrot.jbcrypt.BCrypt;

@ApplicationScoped
public class AuthUseCase extends UseCase<LoginInput, LoginOutput> {

    private final UserGateway userGateway;
    private final JwtProvider jwtProvider;

    public AuthUseCase(UserGateway userGateway, JwtProvider jwtProvider) {
        this.userGateway = userGateway;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public LoginOutput execute(LoginInput input) {
        validate(input);

        final User user = userGateway.findByUserFor(input.username());
        if (user == null) {
            throw new UnauthorizedException("Senha ou usuário inválidos");
        }
        verifyPassword(user, input.password());

        return LoginOutput.from(
                user.getId(),
                user.getName(),
                user.getUsername(),
                jwtProvider.generateToken(user.getUsername())
        );
    }

    private void verifyPassword(User user, String inputPassword) {
        if (!BCrypt.checkpw(inputPassword, user.getPassword())) {
            throw new UnauthorizedException("Senha ou usuário inválidos");
        }
    }

    public void validate(LoginInput input) {
        if (input == null || input.username() == null || input.password() == null) {
            throw new IllegalArgumentException("O input de login não pode ser nulo ou conter campos vazios");
        }
        if (input.username().isEmpty() || input.password().isEmpty()) {
            throw new IllegalArgumentException("O nome de usuário e a senha não podem ser vazios");
        }
    }
}