package desafio.presentation.controller.response;

public record UserResponse(
        String id,
        String name,
        String username
) {
    public static UserResponse from(String id, String name, String username) {
        return new UserResponse(id, name, username);
    }
}
