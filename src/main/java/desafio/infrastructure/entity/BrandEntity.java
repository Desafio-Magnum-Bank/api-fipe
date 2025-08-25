package desafio.infrastructure.entity;

import desafio.domain.model.Brand;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "MARCA")
public class BrandEntity {

    @Id
    @Column(name = "CODIGO_MARCA", nullable = false, unique = true)
    private Long code;

    @Column(name = "NOME", nullable = false)
    private String name;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<VehicleEntity> vehicles = new ArrayList<>();

    public BrandEntity() {
    }

    public BrandEntity(Long code, String name, List<VehicleEntity> vehicles) {
        this.code = code;
        this.name = name;
        this.vehicles = vehicles;
    }

    public BrandEntity(Long code) {
        this.code = code;
    }

    public static BrandEntity from(Brand brand) {
        final BrandEntity brandEntity = new BrandEntity();
        brandEntity.setCode(brand.getCode());
        brandEntity.setName(brand.getName());

        final List<VehicleEntity> vehicleEntities = brand.getVehicles().stream()
                .map(v -> VehicleEntity.from(v.getCode(), v.getName(), null, brandEntity))
                .toList();

        brandEntity.setVehicles(vehicleEntities);
        return brandEntity;
    }

    public Brand toDomain() {
        return new Brand(this.code, this.name,
                this.vehicles.stream()
                        .map(VehicleEntity::toDomain)
                        .toList());
    }
}