package desafio.infrastructure.mapper;

import desafio.domain.model.Brand;
import desafio.infrastructure.messaging.message.BrandMessage;

public class BrandMapper {

    public static BrandMessage toMessage(Brand brand) {
        return new BrandMessage(brand.getCode(), brand.getName());
    }
}