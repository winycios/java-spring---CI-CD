package com.example.springcicd.services;


import com.example.springcicd.dto.entities.carro.CarroDTO;
import com.example.springcicd.dto.entities.carro.CarroRespostaDTO;
import com.example.springcicd.dto.mapper.CarroMapper;
import com.example.springcicd.entities.interfaces.ExCarros;
import com.example.springcicd.repository.CarroRepository;
import com.example.springcicd.services.exceptions.ResourceNotFound;
import com.example.springcicd.services.exceptions.ValidationExc;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * The type Carro service.
 */
// lógica de negócios da classe Carro
@Service
public class CarroService {

    @Autowired
    private CarroRepository carroRepository;

    @Autowired
    private Validator validator;

    // GET

    /**
     * Lista para procurar todos os carros cadastrados
     *
     * @return retorna uma lista com dados referentes ao objeto do tipo carro
     */
    public List<CarroRespostaDTO> findAllCarro() {
        return carroRepository.findAll().stream().map(CarroRespostaDTO::new).toList();
    }

    /**
     * Lista os carros com valor acima de um valor determinado
     *
     * @param value the value
     * @return retorna uma lista com dados referentes ao objeto do tipo carro, que tem seu valor maior
     */
    public List<ExCarros> findByValorGreaterThan(Double value) {
        List<ExCarros> exCarrosList = carroRepository.findBycarrosGreaterThan(value);
        if (exCarrosList.isEmpty()) {
            throw new ResourceNotFound("Nenhum carro encontrrado");
        }
        return exCarrosList;
    }

    /**
     * Procura um carro pelo ID determinado
     *
     * @param id do carro
     * @return Retorna um Carro de determinado ID
     * @throws ResourceNotFound ID não encontrado
     */
    public CarroRespostaDTO findById(Long id) {
        Optional<CarroRespostaDTO> carro = carroRepository.findById(id).stream().map(CarroRespostaDTO::new).findFirst();

        return carro.orElseThrow(() -> new ResourceNotFound(id));
    }

    /**
     * Verifica se o nome de determinado Carro já está em uso
     *
     * @param obj the obj
     * @throws ValidationExc Modelo do carro existente
     */
    public void verificarNome(CarroDTO obj) {
        long qtd = carroRepository.findAll().stream()
                .filter(m -> m.getModelo().equalsIgnoreCase(obj.getModelo())).count();

        if (qtd != 0) {
            throw new ValidationExc("Modelo de Carro já existente");
        }
    }

    // POST

    /**
     * Cadastra um novo carro.
     *
     * @param obj Objeto do tipo Carro que será cadastrado. Não pode ser nulo.
     * @return realiza a inserção e retorna o objeto Carro.
     * @throws ValidationExc Caso ocorrer descumprimento de validação
     */
    public CarroRespostaDTO insert(CarroDTO obj) {
        verificarNome(obj);

        // Validação manual
        Set<ConstraintViolation<CarroDTO>> violations = validator.validate(obj);
        if (!violations.isEmpty()) {
            throw new ValidationExc(violations.stream().map(ConstraintViolation::getMessage).toList().toString());
        }

        return CarroMapper.converterParaRespostaDTO(carroRepository.save(CarroMapper.converterParaEntidade(obj)));
    }

    // PUT

    /**
     * Atualiza os atributos de uma carro com base no ID fornecido e no objeto
     * Carro.
     *
     * @param id  O ID do carro que terá seus atributos atualizados.
     * @param obj O objeto Carro contendo os novos atributos a serem aplicados à Carro.
     * @return Retorna o Carro atualizado após a operação bem-sucedida.
     * @throws ResourceNotFound    Se a Carro com o ‘ID’ especificado não for encontrada.
     * @throws ValidationException Validação não atendida
     */
    public CarroRespostaDTO update(Long id, CarroDTO obj) {
        try {
            CarroRespostaDTO entity = findById(id);

            verificarNome(obj);

            if (obj.getModelo().isEmpty()) {
                throw new ValidationExc("Modelo não pode ser nulo");
            }

            updateData(entity, obj);
            return CarroMapper.converterParaRespostaDTO(carroRepository.save(CarroMapper.converterParaEntidade(entity)));

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFound(id);
        }
    }

    /**
     * Atualiza os atributos de um objeto Carro com base em outro objeto Carro
     * fornecido.
     *
     * @param entity O objeto Carro que terá seus valores atualizados.
     * @param obj    O objeto Carro contendo os novos atributos que serão aplicados
     *               à entidade.
     */
    private void updateData(CarroRespostaDTO entity, CarroDTO obj) {
        entity.setModelo(obj.getModelo());

        // Verifica se o valor não é nulo antes de atualizar
        if (obj.getValor() != null) {
            entity.setValor(obj.getValor());
        }
    }

    // DELETE

    /**
     * Deletar carro
     *
     * @param id the id
     */
    public void delete(Long id) {

        if (carroRepository.findById(id).isEmpty())
            throw new ResourceNotFound(id);

        carroRepository.deleteById(id);
    }
}