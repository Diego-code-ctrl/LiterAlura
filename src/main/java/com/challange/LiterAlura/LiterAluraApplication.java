package com.challange.LiterAlura;

import com.challange.LiterAlura.entity.Autor;
import com.challange.LiterAlura.model.DatosApi;
import com.challange.LiterAlura.model.DatosAutor;
import com.challange.LiterAlura.model.DatosLibro;
import com.challange.LiterAlura.repository.AutorRepository;
import com.challange.LiterAlura.service.ConsumoAPI;
import com.challange.LiterAlura.service.ConvierteDatos;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Optional;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {
    
    @Autowired
    private AutorRepository autorRepository;
    
//    @Autowired
//    private LibroRepository libroRepository;

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
                    listarLibrosPorTitulo();
                }
                case 3 -> {
                    listarAutoresRegistrados();
                }
                case 4 -> {
                    listarAutoresVivos();
                }
                case 5 -> {
                    listarLibrosPorIdioma();
                }
                default -> {
                    System.out.println("Opción no válida...");
                    System.out.println("Vuelva a intentar");
                }

            }
        } while (op != 0);
    }

    private void buscarLibro(String titulo) throws JsonProcessingException {
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
            System.out.println("response = " + response);

            if (response.results().isEmpty()) {
                System.out.println("No se encontraron libros.");
                return;
            }

            DatosLibro libro = response.results().get(0); // Obtener el primer libro encontrado

            System.out.println("------------ LIBRO ------------");
            System.out.println("Título: " + libro.title());
            System.out.print("Autores: ");
            for (DatosAutor author : libro.authors()) {
                System.out.print(author.name() + "\n\t");
                Optional<Autor> existeAutor = autorRepository.findByName(author.name());
                if (existeAutor.isEmpty()) {
                    autorRepository.save(new Autor(author));
                }
            }
            System.out.println("Idiomas: " + String.join(", ", libro.languages()));
            System.out.println("Número de descargas: " + libro.downloadCount());
            System.out.println("-------------------------------");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listarLibrosPorTitulo() {

    }

    private void listarAutoresRegistrados() {
        
    }

    private void listarAutoresVivos() {

    }

    private void listarLibrosPorIdioma() {

    }
}
