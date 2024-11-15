package com.example.springcicd.controller;


import com.example.springcicd.dto.entities.marca.MarcaDTO;
import com.example.springcicd.dto.entities.marca.MarcaRespostaDTO;
import com.example.springcicd.services.MarcaService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Controlador que lida com requisições HTTP relacionadas à entidade Marca.
 * A base do mapeamento é "/marcas".
 */
@RestController
@RequestMapping("/marcas")
public class MarcaResource {

    @Autowired
    private MarcaService marcaService;

    // GET

    @Operation(summary = "Lista todas as marcas",
            description = "Retorna uma lista com dados referentes ao objeto do tipo Marca")
    @GetMapping
    public ResponseEntity<List<MarcaRespostaDTO>> findAll() {
        List<MarcaRespostaDTO> result = marcaService.findAllMarca();
        return ResponseEntity.ok().body(result);
    }

    @Operation(summary = "Busca uma marca por ID",
            description = "Recupera uma instância de Marca com base no ID fornecido e retorna o objeto encontrado com o status 200 caso sucesso")
    @GetMapping("{id}")
    public ResponseEntity<MarcaRespostaDTO> findById(@PathVariable Long id) {
        MarcaRespostaDTO result = marcaService.findById(id);
        return ResponseEntity.ok().body(result);
    }

    @Operation(summary = "Lista todos os veículos de uma marca",
            description = "Retorna uma lista de veículos associados à marca especificada pelo ID")
    @GetMapping("findMarca/{id}")
    public ResponseEntity<MarcaDTO> findMarca(@PathVariable Long id) {
        MarcaDTO result = marcaService.findAllById(id);
        return ResponseEntity.ok().body(result);
    }

    // POST

    @Operation(summary = "Cadastra uma nova marca",
            description = "Cadastra uma nova marca e retorna o objeto recém-cadastrado com o status 201 (Created)")
    @PostMapping
    public ResponseEntity<MarcaRespostaDTO> insert(@RequestBody MarcaDTO obj) {
        MarcaRespostaDTO marca = marcaService.insert(obj);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/marcas/{id}")
                .buildAndExpand(marca.getId()).toUri();
        return ResponseEntity.created(uri).body(marca);
    }

    // PUT

    @Operation(summary = "Atualiza uma marca por ID",
            description = "Atualiza os atributos de uma marca com base no ID fornecido e no objeto Marca")
    @PutMapping("{id}")
    public ResponseEntity<MarcaRespostaDTO> update(@PathVariable Long id, @RequestBody MarcaDTO obj) {
        MarcaRespostaDTO marca = marcaService.update(id, obj);
        return ResponseEntity.ok().body(marca);
    }

    // DELETE

    @Operation(summary = "Exclui uma marca por ID",
            description = "Exclui uma marca com base no ID fornecido e retorna o status 200 caso sucesso")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        marcaService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Exclui uma marca e todos os carros associados",
            description = "Exclui uma marca com base no ID fornecido, incluindo a exclusão de todos os carros associados")
    @DeleteMapping("cascade/{id}")
    public ResponseEntity<Void> delAll(@PathVariable Long id) {
        marcaService.delAll(id);
        return ResponseEntity.ok().build();
    }
}
