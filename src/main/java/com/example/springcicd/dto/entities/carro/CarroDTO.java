package com.example.springcicd.dto.entities.carro;

import com.example.springcicd.dto.entities.marca.MarcaDTO;
import com.example.springcicd.dto.mapper.MarcaMapper;
import com.example.springcicd.entities.Carro;
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

    public CarroDTO(Carro carro) {

        this.id = carro.getId();
        this.modelo = carro.getModelo();
        this.valor = carro.getValor();
        this.marca = MarcaMapper.converterParaDTO(carro.getMarca());
    }
}
