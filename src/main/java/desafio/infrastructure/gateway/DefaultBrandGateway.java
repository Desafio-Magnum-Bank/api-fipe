package desafio.infrastructure.gateway;

import desafio.domain.gateway.BrandGateway;
import desafio.domain.model.Brand;
import desafio.infrastructure.gateway.client.FipeApiClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@ApplicationScoped
public class DefaultBrandGateway implements BrandGateway {

    private final FipeApiClient client;

    @Inject
    public DefaultBrandGateway(@RestClient FipeApiClient client) {
        this.client = client;
    }

    @Override
    public List<Brand> fetchBrands() {
        return client.getBrands();
    }
}