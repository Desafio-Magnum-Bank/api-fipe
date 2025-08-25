package desafio.domain.model;

public class Vehicle {
    private Long code;
    private String name;
    private String note;
    private Brand brand;

    public Vehicle() {
    }

    public Vehicle(Long code, String name, String note, Brand brand) {
        validate(code, name);

        this.code = code;
        this.name = name;
        this.note = note;
        this.brand = brand;
    }

    public static Vehicle create(Long code, String name, String note, Brand brand) {
        return new Vehicle(code, name, note, brand);
    }

    private void validate(Long code, String name) {
        if (code == null || code <= 0) {
            throw new IllegalArgumentException("Code must be a positive number");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
    }

    public Long getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Brand getBrand() {
        return brand;
    }
}