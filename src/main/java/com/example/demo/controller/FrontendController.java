package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontendController {

    // Отдаёт одну и ту же страницу movie-detail.html для любого /movies/xxx
    @GetMapping("/movies/{title}")
    public String movieDetail() {
        return "movie-detail";
    }
}
