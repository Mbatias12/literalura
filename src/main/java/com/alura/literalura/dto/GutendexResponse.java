package com.alura.literalura.dto;

import java.util.List;

public class GutendexResponse {
    private Integer count;
    private String next;
    private String previous;
    private java.util.List<GutendexBookDto> results;

    public Integer getCount() { return count; }
    public void setCount(Integer count) { this.count = count; }
    public String getNext() { return next; }
    public void setNext(String next) { this.next = next; }
    public String getPrevious() { return previous; }
    public void setPrevious(String previous) { this.previous = previous; }
    public java.util.List<GutendexBookDto> getResults() { return results; }
    public void setResults(java.util.List<GutendexBookDto> results) { this.results = results; }
}