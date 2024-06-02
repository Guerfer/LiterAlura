package com.aluracursos.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//// Este record contiene los nombre que dimos y los alias con los que aparecen en formato Json ////

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(
    @JsonAlias("name") String nombre,
    @JsonAlias("birth_year") Integer añoNacimiento,
    @JsonAlias("death_year") Integer añoMuerte
) {
}
