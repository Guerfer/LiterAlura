package com.aluracursos.LiterAlura.service;

public interface IConvierteDatos {
    <C> C obtenerDatos(String json, Class<C> clase);
}
