package com.example.springcicd.controller;


import com.example.springcicd.dto.entities.carro.CarroDTO;
import com.example.springcicd.dto.entities.carro.CarroRespostaDTO;
import com.example.springcicd.entities.interfaces.ExCarros;
import com.example.springcicd.services.CarroService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/carros")
public class CarroResource {

    @Autowired
    private CarroService carroService;

    // GET

    @Operation(summary = "Lista todos os carros cadastrados",
            description = "Retorna uma lista com dados referentes ao objeto do tipo carro")
    @GetMapping
    public ResponseEntity<List<CarroRespostaDTO>> findAll() {
        List<CarroRespostaDTO> result = carroService.findAllCarro();
        return ResponseEntity.ok().body(result);
    }

    @Operation(summary = "Busca um carro por ID",
            description = "Procura um carro pelo ID determinado e retorna o objeto encontrado com o status 200 caso sucesso")
    @GetMapping("/{id}")
    public ResponseEntity<CarroRespostaDTO> findById(@PathVariable Long id) {
        CarroRespostaDTO result = carroService.findById(id);
        return ResponseEntity.ok().body(result);
    }

    @Operation(summary = "Busca carros com valor acima de um valor determinado",
            description = "Retorna uma lista com dados de carros com valor maior que o valor especificado")
    @GetMapping("findCarValue/{value}")
    public ResponseEntity<List<ExCarros>> findCarValue(@PathVariable Double value) {
        List<ExCarros> result = carroService.findByValorGreaterThan(value);
        return ResponseEntity.ok().body(result);
    }

    // POST

    @Operation(summary = "Cadastra um novo carro",
            description = "Cadastra um carro e retorna o objeto recém-cadastrado com o status 201 (Created)")
    @PostMapping
    public ResponseEntity<CarroRespostaDTO> insert(@RequestBody CarroDTO obj) {
        CarroRespostaDTO carro = carroService.insert(obj);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/carros/{id}")
                .buildAndExpand(carro.getId()).toUri();
        return ResponseEntity.created(uri).body(carro);
    }

    // PUT

    @Operation(summary = "Atualiza um carro por ID",
            description = "Atualiza os atributos de um carro com base no ID fornecido e retorna o objeto atualizado")
    @PutMapping("/{id}")
    public ResponseEntity<CarroRespostaDTO> update(@PathVariable Long id, @RequestBody CarroDTO obj) {
        CarroRespostaDTO carroRespostaDTO = carroService.update(id, obj);
        return ResponseEntity.ok().body(carroRespostaDTO);
    }

    // DELETE

    @Operation(summary = "Exclui um carro por ID",
            description = "Exclui um carro com base no ID fornecido e retorna o status 200 caso sucesso")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCarro(@PathVariable Long id) {
        carroService.delete(id);
        return ResponseEntity.ok().build();
    }
}
