package desafio.application.usecase.brand;

import desafio.application.VoidUseCase;
import desafio.application.exception.NoContentException;
import desafio.application.usecase.brand.output.BrandOutput;
import desafio.domain.gateway.BrandGateway;
import desafio.domain.model.Brand;
import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class FindBrandsUseCase extends VoidUseCase<BrandOutput> {

    private final BrandGateway brandGateway;

    public FindBrandsUseCase(BrandGateway brandGateway) {
        this.brandGateway = brandGateway;
    }

    @Override
    @CacheResult(cacheName = "brands-cache")
    public BrandOutput execute() {
        final List<Brand> brands = brandGateway.findAll();

        if (brands.isEmpty()) {
            throw new NoContentException();
        }

        return new BrandOutput(brands);
    }
}
