package desafio.domain.gateway;

import desafio.domain.model.Brand;

import java.util.List;

public interface BrandGateway {

    List<Brand> fetchBrands();
}