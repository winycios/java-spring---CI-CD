package com.example.springcicd.dto.entities.carro;

import com.example.springcicd.dto.entities.marca.MarcaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CarroDTO {

    private Long id;
    private String modelo;
    private Double valor;
    private MarcaDTO marca;

    public CarroDTO(Long id, String modelo, Double valor) {
        this.id = id;
        this.modelo = modelo;
        this.valor = valor;
    }
}
