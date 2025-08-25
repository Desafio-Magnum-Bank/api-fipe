package desafio.presentation.controller.response;

import desafio.application.usecase.brand.output.BrandOutput;

import java.util.List;

public record BrandResponse(
        List<BrandResponseItem> marcas
) {

    public static BrandResponse from(BrandOutput brandOutput) {
        return new BrandResponse(brandOutput.brands()
                .stream()
                .map(BrandResponseItem::from)
                .toList());
    }
}