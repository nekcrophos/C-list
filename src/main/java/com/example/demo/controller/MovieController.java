package com.example.demo.controller;

import com.example.demo.model.Movie;
import com.example.demo.repository.MovieRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/movies")
public class MovieController {
    private final MovieRepository movieRepo;
    public MovieController(MovieRepository movieRepo) {
        this.movieRepo = movieRepo;
    }

    @GetMapping
    public String list(Model model) {
        List<Movie> movies = movieRepo.findAll();
        model.addAttribute("movies", movies);
        return "movies";
    }

//    @GetMapping("/{id}")
//    public String detail(@PathVariable Long id, Model model) {
//        Movie m = movieRepo.findById(id).orElseThrow();
//        model.addAttribute("movie", m);
//        return "movie-detail";
//    }
}
