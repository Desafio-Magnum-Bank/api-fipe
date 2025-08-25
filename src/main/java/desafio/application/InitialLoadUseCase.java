package desafio.application;

import desafio.domain.gateway.BrandGateway;
import desafio.domain.model.Brand;
import desafio.infrastructure.mapper.BrandMapper;
import desafio.infrastructure.messaging.BrandProducer;
import io.quarkus.cache.CacheInvalidate;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class InitialLoadUseCase extends VoidUseCaseNoInput {

    private final BrandGateway brandGateway;
    private final BrandProducer brandProducer;

    @Inject
    public InitialLoadUseCase(BrandGateway brandGateway, BrandProducer brandProducer) {
        this.brandGateway = brandGateway;
        this.brandProducer = brandProducer;
    }

    @Override
    @CacheInvalidate(cacheName = "brands-cache")
    public void execute() {
        List<Brand> brandList = brandGateway.fetchBrands();
        for (Brand brand : brandList) {
            brandProducer.send(BrandMapper.toMessage(brand));
        }
    }
}