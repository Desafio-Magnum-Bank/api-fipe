package desafio.application.usecase.brand.output;

import desafio.domain.model.Brand;
import java.util.List;

public record BrandOutput(List<Brand> brands) {

    public static BrandOutput from(List<Brand> brandList) {
        return new BrandOutput(brandList);
    }
}