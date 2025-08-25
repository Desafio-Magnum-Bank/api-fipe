package desafio.infrastructure.gateway;

import desafio.domain.gateway.BrandGateway;
import desafio.domain.model.Brand;
import desafio.infrastructure.gateway.client.FipeApiClient;
import desafio.infrastructure.gateway.repository.BrandRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@ApplicationScoped
public class DefaultBrandGateway implements BrandGateway {

    private final FipeApiClient client;
    private final BrandRepository brandRepository;

    @Inject
    public DefaultBrandGateway(@RestClient FipeApiClient client, BrandRepository brandRepository) {
        this.client = client;
        this.brandRepository = brandRepository;
    }

    @Override
    public List<Brand> fetchBrands() {
        return client.getBrands()
                .stream()
                .map(
                        b -> new Brand(
                                Long.valueOf(b.codigo()),
                                b.nome()
                        )
                ).toList();
    }

    @Override
    public List<Brand> findAll() {
        return brandRepository.findAll()
                .stream()
                .map(b -> new Brand(
                        b.getCode(),
                        b.getName(),
                        b.getVehicles().stream()
                                .map(v -> v.toDomain())
                                .toList()
                ))
                .toList();
    }
}