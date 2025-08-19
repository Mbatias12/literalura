package com.alura.literalura.service;

import com.alura.literalura.dto.GutendexAuthorDto;
import com.alura.literalura.dto.GutendexBookDto;
import com.alura.literalura.model.Author;
import com.alura.literalura.model.Book;
import com.alura.literalura.repository.AuthorRepository;
import com.alura.literalura.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
public class CatalogService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public CatalogService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional
    public String saveBookFromDto(GutendexBookDto dto) {
        if (dto == null) return "Libro no encontrado en la API.";
        if (dto.getId() == null) return "El libro de la API no trae id.";
        if (bookRepository.existsByGutendexId(dto.getId())) {
            return "El libro ya está registrado (id=" + dto.getId() + ").";
        }
        String lang = (dto.getLanguages() != null && !dto.getLanguages().isEmpty())
                ? dto.getLanguages().get(0).toUpperCase(Locale.ROOT)
                : "N/A";

        Book book = new Book(dto.getId(), dto.getTitle(), lang, dto.getDownloadCount());

        if (dto.getAuthors() != null) {
            for (GutendexAuthorDto a : dto.getAuthors()) {
                Author author = authorRepository
                        .findByNameAndBirthYearAndDeathYear(a.getName(), a.getBirthYear(), a.getDeathYear())
                        .orElseGet(() -> authorRepository.save(new Author(a.getName(), a.getBirthYear(), a.getDeathYear())));
                book.getAuthors().add(author);
            }
        }
        bookRepository.save(book);
        return "Libro guardado: " + book.getTitle() + " (" + book.getLanguage() + "), descargas=" + book.getDownloadCount();
    }
}