package desafio.application.usecase.user.output;

import desafio.domain.model.User;

public record CreateUserOutput(
        String id,
        String name,
        String username
) {
    public static CreateUserOutput from(User user) {
        return new CreateUserOutput(
                user.getId(),
                user.getName(),
                user.getUsername()
        );
    }
}