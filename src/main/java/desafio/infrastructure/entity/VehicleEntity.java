package desafio.infrastructure.entity;

import desafio.domain.model.Brand;
import desafio.domain.model.Vehicle;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "VEICULO")
@Getter
@Setter
public class VehicleEntity {

    @Id
    @Column(name = "CODIGO_VEICULO", nullable = false, unique = true)
    private Long id;

    @Column(name = "NOME", nullable = false)
    private String name;

    @Column(name = "OBSERVACAO")
    private String note;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CODIGO_MARCA", nullable = false)
    private BrandEntity brand;

    public VehicleEntity() {
    }

    public VehicleEntity(Long id, String name, String note, BrandEntity brand) {
        this.id = id;
        this.name = name;
        this.note = note;
        this.brand = brand;
    }

    public static VehicleEntity from(Long id, String name, String note, BrandEntity brand) {
        VehicleEntity v = new VehicleEntity(id, name, note, brand);
        v.setBrand(brand);
        return v;
    }

    public Vehicle toDomain() {
        return new Vehicle(
                this.id,
                this.name,
                this.note,
                this.brand != null ? new Brand(this.brand.getCode(), this.brand.getName()) : null
        );
    }

}
