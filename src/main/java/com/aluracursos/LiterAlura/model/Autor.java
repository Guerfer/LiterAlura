package com.aluracursos.LiterAlura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "autor")

public class Autor {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //// estos @ son para la base de datos ////
    private Long Id;
    private String nombre;
    private String añoNacimiento;
    private String añoMuerte;
    @ManyToOne
    private Libro libro;


    public Autor(){}

    @Override
    public String toString() {
        return
                "Id=" + Id +
                ", nombre = '" + nombre + '\'' +
                ", añoNacimiento = '" + añoNacimiento + '\'' +
                ", añoMuerte = '" + añoMuerte + '\'' +
                ", libro = " + libro;
    }

    public Autor(Long id, String nombre, String añoNacimiento, String añoMuerte, Libro libro) {
        Id = id;
        this.nombre = nombre;
        this.añoNacimiento = añoNacimiento;
        this.añoMuerte = añoMuerte;
        this.libro = libro;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAñoNacimiento() {
        return añoNacimiento;
    }

    public void setAñoNacimiento(String añoNacimiento) {
        this.añoNacimiento = añoNacimiento;
    }

    public String getAñoMuerte() {
        return añoMuerte;
    }

    public void setAñoMuerte(String añoMuerte) {
        this.añoMuerte = añoMuerte;
    }
}
