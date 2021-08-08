package com.alkemy.challenge.controller;

import com.alkemy.challenge.dto.CharacterDto;
import com.alkemy.challenge.entity.Character;
import com.alkemy.challenge.exception.ExceptionSpring;
import com.alkemy.challenge.repository.CharacterRepository;
import com.alkemy.challenge.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/characters")
public class CharacterController {

    @Autowired
    CharacterRepository characterRepository;

    @Autowired
    CharacterService characterService;

    @GetMapping
    public ResponseEntity<?> getCharacters() {
        try {
            Set<CharacterDto> characters = characterService.getCharacters();
            return new ResponseEntity(characters, HttpStatus.OK);

        } catch (ExceptionSpring e) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCharacter(@PathVariable String id) {
        try {
            CharacterDto character = characterService.getCharacter(id);
            return new ResponseEntity(character, HttpStatus.OK);

        } catch (ExceptionSpring e) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> createCharacter(@Valid @RequestBody CharacterDto characterDto, BindingResult result) {
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : result.getFieldErrors()) {
                errors.add(fieldError.getDefaultMessage());
            }

            response.put("errors", errors);
            return new ResponseEntity(response, BAD_REQUEST);
        }

        try {
            characterService.createCharacter(characterDto);
            return new ResponseEntity(characterDto, CREATED);
        } catch (ExceptionSpring e) {
            response.put("error", e.getMessage());
            return new ResponseEntity(response, e.getStatus());
        }

    }

    @PutMapping
    public ResponseEntity<?> updateCharacter(@Valid @RequestBody CharacterDto characterDto, BindingResult result) {
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : result.getFieldErrors()) {
                errors.add(fieldError.getDefaultMessage());
            }

            response.put("errors", errors);
            return new ResponseEntity(response, BAD_REQUEST);
        }

        try {
            characterService.updateCharacter(characterDto);
            return new ResponseEntity(characterDto, CREATED);
        } catch (ExceptionSpring e) {
            response.put("error", e.getMessage());
            return new ResponseEntity(response, e.getStatus());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCharacter(@PathVariable String id) {
        try {
            characterService.deleteCharacter(id);
            return new ResponseEntity(OK);
        } catch (ExceptionSpring e) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity(response, e.getStatus());
        }
    }

    @GetMapping("/resume")
    public ResponseEntity<?> getCharactersResume() {
        try {
            Set<CharacterDto> characters = characterService.getCharactersResumeDto();
            return new ResponseEntity(characters, HttpStatus.OK);

        } catch (ExceptionSpring e) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getCharacterByName(@PathVariable String name) {
        try {
            Set<CharacterDto> characters = characterService.getCharacterByName(name);
            return new ResponseEntity(characters, HttpStatus.OK);

        } catch (ExceptionSpring e) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{age}")
    public ResponseEntity<?> getCharacterByAge(@PathVariable int age) {
        try {
            Set<CharacterDto> characters = characterService.getCharactersByAge(age);
            return new ResponseEntity(characters, HttpStatus.OK);

        } catch (ExceptionSpring e) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/movie/{id}")
    public ResponseEntity<?> getCharacterByMovie(@PathVariable String id) {
        try {
            Set<CharacterDto> characters = characterService.getCharactersByMovie(id);
            return new ResponseEntity(characters, HttpStatus.OK);

        } catch (ExceptionSpring e) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
