package desafio.application.usecase.user.output;

public record LoginOutput(
        String id,
        String name,
        String username,
        String token
) {
    public static LoginOutput from(String id, String name, String username, String token) {
        return new LoginOutput(id, name, username, token);
    }
}