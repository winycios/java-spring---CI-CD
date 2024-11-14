package com.example.springcicd.controller.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.Instant;

/**
 * Modelo personalizado criado para reprensetar uma excessão HTTP
 */
public class ModeloError implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss'Z'", timezone = "GMT")
    /**
     * horario do ocorrido
     */
    private Instant timestamp;
    /**
     * Status HTTP
     */
    private Integer status;
    /**
     * Erro
     */
    private String error;
    /**
     * Descrição do erro
     */
    private String message;
    /**
     * Localização do arquivo
     */
    private String path;

    public ModeloError() {
    }

    public ModeloError(Instant timestamp, Integer status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
