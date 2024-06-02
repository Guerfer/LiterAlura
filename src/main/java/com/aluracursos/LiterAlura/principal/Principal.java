package com.aluracursos.LiterAlura.principal;

import com.aluracursos.LiterAlura.model.*;
import com.aluracursos.LiterAlura.repository.ILibroRepository;
import com.aluracursos.LiterAlura.service.ConsumoAPI;
import com.aluracursos.LiterAlura.service.ConvierteDatos;
import com.aluracursos.LiterAlura.service.IConvierteDatos;


import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos convertidor = new ConvierteDatos();
    private ILibroRepository repositorio;
    private List<Libro> libros;
    private Optional<Libro> libroBuscado;
    private List<DatosLibro> datosLibros = new ArrayList<>();


    public Principal(ILibroRepository repository) {
        this.repositorio = repository;
    }

    /// Menu ///
    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            System.out.println("\n*************************************************************************************");
            var menu = """ 
                    1- Buscar libro por titulo
                    2- Validar libros registrados
                    3- Validar autores de los libros registrados
                    4- Validar autores vivos por determinada cantidad de años
                    5- Validar libros por idioma
                                        
                    0- Salir
                    """;
            System.out.println("\n****************** - Bienvenido a LiterAlura - ******************");
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    validarLibrosRegistrados();
                    break;
                case 3:
                    validarAutoresLibros();
                    break;
                case 4:
                    validarPorAutoresVivos();
                    break;
                case 5:
                    validarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación");
                    break;
                default:
                    System.out.println("Opción invalida, intente nuevamente");
            }
        }
    }
    ///// funcion para deserializar /////
    private DatosLibro getDatosLibro() {
        System.out.println("Por favor escribe el nombre del libro que deseas buscar");
        var nombreLibro = teclado.nextLine().trim(); // se usa para eliminae espacios en blanco alrededor
        // validar que el ingreso no este vacio
        if (nombreLibro.isEmpty()) {
            System.out.println("El nombre de libro es inválido. Inténtalo nuevamente.");
            return null;
        }
        // Obtener datos desde la API
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
        var datosBusqueda = convertidor.obtenerDatos(json, Datos.class);
        // traemos en primer libro de la busqueda
        Optional<DatosLibro> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
                .findFirst();
        if (libroBuscado.isPresent()) {
            System.out.println("*** Libro encontrado ***");
            System.out.println(libroBuscado.get());
            return libroBuscado.get(); // Retorna el libro encontrado
        } else {
            System.out.println("El libro no se encontró.");
            return null;
        }
    }
    // case 1 //
    private void buscarLibro() {
        System.out.println("\n****************** - LiterAlura - ******************");
        DatosLibro datos = getDatosLibro();
        Libro libro = new Libro(datos);
        repositorio.save(libro);
        // mensaje de confirmacion //
        System.out.println("****** - Libro guardado exitosamente - ******");
    }
    // case 2 //
    private void validarLibrosRegistrados() {
        System.out.println("\n****************** - LiterAlura - ******************");
        libros = repositorio.findAll();

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros registrados.");
        } else {
            System.out.println("\n******* Libros registrados *******");
            libros.stream()
                    .sorted(Comparator.comparing(Libro::getAutores))
                    .forEach(System.out::println);
        }
    }
    // case 3 //
    private void validarAutoresLibros() {
        validarLibrosRegistrados();
        System.out.println("\n****************** - LiterAlura - ******************");
        System.out.println("Por favor, escribe el nombre del libro del cual quieres saber su autor:");
        var nombreLibro = teclado.nextLine();

        Optional<Libro> libro = libros.stream()
                .filter(l -> l.getTitulo().toLowerCase().contains(nombreLibro.toLowerCase()))
                .findFirst();

            if (libro.isPresent()) {
                var libroEncontrado = libro.get();
                System.out.println("\n******* Autor encontrado *******");
                System.out.println("Autor: " + libroEncontrado.getAutores());

            // se crear la instancia de Autor y se reemplaza con los datos del autor real
                Autor autor = new Autor();
                autor.setNombre("Nombre del autor"); // Establece el nombre del autor

            // agrega el autor al libro
                libroEncontrado.getAutores();

            // guarda los datos del libre y del autor
                repositorio.save(libroEncontrado);
                } else {
                    System.out.println("No se encontró el libro con el título especificado.");
                }
            }
    // case 4 //
    private void validarPorAutoresVivos() {
        System.out.println("\n****************** - LiterAlura - ******************");
        System.out.println("Por favor, ingresa el año inicial de la búsqueda (por ejemplo, 1800):");
        var añoInicial = teclado.nextInt();
        System.out.println("Por favor, ingresa el año final de la búsqueda (por ejemplo, 1900):");
        var añoFinal = teclado.nextInt();

        // manejo de excepciones para validar los valores ingresados
        if (añoInicial > añoFinal) {
            System.out.println("El año inicial debe ser menor o igual al año final.");
            return;
        }
        // consulta a la base de datos para obtener los autores que vivieron en el rango especificado
        List<Autor> autoresEnRango = repositorio.findAutoresVivosEnRango(añoInicial, añoFinal);

        if (autoresEnRango.isEmpty()) {
            System.out.println("No se encontraron autores que vivieron entre los años " + añoInicial + " y " + añoFinal + ".");
        } else {
            System.out.println("\n******* Autores encontrados *******");
            autoresEnRango.forEach(autor -> {
                System.out.println("Nombre del autor: " + autor.getNombre());
            });
        }
    }
    // case 5 //
    private void validarLibrosPorIdioma() {
        System.out.println("\n****************** - LiterAlura - ******************");
        System.out.println("""
          Por favor, escribe la inicial del idioma para buscar los libros, ejemplo (es):
                                
          es - Español
          en - Inglés
          fr - Francés
          pt - Portugués
          """);
        var idioma = teclado.nextLine().trim();
        // Filtra los libros por idioma
        List<Libro> librosPorIdioma = repositorio.findByIdiomasIgnoreCase(idioma);

        if (librosPorIdioma.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma especificado: " + idioma);
        } else {
            System.out.println("\n******* Libros en " + idioma + " *******");
            librosPorIdioma.forEach(System.out::println);
        }
    }
}
