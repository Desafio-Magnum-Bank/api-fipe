package desafio.domain.model;

import java.util.List;
import java.util.Objects;

public class Brand {
    private Long code;
    private String name;
    private List<Vehicle> vehicles;

    public Brand() {
    }

    public Brand(Long code, String name, List<Vehicle> vehicles) {
        isValid(code, name);
        this.code = code;
        this.name = name;
        this.vehicles = vehicles;
    }

    public Brand(Long code, String name) {
        isValid(code, name);
        this.code = code;
        this.name = name;
    }

    public static Brand create(Long code, String name, List<Vehicle> vehicles) {
        return new Brand(code, name, vehicles);
    }

    private void isValid(Long code, String name) {

        if (code == null || code <= 0) {
            throw new IllegalArgumentException("Código não pode ser nulo ou vazio");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
        }
    }

    public Long getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    // equals, hashCode e toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Brand brand = (Brand) o;
        return Objects.equals(code, brand.code) &&
                Objects.equals(name, brand.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name);
    }

    @Override
    public String toString() {
        return "Brand{" +
                "code=" + code +
                ", name='" + name + '\'' +
                ", vehicles=" + vehicles +
                '}';
    }
}