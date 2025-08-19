package com.alura.literalura.repository;

import com.alura.literalura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByGutendexId(Long gutendexId);
    Optional<Book> findByGutendexId(Long gutendexId);
    List<Book> findByLanguageIgnoreCase(String language);
}