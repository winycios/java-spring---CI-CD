package com.example.springcicd.dto.entities.marca;


import com.example.springcicd.entities.Marca;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarcaRespostaDTO {
    
    private Long id;
    private String name;

    public MarcaRespostaDTO(Marca marca) {

        this.id = marca.getId();
        this.name = marca.getName();
    }
}
