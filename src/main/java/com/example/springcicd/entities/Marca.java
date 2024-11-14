package com.example.springcicd.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe que abstrai uma concessionária de veiculos
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_marca")
@SequenceGenerator(name = "tb_marca_id_seq", sequenceName = "tb_marca_id_seq", allocationSize = 1)
public class Marca implements Serializable {

    /**
     * ID da marca
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_marca_id_seq")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    /**
     * Nome da marca
     */
    @NotBlank(message = "Name is mandatory")
    private String name;

    /**
     * Lista de todos os carros criados pela marca
     */
    // uma marca tem muitos carros
    // Metodo não recomendavel
    @OneToMany(mappedBy = "marca", fetch = FetchType.EAGER)
    @Column(name = "carros")
    private List<Carro> carros = new ArrayList<Carro>();

    public Marca(Long id, String name) {
        this.id = id;
        this.name = name;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id.hashCode();
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
        Marca other = (Marca) obj;
        return Objects.equals(id, other.id);
    }
}
