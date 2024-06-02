package com.aluracursos.LiterAlura.model;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "Libros")
public class Libro {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //// estos @ son para la base de datos ////
    private Long Id;
    @Column(unique = true)
    private String titulo;
    private String autores;
//    @Enumerated(EnumType.STRING)
    private String idiomas;
    private double numeroDescargas;
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Autor> autor = new ArrayList<>();

    public Libro(){}

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.autores = String.valueOf(datosLibro.autor());
        this.idiomas = String.valueOf(datosLibro);
        this.numeroDescargas = datosLibro.numeroDescargas();
    }
    @Override
    public String toString() {
        return
                "Id = " + Id +
                        ", titulo = '" + titulo + '\'' +
                        ", autores = '" + autor + '\'' +
                        ", idiomas = '" + idiomas + '\'' +
                        ", numeroDescargas = " + numeroDescargas;
    }

    public Libro(Long id, String titulo, String autores, List<String> idiomas, double numeroDescargas, List<Autor> autor) {
        Id = id;
        this.titulo = titulo;
        this.autores = autores;
        this.idiomas = idiomas.toString();
        this.numeroDescargas = numeroDescargas;
        this.autor = autor;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas.toString();
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public List<Autor> getAutor() {
        return autor;
    }

    public void setAutor(List<Autor> autor) {
        autor.forEach(a -> a.setLibro(this));
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutores() {
        return autores;
    }

    public void setAutores(String autores) {
        this.autores = autores;
    }

    public String getIdiomas() {
        return idiomas.toString();
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = Collections.singletonList(idiomas).toString();
    }

    public double getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(double numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }


}
