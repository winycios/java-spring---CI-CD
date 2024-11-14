package com.example.springcicd.services.exceptions;

/**
 * Exceção personalizada para representar problemas relacionados a rcursos não
 * encontrados
 */
public class ResourceNotFound extends RuntimeException {

    /**
     * Cria uma nova instância de ResourceNotFound com uma mensagem descritiva.
     *
     * @param obj valor ou ID passado.
     */
    public ResourceNotFound(Object obj) {
        super("Recurso não encontrado. ID: " + obj);
    }
}
