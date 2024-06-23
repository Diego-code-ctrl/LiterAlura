package com.challange.LiterAlura;

import com.challange.LiterAlura.entity.Autor;
import com.challange.LiterAlura.entity.Lenguaje;
import com.challange.LiterAlura.entity.Libro;
import com.challange.LiterAlura.model.DatosApi;
import com.challange.LiterAlura.model.DatosAutor;
import com.challange.LiterAlura.model.DatosLibro;
import com.challange.LiterAlura.repository.AutorRepository;
import com.challange.LiterAlura.repository.LenguajeRepository;
import com.challange.LiterAlura.repository.LibroRepository;
import com.challange.LiterAlura.service.ConsumoAPI;
import com.challange.LiterAlura.service.ConvierteDatos;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.challange.LiterAlura.entity"})
@EnableJpaRepositories(basePackages = {"com.challange.LiterAlura.repository"})
public class LiterAluraApplication implements CommandLineRunner {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private LenguajeRepository lenguajeRepository;

    private Libro libro;

    public static void main(String[] args) {
        SpringApplication.run(LiterAluraApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        int op;
        String menu = """
                      Elija la opción a través de su número: 
                      1.- Buscar libro por título
                      2.- Listar libros registrados
                      3.- Listar autores registrados
                      4.- Listar autores vivos en un determinado año
                      5.- Listar libros por idioma
                      0.- Salir
                      """;

        do {

            System.out.println(menu);

            op = scanner.nextInt();
            System.out.println("");

            switch (op) {
                case 0 ->
                    System.out.println("Saliendo...");
                case 1 -> {
                    scanner.nextLine();
                    System.out.print("Ingrese el título: ");
                    String titulo = scanner.nextLine();
                    buscarLibro(titulo);
                }
                case 2 -> {
                    listarLibrosRegistrados();
                }
                case 3 -> {
                    listarAutoresRegistrados();
                }
                case 4 -> {
                    scanner.nextLine();
                    System.out.print("Ingrese la fecha: ");
                    String fecha = scanner.nextLine();
                    listarAutoresVivos(fecha);
                }
                case 5 -> {
                    scanner.nextLine();
                    System.out.print("Escoja el idioma: ");
                    String idioma = scanner.nextLine();
                    listarLibrosPorIdioma(idioma);
                }
                default -> {
                    System.out.println("Opción no válida...");
                    System.out.println("Vuelva a intentar");
                }
            }
        } while (op != 0);
    }

    public void buscarLibro(String titulo) {
        var api = new ConsumoAPI();
        ConvierteDatos conversor = new ConvierteDatos();

        titulo = titulo.replace(" ", "%20");

        String json = api.getInfoAPI("/books/?search=" + titulo);
        if (json == null) {
            System.out.println("No se pudo obtener datos de la API.");
            return;
        }

        try {
            DatosApi response = conversor.obtenerDatos(json, DatosApi.class);

            if (response.results().isEmpty()) {
                System.out.println("No se encontraron libros.");
                return;
            }

            DatosLibro datosLibro = response.results().get(0); // Obtener el primer libro encontrado

            Optional<Libro> existeLibro = libroRepository.findByTitulo(datosLibro.title());
            if (existeLibro.isPresent()) {
                System.out.println("El libro ya está en la base de datos");
            } else {
                libro = new Libro(datosLibro.title(), datosLibro.downloadCount());
                libroRepository.save(libro);
            }

            //Guarda al autor en la base de  datos
            for (DatosAutor author : datosLibro.authors()) {
                Optional<Autor> existeAutor = autorRepository.findByNombre(author.name());
                if (existeAutor.isEmpty()) {
                    Autor autor = new Autor(author.name(), author.birthYear(), author.deathYear());
                    autor.setLibro(libro);
                    autorRepository.save(autor);
                }
            }

            //Guarda el lenguaje en la base de datos
            Lenguaje lenguaje = new Lenguaje(datosLibro.languages().getFirst());
            lenguaje.setLibro(libro);
            lenguajeRepository.save(lenguaje);
            lenguaje.setLibro(libro);

            imprimirLibro(libro);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listarLibrosRegistrados() {
        List<Libro> libros = libroRepository.findAll();

        for (Libro libro : libros) {
            imprimirLibro(libro);
        }
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();

        for (Autor autor : autores) {
            System.out.println("--------------- AUTOR ---------------");
            System.out.println("Autor: " + autor.getNombre());
            System.out.println("Fecha de nacimiento: " + autor.getBirthYear());
            System.out.println("Fecha de fallecimiento: " + autor.getDeathYear());
            System.out.println("Libro: " + autor.getLibro().getTitulo());
            System.out.println("-------------------------------------");
            System.out.println("");
        }
    }

    private void listarAutoresVivos(String fecha) {
        int fechaEntera = Integer.parseInt(fecha);

        List<Autor> autores = autorRepository.findAll();

        for (Autor autor : autores) {
            if (fechaEntera >= autor.getBirthYear() && fechaEntera <= autor.getDeathYear()) {
                System.out.println("--------------- AUTOR ---------------");
                System.out.println("Nombre: " + autor.getNombre());
                System.out.println("Fecha de nacimiento: " + autor.getBirthYear());
                System.out.println("Fecha de fallecimiento: " + autor.getDeathYear());
                System.out.println("Libro: " + autor.getLibro().getTitulo());
                System.out.println("-------------------------------------");
                System.out.println("");
            }
        }
    }

    private void listarLibrosPorIdioma(String idioma) {
        List<Libro> libros = libroRepository.findAll();

        for (Libro libro : libros) {
            List<String> autores = autorRepository.findAutorLibro(libro.getTitulo());

            if (idioma.equals(lenguajeRepository.unicoLenguaje(libro.getTitulo()))) {
                System.out.println("--------------- LIBRO ---------------");
                System.out.println("Titulo: " + libro.getTitulo());
                for (String autor : autores) {
                    System.out.println("Autores: " + autor);
                }
                System.out.println("Lenguaje: " + idioma);
                System.out.println("Descargas: " + libro.getDownloadCount());
                System.out.println("-------------------------------------");
                System.out.println("");
            }
        }
    }

    private void imprimirLibro(Libro libro) {
        List<String> autores = autorRepository.findAutorLibro(libro.getTitulo());
        List<String> lenguajes = lenguajeRepository.findLenguajeLibro(libro.getTitulo());

        System.out.println("--------------- LIBRO ---------------");
        System.out.println("Titulo: " + libro.getTitulo());
        for (String autor : autores) {
            System.out.println("Autores: " + autor);
        }

        for (String lenguaje : lenguajes) {
            System.out.println("Lenguajes: " + lenguaje);
        }
        System.out.println("Descargas: " + libro.getDownloadCount());
        System.out.println("-------------------------------------");
        System.out.println("");
    }
}
