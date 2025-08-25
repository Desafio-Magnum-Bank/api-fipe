package desafio.infrastructure.messaging;

import desafio.infrastructure.messaging.message.BrandMessage;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import com.fasterxml.jackson.databind.ObjectMapper;

@ApplicationScoped
public class BrandProducer {

    @Channel("brand-queue")
    Emitter<String> emitter;

    private final ObjectMapper mapper = new ObjectMapper();

    public void send(BrandMessage brand) {
        try {
            String json = mapper.writeValueAsString(brand);
            emitter.send(json)
                    .thenAccept(result -> Log.info("Enviado com sucesso: " + json))
                    .exceptionally(throwable -> {
                        Log.error("Erro no envio: " + throwable.getMessage());
                        return null;
                    });
        } catch (Exception e) {
            Log.error("Erro ao converter para JSON: " + e.getMessage());
        }
    }
}