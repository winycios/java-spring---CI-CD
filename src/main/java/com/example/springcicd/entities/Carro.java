package com.example.springcicd.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Classe que abstrai um carro
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_carro")
public class Carro implements Serializable {

    /**
     * ID do veiculo
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_carro_generator")
    @SequenceGenerator(name = "tb_carro_generator", sequenceName = "tb_carro_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    /**
     * Modelo ou nome do veiculo
     */
    @NotBlank(message = "modelo is mandatory")
    private String modelo;

    /**
     * Valor do veiculo
     */
    @NotNull(message = "valor is mandatory")
    private Double valor;

    /**
     * Marca que aquele carro pertence
     */
    // muitos carros é de somente uma marca
    @ManyToOne
    @JoinColumn(name = "marca_id")
    @NotNull(message = "Marca is mandatory")
    private Marca marca;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Carro other = (Carro) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
