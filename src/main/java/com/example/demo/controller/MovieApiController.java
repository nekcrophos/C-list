package com.example.demo.controller;

import com.example.demo.dto.MovieDto;
import com.example.demo.model.Movie;
import com.example.demo.repository.MovieRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MovieApiController {

    private final MovieRepository movieRepo;

    public MovieApiController(MovieRepository movieRepo) {
        this.movieRepo = movieRepo;
    }

    @GetMapping("/api/movies")
    public List<MovieDto> list() {
        return movieRepo.findAll().stream()
                .map(m -> new MovieDto(
                        m.getId(),
                        m.getName(),
                        m.getImdbRating(),
                        m.getGenre(),
                        m.getReleaseDate() != null ? m.getReleaseDate().getYear() : null))
                .collect(Collectors.toList());
    }
}
