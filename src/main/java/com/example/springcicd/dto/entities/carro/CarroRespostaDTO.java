package com.example.springcicd.dto.entities.carro;

import com.example.springcicd.entities.Carro;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarroRespostaDTO {

    private Long id;
    private String modelo;
    private Double valor;

    public CarroRespostaDTO(Carro carro) {

        this.id = carro.getId();
        this.modelo = carro.getModelo();
        this.valor = carro.getValor();
    }
}
