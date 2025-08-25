package desafio.infrastructure.messaging.message;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.io.Serializable;

@RegisterForReflection
public record BrandMessage(
        Long codigo,
        String nome
) implements Serializable {
}