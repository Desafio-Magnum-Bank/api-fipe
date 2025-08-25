package desafio.infrastructure.gateway.client;

import desafio.infrastructure.gateway.client.response.BrandResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;


@RegisterRestClient(configKey = "fipe-api")
public interface FipeApiClient {
    @GET
    @Path("/carros/marcas")
    List<BrandResponse> getBrands();
}
