package com.example.springcicd.services;


import com.example.springcicd.dto.entities.marca.MarcaDTO;
import com.example.springcicd.dto.entities.marca.MarcaRespostaDTO;
import com.example.springcicd.dto.mapper.MarcaMapper;
import com.example.springcicd.repository.CarroRepository;
import com.example.springcicd.repository.MarcaRepository;
import com.example.springcicd.services.exceptions.DatabaseException;
import com.example.springcicd.services.exceptions.ResourceNotFound;
import com.example.springcicd.services.exceptions.ValidationExc;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

// lógica de negócios da classe Marca
@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private CarroRepository carroRepository;


    // @Get

    /**
     * Lista para procurar todos as marcas cadastradas
     *
     * @return retorna uma lista com dados referentes ao objeto do tipo Marca
     */
    public List<MarcaRespostaDTO> findAllMarca() {
        return marcaRepository.findAll().stream().map(MarcaRespostaDTO::new).toList();
    }

    /**
     * Procura uma marca pelo ID determinado
     *
     * @param id, ID da marca
     * @return retorna um Objeto do tipo marca e todos os carros existentes nela
     * @throws ResourceNotFound ID não encontrado
     */
    public MarcaRespostaDTO findById(Long id) {
        Optional<MarcaRespostaDTO> marca = marcaRepository.findById(id).stream().map(MarcaRespostaDTO::new).findAny();

        return marca.orElseThrow(() -> new ResourceNotFound(id));
    }

    /**
     * Verifica se o nome de determinada marca já está em uso
     *
     * @param obj, É passado um objeto do tipo Marca.
     * @throws ValidationExc        Campo da marca sendo utilizado
     * @throws NullPointerException Campo em branco
     */
    public void verificarNome(MarcaDTO obj) {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<MarcaDTO>> violations = validator.validate(obj);
        if (!violations.isEmpty()) {
            throw new ValidationExc(violations.stream().map(ConstraintViolation::getMessage).toList().toString());
        }

        long qtd = marcaRepository.findAll().stream()
                .filter(m -> m.getName().equalsIgnoreCase(obj.getName())).count();

        if (qtd != 0) {
            throw new ValidationExc("Nome de marca já existente");
        }

    }

    /**
     * Lista todos os veículos associados a uma marca específica.
     *
     * @param id O ID da marca
     * @return Uma lista de veículos associados à marca especificada.
     * @throws ResourceNotFound Se a marca com o ID especificado não tiver
     *                          veículos associados.
     */
    public MarcaDTO findAllById(Long id) {
        Optional<MarcaDTO> marca = marcaRepository.findById(id).stream().map(MarcaDTO::new).findAny();

        return marca.orElseThrow(() -> new ResourceNotFound(id));
    }

    // @Post

    /**
     * Cadastra uma nova marca
     *
     * @param obj, é passado um objeto do tipo Marca
     * @return Retorna a marca cadastrada após a operação bem-sucedida.
     * @throws ConstraintViolationException Validação não atendida
     */
    public MarcaRespostaDTO insert(MarcaDTO obj) {

        verificarNome(obj);

        return MarcaMapper.converterParaRespostaDTO(marcaRepository.save(MarcaMapper.converterParaEntidade(obj)));
    }

    // @PUT

    /**
     * Atualiza os atributos de uma marca com base no ID fornecido e no objeto
     * Marca.
     *
     * @param id  O ID da marca que terá seus atributos atualizados.
     * @param obj O objeto Marca contendo os novos atributos a serem aplicados à
     *            marca.
     * @return Retorna a marca atualizada após a operação bem-sucedida.
     * @throws ResourceNotFound    Se a marca com o ID especificado não for
     *                             encontrada.
     * @throws ValidationException Validação não atendida
     */
    public MarcaRespostaDTO update(Long id, MarcaDTO obj) {
        try {
            MarcaDTO entity = findAllById(id);

            verificarNome(obj);

            if (obj.getName().isEmpty()) {
                throw new ValidationExc("Nome não pode ser nulo");
            }

            updateData(entity, obj);
            return MarcaMapper.converterParaRespostaDTO(marcaRepository.save(MarcaMapper.converterParaEntidade(entity)));

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFound(id);
        }
    }

    /**
     * Atualiza os atributos de um objeto Marca com base em outro objeto Marca
     * fornecido.
     *
     * @param entity O objeto Marca que terá seus valores atualizados.
     * @param obj    O objeto Marca contendo os novos atributos que serão aplicados
     *               à entidade.
     */
    private void updateData(MarcaDTO entity, MarcaDTO obj) {
        entity.setName(obj.getName());
    }

    // @DELETE

    /**
     * Exclui uma marca com base no ID fornecido.
     *
     * @param id O ID da marca a ser excluída.
     * @throws ResourceNotFound  ID não encontrado
     * @throws DatabaseException Caso ocorra um erro de violação de
     *                           integridade de dados durante a exclusão.
     */
    public void delete(Long id) {
        try {
            marcaRepository.findById(id).ifPresentOrElse(marca -> {
                marcaRepository.deleteById(id);
            }, () -> {
                throw new ResourceNotFound(id);
            });

        } catch (DataIntegrityViolationException e) {

            throw new DatabaseException(e.getMostSpecificCause().getMessage());
        }
    }

    /**
     * Exclui uma marca com base no ID fornecido, incluindo a exclusão de
     * todos os carros associados.
     *
     * @param id O ID da marca a ser excluída
     * @throws ResourceNotFound  ID não encontrado
     * @throws DatabaseException Caso ocorra um erro de violação de
     *                           integridade de dados durante a exclusão.
     */
    public void delAll(Long id) {
        try {
            marcaRepository.findById(id).ifPresentOrElse(marca -> {
                MarcaDTO carroIds = findAllById(id);

                if (!carroIds.getCarros().isEmpty()) {
                    carroIds.getCarros().forEach(carro -> carroRepository.deleteById(carro.getId()));
                }

                marcaRepository.deleteById(id);

            }, () -> {
                throw new ResourceNotFound(id);
            });
        } catch (

                DataIntegrityViolationException e) {

            throw new DatabaseException(e.getMostSpecificCause().getMessage());
        }
    }
}
