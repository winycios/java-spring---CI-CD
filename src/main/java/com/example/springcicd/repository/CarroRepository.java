package com.example.springcicd.repository;


import com.example.springcicd.entities.Carro;
import com.example.springcicd.entities.interfaces.ExCarros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * A interface CarroRepository é responsável por acessar e manipular dados no
 * banco de dados ou em outra fonte de dados externa para a entidade Carro.
 */
@Repository
public interface CarroRepository extends JpaRepository<Carro, Long> {

    /**
     * Lista todos os carros e suas marcas cujo valor é maior ou igual ao valor
     * fornecido.
     *
     * @param valor O valor a ser utilizado como critério para a busca de veículos.
     * @return Uma lista de carros contendo o modelo do carro, valor e nome da
     *         marca para carros com valor maior ou igual ao especificado.
     */
    @Query("SELECT m.name AS nomeMarca, c.modelo AS modelo, c.valor AS valor FROM Carro c INNER JOIN c.marca m WHERE c.valor >= :valor")
    List<ExCarros> findBycarrosGreaterThan(@Param("valor") Double valor);
    /*
     * @Query("SELECT m FROM Carro c INNER JOIN c.marca m WHERE c.valor >= :valor")
     * List<Marca> findBycarrosGreaterThan(@Param("valor") Double valor);
     */
}
