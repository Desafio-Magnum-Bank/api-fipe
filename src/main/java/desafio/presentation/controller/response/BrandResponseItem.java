package desafio.presentation.controller.response;

import desafio.domain.model.Brand;

public record BrandResponseItem(
        String nome
) {
    public static BrandResponseItem from(Brand brand) {
        return new BrandResponseItem(brand.getName());
    }
}