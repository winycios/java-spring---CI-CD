package com.example.springcicd.dto.entities.marca;


import com.example.springcicd.dto.entities.carro.CarroRespostaDTO;
import com.example.springcicd.dto.mapper.CarroMapper;
import com.example.springcicd.entities.Marca;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarcaDTO {

    private Long id;
    private String name;
    private List<CarroRespostaDTO> carros = new ArrayList<CarroRespostaDTO>();

    public MarcaDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public MarcaDTO(Marca marca) {

        this.id = marca.getId();
        this.name = marca.getName();
        this.carros = CarroMapper.converterListaParaDTO(marca.getCarros());
    }
}
