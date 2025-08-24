package desafio.application.usecase.user;

import desafio.application.UseCase;
import desafio.application.exception.ConflictException;
import desafio.application.usecase.user.input.CreateUserInput;
import desafio.application.usecase.user.output.CreateUserOutput;
import desafio.domain.gateway.UserGateway;
import desafio.domain.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.mindrot.jbcrypt.BCrypt;

@ApplicationScoped
public class CreateUserUseCase extends UseCase<CreateUserInput, CreateUserOutput> {

    private final UserGateway userGateway;

    @Inject
    public CreateUserUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public CreateUserOutput execute(CreateUserInput input) {
        validate(input);

        final boolean userNameExists = userGateway.validateUserName(input.username());
        if (userNameExists) {
            throw new ConflictException("O nome de usuário já existe: " + input.username());
        }

        final String passEncrypted = encryptPassword(input.password());

        final User user = User.create(
                input.name(),
                input.username(),
                passEncrypted
        );

        return CreateUserOutput.from(userGateway.createUser(user));
    }

    private String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private void validate(CreateUserInput input) {
        if (input == null) {
            throw new IllegalArgumentException("O input nao pode ser nulo");
        }
    }
}