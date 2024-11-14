package com.example.springcicd.dto.mapper;


import com.example.springcicd.dto.entities.carro.CarroDTO;
import com.example.springcicd.dto.entities.carro.CarroRespostaDTO;
import com.example.springcicd.entities.Carro;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CarroMapper {

    private static final ModelMapper mapper = new ModelMapper();

    // converte um DTO para entidade
    public static Carro converterParaEntidade(CarroDTO CarroDTO) {
        return mapper.map(CarroDTO, Carro.class);
    }

    public static Carro converterParaEntidade(CarroRespostaDTO CarroDTO) {
        return mapper.map(CarroDTO, Carro.class);
    }


    // converte carro para RespostaDTO
    public static CarroRespostaDTO converterParaRespostaDTO(Carro Carro) {
        return mapper.map(Carro, CarroRespostaDTO.class);
    }

    // converte uma lista de carro para CarroDTO
    public static List<CarroRespostaDTO> converterListaParaDTO(List<Carro> list) {
        return list.stream().map(CarroMapper::converterParaRespostaDTO).toList();
    }

    public static List<Carro> converterListaParaCarro(List<CarroDTO> list) {
        return list.stream().map(CarroMapper::converterParaEntidade).toList();
    }
}
