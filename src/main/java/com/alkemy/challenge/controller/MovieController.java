package com.alkemy.challenge.controller;


import com.alkemy.challenge.dto.GenreDto;
import com.alkemy.challenge.dto.MovieDto;
import com.alkemy.challenge.exception.ExceptionSpring;
import com.alkemy.challenge.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping
    public ResponseEntity<?> createMovie(@Valid @RequestBody MovieDto movieDto, BindingResult result) {
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : result.getFieldErrors()) {
                errors.add(fieldError.getDefaultMessage());
            }

            response.put("errors", errors);
            return new ResponseEntity<>(response, BAD_REQUEST);
        }

        try {
            movieService.createMovie(movieDto);
            return new ResponseEntity<>(movieDto, CREATED);
        } catch (ExceptionSpring e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, e.getStatus());
        }
    }

    @GetMapping()
    public ResponseEntity<?> getMovies() {
        try {
            Set<MovieDto> movieDtos = movieService.getMovies();

            return new ResponseEntity<>(movieDtos, OK);
        } catch (ExceptionSpring e) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, INTERNAL_SERVER_ERROR);
        }
    }
}
