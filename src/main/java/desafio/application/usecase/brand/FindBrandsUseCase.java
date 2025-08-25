package desafio.application.usecase.brand;

import desafio.application.UseCase;
import desafio.application.VoidUseCase;
import desafio.application.usecase.brand.output.BrandOutput;
import desafio.domain.gateway.BrandGateway;
import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FindBrandsUseCase extends VoidUseCase<BrandOutput> {

    private final BrandGateway brandGateway;

    public FindBrandsUseCase(BrandGateway brandGateway) {
        this.brandGateway = brandGateway;
    }

    @Override
    @CacheResult(cacheName = "brands-cache")
    public BrandOutput execute() {
        var brands = brandGateway.findAll();
        return new BrandOutput(brands);
    }
}
