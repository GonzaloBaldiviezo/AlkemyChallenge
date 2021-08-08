package com.alkemy.challenge.controller;

import com.alkemy.challenge.dto.GenreDto;
import com.alkemy.challenge.exception.ExceptionSpring;
import com.alkemy.challenge.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @GetMapping()
    public ResponseEntity<?> getGenres() {
        try {
            Set<GenreDto> genreDtos = genreService.getGenres();

            return new ResponseEntity<>(genreDtos, OK);
        } catch (ExceptionSpring e) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGenre(@PathVariable String id) {
        try {
            GenreDto genreDto = genreService.getGenre(id);

            return new ResponseEntity<>(genreDto, OK);
        } catch (ExceptionSpring e) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, e.getStatus());
        }
    }

    @PostMapping
    public ResponseEntity<?> createGenre(@Valid @RequestBody GenreDto genreDto, BindingResult result) {
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
            genreService.create(genreDto);
            return new ResponseEntity<>(genreDto, CREATED);
        } catch (ExceptionSpring e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, e.getStatus());
        }
    }

    @PutMapping("")
    public ResponseEntity<?> updateGenre(@Valid @RequestBody GenreDto genreDto, BindingResult result) {
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
            genreService.update(genreDto);
            return new ResponseEntity<>(genreDto, OK);
        } catch (ExceptionSpring e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, e.getStatus());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGenre(@PathVariable String id) {
        try {
            genreService.deleteGenre(id);
            return new ResponseEntity<>(OK);
        } catch (ExceptionSpring e) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, e.getStatus());
        }
    }
}
