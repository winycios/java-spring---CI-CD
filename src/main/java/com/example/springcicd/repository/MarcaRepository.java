package com.example.springcicd.repository;


import com.example.springcicd.entities.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * A interface MarcaRepository é responsável por acessar e manipular dados no
 * banco de dados ou em outra fonte de dados externa para a entidade marca.
 */
@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {

}
