package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // << Hibernate сам проставляет ID
    private Long id;

    @Column(name = "imdb_id", unique = true, nullable = false)
    private Long imdbId;

    @Column(nullable = false)
    private String name;

    @Column(length = 2000)
    private String description;

    private Double imdbRating;
    private String directors;
    private String actors;
    private LocalDate releaseDate;
    private String genre;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Rating> ratings = new HashSet<>();

    // getters/setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public Set<Rating> getRatings() {
        return ratings;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }

    public Long getImdbId() {
        return imdbId;
    }

    public void setImdbId(Long imdbId) {
        this.imdbId = imdbId;
    }
}