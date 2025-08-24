package desafio.domain.model;

import java.io.Serializable;
import java.util.Objects;

public class Brand implements Serializable {
    private static final long serialVersionUID = 1L; 
    private Long codigo;
    private String nome;

    public Brand() {
    }

    public Brand(Long codigo, String nome) {
        isValid(codigo, nome);
        this.codigo = codigo;
        this.nome = nome;
    }

    private void isValid(Long codigo, String nome) {
        if (codigo == null || codigo <= 0) {
            throw new IllegalArgumentException("Código não pode ser nulo ou vazio");
        }
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
        }
    }

    // Getters e Setters
    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // equals, hashCode e toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Brand brand = (Brand) o;
        return Objects.equals(codigo, brand.codigo) &&
                Objects.equals(nome, brand.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, nome);
    }

    @Override
    public String toString() {
        return "Brand{" +
                "codigo='" + codigo + '\'' +
                ", nome='" + nome + '\'' +
                '}';
    }
}