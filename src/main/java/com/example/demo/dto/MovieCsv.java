package com.example.demo.dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import java.time.LocalDate;

public class MovieCsv {
    @CsvBindByName(column = "imdb_id")
    private Long imdbId;

    @CsvBindByName(column = "name")
    private String names;

    @CsvBindByName(column = "description")
    private String description;

    @CsvBindByName(column = "imdb_rating")
    private Double imdbRating;

    @CsvBindByName(column = "directors")
    private String directors;

    @CsvBindByName(column = "actors")
    private String actors;

    @CsvBindByName(column = "release_date")
    @CsvDate("yyyy-MM-dd")
    private LocalDate releaseDate;

    @CsvBindByName(column = "genre")
    private String genre;

    // getters and setters

    public Long getImdbId() {
        return imdbId;
    }

    public String getNames() {
        return names;
    }

    public String getDescription() {
        return description;
    }

    public Double getImdbRating() {
        return imdbRating;
    }

    public String getDirectors() {
        return directors;
    }

    public String getActors() {
        return actors;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public String getGenre() {
        return genre;
    }

    public void setImdbId(Long imdbId) {
        this.imdbId = imdbId;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImdbRating(Double imdbRating) {
        this.imdbRating = imdbRating;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
