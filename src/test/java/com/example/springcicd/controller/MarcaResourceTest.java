package com.example.springcicd.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import  com.example.springcicd.dto.entities.carro.CarroDTO;
import  com.example.springcicd.dto.entities.marca.MarcaDTO;
import  com.example.springcicd.dto.entities.marca.MarcaRespostaDTO;
import  com.example.springcicd.dto.mapper.CarroMapper;
import  com.example.springcicd.dto.mapper.MarcaMapper;
import  com.example.springcicd.repository.CarroRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * The type Marca resource test.
 */
@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
class MarcaResourceTest {

    @Autowired
    private MarcaResource resource;

    @Autowired
    private CarroRepository carroRepository;

    @Autowired
    private MockMvc mvc;

    /**
     * When create then return created.
     *
     * @throws Exception the exception
     */
    @Test
    @Order(1)
    @DisplayName("Post OK - quando criar, retorne ok")
    void whenCreateThenReturnCreated() throws Exception {

        mvc.perform(MockMvcRequestBuilders.post("/marcas").content(asJsonString(new MarcaDTO(null, "Fiat")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Fiat"));
    }

    /**
     * When create then return an constraint violation exception.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Post error - quando tentar criar, retorne uma exceção de violação de restrição")
    void whenCreateThenReturnAnConstraintViolationException() throws Exception {

        mvc.perform(MockMvcRequestBuilders.post("/marcas").content(asJsonString(new MarcaDTO(null, "Fiat")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotAcceptable())
                .andExpect(result -> assertEquals("Nome de marca já existente", Objects.requireNonNull(result.getResolvedException()).getMessage()));

    }

    /**
     * When find all then return an carros instance.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("findAll - quando procurar, retorne todas instâncias de marca")
    void whenFindAllThenReturnAnCarrosInstance() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/marcas").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());
    }

    /**
     * When find by id then return an carros instance.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("findById OK- quando procurar, retorne uma instancia do Objeto carro")
    void whenFindByIdThenReturnAnCarrosInstance() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/marcas/{id}", 1).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Fiat"));
    }

    /**
     * When find by id then return not found exception.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("findById Error- quando procurar, retorne not found exception")
    void whenFindByIdThenReturnNotFoundException() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/marcas/{id}", 10)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals("Recurso não encontrado. ID: 10", Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    /**
     * When find all by id then return all list marca.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("findAllById Ok- quando procurar, retorne um objeto marca com todos os carros associados")
    void whenFindAllByIdThenReturnAllListMarca() throws Exception {
        popularBanco(resource.findById(1L).getBody());

        mvc.perform(MockMvcRequestBuilders.get("/marcas/findMarca/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.carros", hasSize(2)));
    }

    /**
     * As json string string.
     *
     * @param obj the obj
     * @return the string
     */
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * When update then return ok.
     *
     * @throws Exception the exception
     */
    @Test
    @Order(2)
    @DisplayName("Update Ok- Ao fazer update, retorne OK")
    void whenUpdateThenReturnOk() throws Exception {
        MarcaRespostaDTO respostaDTO = resource.insert(new MarcaDTO(null, "volks")).getBody();

        mvc.perform(MockMvcRequestBuilders.put("/marcas/{id}", respostaDTO.getId())
                        .content(asJsonString(new MarcaDTO(null, "Mercedes")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(respostaDTO.getId()))
                .andExpect(jsonPath("$.name").value("Mercedes"));
    }

    /**
     * When update then return not found exception.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Update Error - Ao fazer update, retorne not found exception")
    void whenUpdateThenReturnNotFoundException() throws Exception {

        mvc.perform(MockMvcRequestBuilders.put("/marcas/{id}", 18)
                        .content(asJsonString(new MarcaDTO(null, "Mercedes")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals("Recurso não encontrado. ID: 18", Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }


    /**
     * When update then return validation exc.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Update Error - Ao fazer update, retorne ValidationException")
    void whenUpdateThenReturnValidationExc() throws Exception {

        mvc.perform(MockMvcRequestBuilders.put("/marcas/{id}", 2)
                        .content(asJsonString(new MarcaDTO(null, "")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotAcceptable())
                .andExpect(result -> assertEquals("Nome não pode ser nulo", Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    /**
     * When delete then return ok.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Delete OK - Quando deletar, retorne o ok")
    void whenDeleteThenReturnOk() throws Exception {
        MarcaRespostaDTO respostaDTO = resource.insert(new MarcaDTO(null, "volks")).getBody();

        mvc.perform(MockMvcRequestBuilders.delete("/marcas/{id}", respostaDTO.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    /**
     * When delete then return not found exception.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Delete Error - Quando deletar, retorne not found exception")
    void whenDeleteThenReturnNotFoundException() throws Exception {

        mvc.perform(MockMvcRequestBuilders.delete("/marcas/{id}", 10))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals("Recurso não encontrado. ID: 10", Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    @Order(3)
    @DisplayName("Delete cascade - Quando deletar tudo, retorne ok")
    void whenDeleteCascadeThenReturnOk() throws Exception {
        MarcaRespostaDTO respostaDTO = resource.insert(new MarcaDTO(null, "volks")).getBody();
        popularBanco(respostaDTO);

        mvc.perform(MockMvcRequestBuilders.delete("/marcas/cascade/{id}", respostaDTO.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private void popularBanco(MarcaRespostaDTO marcaRespostaDTO) {

        MarcaDTO respostaDTO = MarcaMapper.converterParaDTO(marcaRespostaDTO);
        CarroDTO c1 = new CarroDTO(null, "carro 1", 3000.00, respostaDTO);
        CarroDTO c2 = new CarroDTO(null, "carro 2", 5000.00, respostaDTO);

        List<CarroDTO> carros = Arrays.asList(c1, c2);
        carroRepository.saveAll(CarroMapper.converterListaParaCarro(carros));
    }
}