package com.alura.literalura.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class GutendexBookDto {
    private Long id;
    private String title;
    private java.util.List<GutendexAuthorDto> authors;
    private java.util.List<String> languages;
    @JsonProperty("download_count")
    private Integer downloadCount;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public java.util.List<GutendexAuthorDto> getAuthors() { return authors; }
    public void setAuthors(java.util.List<GutendexAuthorDto> authors) { this.authors = authors; }
    public java.util.List<String> getLanguages() { return languages; }
    public void setLanguages(java.util.List<String> languages) { this.languages = languages; }
    public Integer getDownloadCount() { return downloadCount; }
    public void setDownloadCount(Integer downloadCount) { this.downloadCount = downloadCount; }
}