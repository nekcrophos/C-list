package com.example.demo.service;

import com.example.demo.dto.MovieCsv;
import com.example.demo.model.Movie;
import com.example.demo.repository.MovieRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class CsvLoader {
    private final MovieRepository movieRepo;

    public CsvLoader(MovieRepository movieRepo) {
        this.movieRepo = movieRepo;
    }

    @PostConstruct
    public void load() throws Exception {
        // Используем ClassLoader без ведущего слеша:
        InputStream is = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("static/data/imdb_movies.csv");

        if (is == null) {
            System.err.println("CSV файл не найден в classpath: data/imdb_movies.csv");
            return; // или бросить исключение, если критично
        }

        try (var reader = new InputStreamReader(is)) {
            CsvToBean<MovieCsv> csvToBean = new CsvToBeanBuilder<MovieCsv>(reader)
                    .withType(MovieCsv.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<MovieCsv> rows = csvToBean.parse();
            for (MovieCsv r : rows) {
                if (r.getImdbId() == null) continue;

                Movie m = new Movie();
                m.setImdbId(r.getImdbId());
                m.setName(r.getNames());
                m.setDescription(r.getDescription());
                m.setImdbRating(r.getImdbRating());
                m.setDirectors(r.getDirectors());
                m.setActors(r.getActors());
                m.setReleaseDate(r.getReleaseDate());
                m.setGenre(r.getGenre());

                movieRepo.save(m); // Hibernate сам сгенерирует id
            }
        }
    }
}
