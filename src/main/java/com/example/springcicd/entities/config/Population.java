package com.example.springcicd.entities.config;


import com.example.springcicd.entities.Carro;
import com.example.springcicd.entities.Marca;
import com.example.springcicd.repository.CarroRepository;
import com.example.springcicd.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.List;

/**
 * Classe criada para fazer a população do banco de dados postGrenSQL
 */
@Configuration
@Profile("dev")
public class Population implements CommandLineRunner {

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private CarroRepository carroRepository;

    @Override
    public void run(String... args) throws Exception {

        Marca m1 = new Marca(null, "oi");
        Marca m2 = new Marca(null, "tim");
        Marca m3 = new Marca(null, "claro");

        List<Marca> marcas = Arrays.asList(m1, m2, m3);
        marcaRepository.saveAll(marcas);

        Carro c1 = new Carro(null, "carro 1", 3000.00, m1);
        Carro c2 = new Carro(null, "carro 2", 5000.00, m1);
        Carro c3 = new Carro(null, "carro 3", 6000.00, m3);
        Carro c4 = new Carro(null, "carro 4", 7000.00, m2);

        List<Carro> carros = Arrays.asList(c1, c2, c3, c4);
        carroRepository.saveAll(carros);
    }
}
