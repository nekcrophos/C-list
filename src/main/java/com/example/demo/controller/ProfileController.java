package com.example.demo.controller;

import com.example.demo.model.Rating;
import com.example.demo.model.User;
import com.example.demo.repository.RatingRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.List;
import java.util.OptionalDouble;

@Controller
public class ProfileController {

    private final UserRepository userRepo;
    private final RatingRepository ratingRepo;

    public ProfileController(UserRepository userRepo, RatingRepository ratingRepo) {
        this.userRepo = userRepo;
        this.ratingRepo = ratingRepo;
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal UserDetails ud, Model model) {
        User user = userRepo.findByEmail(ud.getUsername()).orElseThrow();
        List<Rating> ratings = ratingRepo.findByUserOrderByRatedAtDesc(user);

        long count = ratings.size();
        OptionalDouble avg = ratings.stream()
                .mapToInt(Rating::getScore)
                .average();

        // Исправление для пустого списка оценок
        String lastMovie = "—";
        if (!ratings.isEmpty() && ratings.get(0).getMovie() != null) {
            lastMovie = ratings.get(0).getMovie().getName();
        }

        model.addAttribute("username", user.getUsername());
        model.addAttribute("bio", user.getDescription());
        model.addAttribute("count", count);
        model.addAttribute("avg", avg.isPresent() ? String.format("%.2f", avg.getAsDouble()) : "—");
        model.addAttribute("lastMovie", lastMovie);
        model.addAttribute("ratings", ratings);
        return "profile";
    }
}
