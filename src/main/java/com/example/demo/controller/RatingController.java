package com.example.demo.controller;

import com.example.demo.dto.RatingDto;
import com.example.demo.model.Movie;
import com.example.demo.model.Rating;
import com.example.demo.model.User;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.RatingRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingRepository ratingRepo;
    private final MovieRepository movieRepo;
    private final UserRepository userRepo;

    public RatingController(RatingRepository ratingRepo,
                            MovieRepository movieRepo,
                            UserRepository userRepo) {
        this.ratingRepo = ratingRepo;
        this.movieRepo = movieRepo;
        this.userRepo = userRepo;
    }

    @PostMapping
    public ResponseEntity<?> rate(
            @AuthenticationPrincipal UserDetails ud,
            @RequestBody RatingDto dto) {

        User user = userRepo.findByEmail(ud.getUsername())
                .orElseThrow();

        // 1) точный поиск
        Optional<Movie> optMovie = movieRepo.findByName(dto.getTitle());

        // 2) если не найдено — поиск по вхождению без учёта регистра
        if (optMovie.isEmpty()) {
            List<Movie> matches = movieRepo.findByNameContainingIgnoreCase(dto.getTitle());
            if (!matches.isEmpty()) {
                optMovie = Optional.of(matches.get(0));
            }
        }

        Movie movie = optMovie.orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Фильм не найден: " + dto.getTitle()
                )
        );

        Rating rating = new Rating();
        rating.setUser(user);
        rating.setMovie(movie);
        rating.setScore(dto.getScore());
        ratingRepo.save(rating);

        return ResponseEntity.ok().build();
    }

}

