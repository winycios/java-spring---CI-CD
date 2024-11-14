package com.example.springcicd.dto.mapper;


import com.example.springcicd.dto.entities.marca.MarcaDTO;
import com.example.springcicd.dto.entities.marca.MarcaRespostaDTO;
import com.example.springcicd.entities.Marca;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MarcaMapper {

    private static final ModelMapper mapper = new ModelMapper();

    // converte para entidade
    public static Marca converterParaEntidade(MarcaDTO marcaDTO) {
        return mapper.map(marcaDTO, Marca.class);
    }

    public static Marca converterParaEntidade(MarcaRespostaDTO marcaRespostaDTO) {
        return mapper.map(marcaRespostaDTO, Marca.class);
    }

    // converte para RespostaDTO
    public static MarcaRespostaDTO converterParaRespostaDTO(Marca marca) {
        return mapper.map(marca, MarcaRespostaDTO.class);
    }

    public static MarcaRespostaDTO converterParaRespostaDTO(MarcaDTO marca) {
        return mapper.map(marca, MarcaRespostaDTO.class);
    }

    // converte para DTO
    public static MarcaDTO converterParaDTO(Marca marca) {
        return mapper.map(marca, MarcaDTO.class);
    }

    public static MarcaDTO converterParaDTO(MarcaRespostaDTO marca) {
        return mapper.map(marca, MarcaDTO.class);
    }

}
