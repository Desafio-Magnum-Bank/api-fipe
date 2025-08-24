package desafio.application.usecase.user.input;

public record LoginInput(
        String username,
        String password
) {
    public static LoginInput from(String username, String password) {
        return new LoginInput(username, password);
    }
}