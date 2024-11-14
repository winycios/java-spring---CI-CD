package com.example.springcicd.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.springcicd.dto.entities.carro.CarroDTO;
import com.example.springcicd.dto.entities.carro.CarroRespostaDTO;
import com.example.springcicd.dto.entities.marca.MarcaDTO;
import com.example.springcicd.services.CarroService;
import com.example.springcicd.services.exceptions.ResourceNotFound;
import com.example.springcicd.services.exceptions.ValidationExc;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * The type Carro resource test.
 */
@SpringBootTest
@AutoConfigureMockMvc
class CarroResourceTest {


    @MockBean
    private CarroService carroService;

    @Autowired
    private MockMvc mvc;


    /**
     * When find all then return a carros instance.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("quando procurar, retorne todas instâncias de Carros")
    void whenFindAllThenReturnAnCarrosInstance() throws Exception {

        when(this.carroService.findAllCarro()).thenReturn(new ArrayList<>(
                asList(
                        new CarroRespostaDTO(1L, "oi", 15.00),
                        new CarroRespostaDTO(2L, "sei la", 90.00)
                )
        ));
        mvc.perform(MockMvcRequestBuilders.get("/carros").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)));
    }


    /**
     * When find by id then return an carros instance.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("quando procurar por Id, em seguida, retorne uma instância de Carro")
    void whenFindByIdThenReturnAnCarrosInstance() throws Exception {

        when(this.carroService.findById(1L)).thenReturn(new CarroRespostaDTO(1L, "oi", 15.00));
        mvc.perform(MockMvcRequestBuilders.get("/carros/{id}", 1L).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.modelo").value("oi"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valor").value(15.00));
    }

    /**
     * When find by id then return not found exception.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("quando procurar por Id, em seguida, retorne not found")
    void whenFindByIdThenReturnNotFoundException() throws Exception {

        when(this.carroService.findById(15L)).thenThrow(new ResourceNotFound(15L));
        mvc.perform(MockMvcRequestBuilders.get("/carros/{id}", 15)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    /**
     * When create then return ok.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("quando criar, retorne ok")
    void whenCreateThenReturnCreated() throws Exception {

        when(this.carroService.insert(new CarroDTO(1L, "oi", 15.00, new MarcaDTO(null, "oi")))).thenReturn(new CarroRespostaDTO(1L, "oi", 15.00));

        mvc.perform(MockMvcRequestBuilders.post("/carros").content(asJsonString(new CarroDTO(1L, "oi", 15.00, new MarcaDTO(null, "oi"))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.modelo").value("oi"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valor").value(15.00));

    }

    /**
     * When create then return a constraint violation exception.
     *
     * @throws Exception the exception
     */    @Test
    @DisplayName("quando tentar criar, retorne uma exceção de violação de restrição")
    void whenCreateThenReturnAnConstraintViolationException() throws Exception {

        when(this.carroService.insert(new CarroDTO(1L, null, null, new MarcaDTO(null, "oi")))).thenThrow(new ValidationExc("Modelo e valor é obrigatorio"));

        mvc.perform(MockMvcRequestBuilders.post("/carros").content(asJsonString(new CarroDTO(1L, null, null, new MarcaDTO(null, "oi"))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotAcceptable());
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
     * Delete carro.
     *
     * @throws Exception the exception
     */
    @Test
    void whenDeleteThenReturnOk() throws Exception {

        mvc.perform(MockMvcRequestBuilders.delete("/carros/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(""));
        verify(carroService).delete(1L);
    }

    /**
     * When delete then return not found exception.
     *
     * @throws Exception the exception
     */
    @Test
    void whenDeleteThenReturnNotFoundException() throws Exception {

        doThrow(new ResourceNotFound(10L)).when(carroService).delete(10L);

        mvc.perform(MockMvcRequestBuilders.delete("/carros/{id}", 10))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(carroService).delete(10L);
    }
}