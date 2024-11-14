package com.example.springcicd.services.exceptions;

/**
 * Exceção personalizada para representar problemas relacionados a validação de
 * campos e a classe Validation
 */
public class ValidationExc extends RuntimeException {

    /**
     * Cria uma nova instância de ValidationExc com uma mensagem descritiva.
     *
     * @param message A mensagem descritiva da exceção.
     */
    public ValidationExc(String message) {
        super(message);
    }
}
