package com.netflix.movieapi.controller;

import com.netflix.movieapi.response.ResponseHandler;
import com.netflix.movieapi.entities.Titles;
import com.netflix.movieapi.dao.TitlesRepository;
import com.netflix.movieapi.view.Cast;
import com.netflix.movieapi.enums.TitleType;
import com.netflix.movieapi.view.TitleRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MovieController {

    @Autowired
    private TitlesRepository titlesRepository;

    @GetMapping("/movie")
    public ResponseEntity<List<Titles>> getMovies() {
        List<Titles> response = titlesRepository.findAll(TitleType.MOVIE.getType());
        return ResponseHandler.getResponse(response);
    };

    @GetMapping("/movie/{name}")
    public ResponseEntity<List<Titles>> getMovie(@PathVariable String name) {
        List<Titles> response = titlesRepository.findTitleByName(name, TitleType.MOVIE.getType());
        return ResponseHandler.getResponse(response);
    }

    @GetMapping("/movie/{name}/cast")
    public ResponseEntity<List<Cast>> getMovieCast(@PathVariable String name) {
        List<Cast> response = titlesRepository.findTitleCasts(name);
        return ResponseHandler.getResponse(response);
    }

    @GetMapping("/movie/{name}/rating")
    public ResponseEntity<TitleRating> getMovieRating(@PathVariable String name) {
        TitleRating response = titlesRepository.findTitleRating(name);
        return ResponseHandler.getResponse(response);
    }


}