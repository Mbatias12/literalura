package com.alura.literalura;

import com.alura.literalura.dto.GutendexBookDto;
import com.alura.literalura.model.Author;
import com.alura.literalura.model.Book;
import com.alura.literalura.repository.AuthorRepository;
import com.alura.literalura.repository.BookRepository;
import com.alura.literalura.service.CatalogService;
import com.alura.literalura.service.GutendexService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class App implements CommandLineRunner {

    private final GutendexService gutendexService;
    private final CatalogService catalogService;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public App(GutendexService gutendexService, CatalogService catalogService,
               BookRepository bookRepository, AuthorRepository authorRepository) {
        this.gutendexService = gutendexService;
        this.catalogService = catalogService;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== LiterAlura - Catálogo de libros (Java/Spring/PostgreSQL) ===");

        while (true) {
            printMenu();
            int op = readInt(sc, "Elige una opción: ");
            switch (op) {
                case 0 -> { System.out.println("¡Hasta luego!"); return; }
                case 1 -> buscarYGuardarLibro(sc);
                case 2 -> listarLibros();
                case 3 -> listarAutores();
                case 4 -> listarAutoresVivosEnAnio(sc);
                case 5 -> listarLibrosPorIdioma(sc);
                default -> System.out.println("Opción inválida.");
            }
            System.out.println();
        }
    }

    private void buscarYGuardarLibro(Scanner sc) {
        System.out.print("Ingresa el título a buscar: ");
        String titulo = sc.nextLine().trim();
        if (titulo.isEmpty()) { System.out.println("Título vacío."); return; }
        GutendexBookDto dto = gutendexService.searchFirstByTitle(titulo).block();
        String msg = catalogService.saveBookFromDto(dto);
        System.out.println(msg);
        if (dto != null && dto.getAuthors() != null) {
            System.out.println("Autores:");
            dto.getAuthors().forEach(a -> System.out.println(" - " + a.getName() + " (" + a.getBirthYear() + " - " + a.getDeathYear() + ")"));
        }
    }

    private void listarLibros() {
        List<Book> books = bookRepository.findAll();
        if (books.isEmpty()) { System.out.println("No hay libros registrados."); return; }
        books.forEach(b -> System.out.println(
                "#%d [%s] "%s" - descargas=%d - autores=%d".formatted(
                        b.getId(), b.getLanguage(), b.getTitle(),
                        b.getDownloadCount() == null ? 0 : b.getDownloadCount(),
                        b.getAuthors().size()
                )
        ));
    }

    private void listarAutores() {
        List<Author> authors = authorRepository.findAll();
        if (authors.isEmpty()) { System.out.println("No hay autores registrados."); return; }
        authors.forEach(a -> System.out.println(
                "%s (nac=%s, def=%s) - libros=%d".formatted(
                        a.getName(),
                        a.getBirthYear() == null ? "?" : a.getBirthYear(),
                        a.getDeathYear() == null ? "?" : a.getDeathYear(),
                        a.getBooks().size()
                )
        ));
    }

    private void listarAutoresVivosEnAnio(Scanner sc) {
        int year = readInt(sc, "Año (ej. 1600): ");
        List<Author> alive = authorRepository.findAliveInYear(year);
        if (alive.isEmpty()) { System.out.println("No se encontraron autores vivos en " + year + "."); return; }
        alive.forEach(a -> System.out.println(a.getName() + " (" + a.getBirthYear() + " - " + a.getDeathYear() + ")"));
    }

    private void listarLibrosPorIdioma(Scanner sc) {
        System.out.print("Idioma (ES/EN/FR/PT): ");
        String lang = sc.nextLine().trim().toUpperCase();
        if (!(lang.equals("ES") || lang.equals("EN") || lang.equals("FR") || lang.equals("PT"))) {
            System.out.println("Código inválido. Usa ES, EN, FR o PT.");
            return;
        }
        List<Book> books = bookRepository.findByLanguageIgnoreCase(lang);
        if (books.isEmpty()) { System.out.println("No hay libros en " + lang + "."); return; }
        books.forEach(b -> System.out.println("[%s] "%s" - descargas=%d".formatted(
                b.getLanguage(), b.getTitle(), b.getDownloadCount() == null ? 0 : b.getDownloadCount()
        )));
    }

    private void printMenu() {
        System.out.println(
                "-------------------------------\n" +
                "[1] Buscar libro por título (API) y guardar\n" +
                "[2] Listar libros registrados\n" +
                "[3] Listar autores registrados\n" +
                "[4] Listar autores vivos en un año\n" +
                "[5] Listar libros por idioma (ES/EN/FR/PT)\n" +
                "[0] Salir\n" +
                "-------------------------------"
        );
    }

    private int readInt(Scanner sc, String prompt) {
        System.out.print(prompt);
        while (!sc.hasNextInt()) { sc.nextLine(); System.out.print("Ingresa un número: "); }
        int value = sc.nextInt();
        sc.nextLine(); // consume newline
        return value;
    }
}