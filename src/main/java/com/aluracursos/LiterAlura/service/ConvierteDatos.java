package com.aluracursos.LiterAlura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvierteDatos implements IConvierteDatos {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <C> C obtenerDatos(String json, Class<C> clase) {
        try {
            if (json == null || clase == null) {
                throw new IllegalArgumentException("JSON y clase no pueden ser nulos");
            }
            return objectMapper.readValue(json.toString(), clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al procesar el JSON: " + e.getMessage(), e);
        }
    }
}
