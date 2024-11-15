package com.example.springcicd.services;


import com.example.springcicd.dto.entities.marca.MarcaDTO;
import com.example.springcicd.dto.entities.marca.MarcaRespostaDTO;
import com.example.springcicd.dto.mapper.MarcaMapper;
import com.example.springcicd.entities.Carro;
import com.example.springcicd.repository.CarroRepository;
import com.example.springcicd.services.exceptions.DatabaseException;
import com.example.springcicd.services.exceptions.ResourceNotFound;
import com.example.springcicd.services.exceptions.ValidationExc;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;


/**
 * The type Marca service test.
 */
@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MarcaServiceTest {

    /**
     * The constant ID.
     */
    public static final long ID = 1L;

    @Autowired
    private CarroRepository carroRepository;

    @Autowired
    private MarcaService service;

    private MarcaDTO marca;


    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {

        marca = new MarcaDTO(ID, "marca 1");
    }

    /**
     * When find all then return a marca instance.
     */
    @Test
    @DisplayName("quando encontrar tudo, retorne todas instâncias da Marca")
    @Order(5)
    void whenFindAllThenReturnAnMarcaInstance() {

        List<MarcaRespostaDTO> respostaDTO = service.findAllMarca();
        Assertions.assertFalse(respostaDTO.isEmpty());
        respostaDTO.forEach(System.out::println);
    }

    /**
     * When find by id then return a marca instance.
     */
    @Test
    @DisplayName("quando procurar por Id, em seguida, retorne uma instância de Marca")
    @Order(4)
    void whenFindByIdThenReturnAnMarcaInstance() {
        MarcaRespostaDTO resposta = service.findById(ID);
        System.out.println(resposta);

        Assertions.assertNotNull(resposta, "Objeto vazio");
        Assertions.assertEquals(MarcaRespostaDTO.class, resposta.getClass());
        Assertions.assertEquals(ID, resposta.getId());
    }

    /**
     * When find by idthen return brand instance along with the car instances.
     */
    @Test
    @DisplayName("quando procurar por ID, retorne a instância da marca junto com as instâncias do carro")
    @Order(6)
    void whenFindByIdthenReturnBrandInstanceAlongWithTheCarInstances() {
        popularBanco();

        MarcaDTO respostaDTO = service.findAllById(ID);

        Assertions.assertNotNull(respostaDTO);
        Assertions.assertFalse(respostaDTO.getCarros().isEmpty());
        Assertions.assertEquals("carro 1", respostaDTO.getCarros().get(0).getModelo());

        //System.out.println(respostaDTO);
    }

    /**
     * When find by id then return an not found.
     */
    @Test
    @DisplayName("quando procurar por ID, em seguida, retornar um não encontrado")
    @Order(7)
    void whenFindByIdThenReturnAnNotFound() {

        try {
            service.findById(ID);
        } catch (Exception e) {

            Assertions.assertEquals("Recurso não encontrado. ID: 9", e.getMessage());
        }
    }

    /**
     * When create then return ok.
     */
    @Test
    @Order(1)
    @DisplayName("quando criar, retorne ok")
    void whenCreateThenReturnOk() {

        MarcaRespostaDTO resposta = service.insert(marca);

        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(MarcaRespostaDTO.class, resposta.getClass());
        Assertions.assertEquals(ID, resposta.getId());
    }

    /**
     * When create then return an constraint violation exception.
     */
    @Test
    @Order(2)
    @DisplayName("quando tentar criar, retorne uma exceção de violação de restrição")
    void whenCreateThenReturnAnConstraintViolationException() {

        try {
            service.insert(new MarcaDTO(null, "marca 1"));
        } catch (Exception ex) {
            Assertions.assertEquals(ValidationExc.class, ex.getClass());
            Assertions.assertNotEquals("[Name is mandatory]", ex.getMessage(), "Nome é obrigatorio");
            Assertions.assertEquals("Nome de marca já existente", ex.getMessage());
        }
    }

    /**
     * Update.
     */
    @Test
    @Order(3)
    void update() {

        MarcaRespostaDTO respostaDTO = service.update(marca.getId(), new MarcaDTO(marca.getId(), "Mercedes"));
        Assertions.assertEquals(respostaDTO.getId(), 1);
        Assertions.assertEquals(respostaDTO.getName(), "Mercedes");
    }

    /**
     * When delete with database integrity.
     */
    @Test
    void WhenDeleteReturnExceptionDatabaseIntegrity() {

        try {
            service.delete(ID);
        } catch (Exception ex) {
            Assertions.assertEquals(DatabaseException.class, ex.getClass());
        }
    }

    /**
     * When delete with ok.
     */
    @Test
    public void whenDeleteWithOk() {
        MarcaRespostaDTO marca = service.insert(new MarcaDTO(null, "Fiat"));
        service.delete(marca.getId());
        try {
            service.findById(marca.getId());
            Assertions.fail("A marca não deveria ser encontrada após exclusão.");
        } catch (Exception ex) {
            Assertions.assertNotEquals(DatabaseException.class, ex.getClass());
            Assertions.assertEquals(ResourceNotFound.class, ex.getClass());
        }
    }

    private void popularBanco() {

        MarcaRespostaDTO m1 = service.findById(1L);
        Carro c1 = new Carro(null, "carro 1", 3000.00, MarcaMapper.converterParaEntidade(m1));
        Carro c2 = new Carro(null, "carro 2", 5000.00, MarcaMapper.converterParaEntidade(m1));

        List<Carro> carros = Arrays.asList(c1, c2);
        carroRepository.saveAll(carros);
    }
}