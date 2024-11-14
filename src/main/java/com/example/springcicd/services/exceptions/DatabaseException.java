package com.example.springcicd.services.exceptions;

/**
 * Exceção personalizada para representar problemas relacionados ao banco de
 * dados.
 */
public class DatabaseException extends RuntimeException {

    /**
     * Cria uma nova instância de DatabaseException com uma mensagem descritiva.
     *
     * @param msg A mensagem descritiva da exceção.
     */
    public DatabaseException(String msg) {
        super(msg);
    }
}
