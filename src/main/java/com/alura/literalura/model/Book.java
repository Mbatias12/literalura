package com.alura.literalura.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books", uniqueConstraints = {
        @UniqueConstraint(name = "uk_books_gutendex", columnNames = {"gutendex_id"})
}, indexes = {
        @Index(name = "idx_books_title", columnList = "title"),
        @Index(name = "idx_books_language", columnList = "language")
})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gutendex_id", nullable = false)
    private Long gutendexId;

    @Column(nullable = false)
    private String title;

    /** Código de dos letras: ES, EN, FR, PT */
    private String language;

    private Integer downloadCount;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors = new HashSet<>();

    public Book() {}

    public Book(Long gutendexId, String title, String language, Integer downloadCount) {
        this.gutendexId = gutendexId;
        this.title = title;
        this.language = language;
        this.downloadCount = downloadCount;
    }

    public Long getId() { return id; }
    public Long getGutendexId() { return gutendexId; }
    public void setGutendexId(Long gutendexId) { this.gutendexId = gutendexId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public Integer getDownloadCount() { return downloadCount; }
    public void setDownloadCount(Integer downloadCount) { this.downloadCount = downloadCount; }
    public Set<Author> getAuthors() { return authors; }
    public void setAuthors(Set<Author> authors) { this.authors = authors; }
}