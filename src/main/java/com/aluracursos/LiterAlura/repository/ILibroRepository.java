package com.aluracursos.LiterAlura.repository;

import com.aluracursos.LiterAlura.model.Autor;
import com.aluracursos.LiterAlura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ILibroRepository extends JpaRepository<Libro, Long> {

    @Query("SELECT DISTINCT a FROM Autor a WHERE a.añoNacimiento <= :añoFinal AND (a.añoMuerte IS NULL OR a.añoMuerte >= :añoInicial)")
    List<Autor> findAutoresVivosEnRango(int añoInicial, int añoFinal);

    List<Libro> findByIdiomasIgnoreCase(String idioma);
}

