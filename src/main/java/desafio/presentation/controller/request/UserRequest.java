package desafio.presentation.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserRequest(
        @JsonProperty("username")
        String username,
        @JsonProperty("name")
        String name,
        @JsonProperty("password")
        String password
) {
}