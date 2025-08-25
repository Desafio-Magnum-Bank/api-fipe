package desafio.config;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "API Desafio Veículos",
                version = "1.0.0",
                description = "API para gerenciamento de veículos e marcas",
                contact = @Contact(
                        name = "Equipe de Desenvolvimento",
                        url = "https://suaempresa.com",
                        email = "dev@suaempresa.com"
                ),
                license = @License(
                        name = "MIT",
                        url = "https://opensource.org/licenses/MIT"
                )
        ),
        servers = {
                @Server(url = "/", description = "Servidor local")
        }
)
public class SwaggerConfig {
}
