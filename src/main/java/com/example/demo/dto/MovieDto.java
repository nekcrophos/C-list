package com.example.demo.dto;

public record MovieDto(Long id,
                       String name,
                       Double imdbRating,
                       String genre,
                       Integer year) {}
