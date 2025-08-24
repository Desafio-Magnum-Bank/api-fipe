package desafio.application.usecase.user.input;

public record CreateUserInput(
        String username,
        String name,
        String password
) {
    public static CreateUserInput from(
            String username,
            String name,
            String password
    ) {
        return new CreateUserInput(username, name, password);
    }
}