package com.alkemy.challenge.converter;

import com.alkemy.challenge.dto.CharacterDto;
import com.alkemy.challenge.dto.MovieDto;
import com.alkemy.challenge.entity.Character;
import com.alkemy.challenge.entity.Movie;
import com.alkemy.challenge.exception.ExceptionSpring;
import com.alkemy.challenge.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class CharacterConverter {

    @Autowired
    private MovieRepository movieRepository;

    public Character convertToEntity(Character character, CharacterDto characterDto) throws ExceptionSpring {
        if (characterDto.getImage() != null) {
            character.setImage(characterDto.getImage());
        }

        if (characterDto.getId() == null) {
            character.setId(characterDto.getId());
        }

        character.setName(characterDto.getName());
        character.setAge(characterDto.getAge());
        character.setWeight(characterDto.getWeight());
        character.setHistory(characterDto.getHistory());

//        if (!characterDto.getMovies().isEmpty()) {
//            character.setMovies(getMovies(characterDto.getMovies()));
//        }
        System.out.println("hola");
        return character;
    }

    public CharacterDto convertToDto(Character character, CharacterDto characterDto) throws ExceptionSpring {
        if (character.getImage() != null) {
            characterDto.setImage(character.getImage());
        }

        if (character.getId() == null) {
            characterDto.setId(character.getId());
        }

        characterDto.setName(character.getName());
        characterDto.setAge(character.getAge());
        characterDto.setWeight(character.getWeight());
        characterDto.setHistory(character.getHistory());

        if (!characterDto.getMovies().isEmpty()) {
            getMovies(characterDto.getMovies());
        }

        return characterDto;
    }


    private Set<Movie> getMovies(Set<MovieDto> movieDtos) {
        Set<Movie> movies = new HashSet<>();

        for (MovieDto movieDto : movieDtos) {
            if (movieRepository.existsByIdAndDateOffIsNull(movieDto.getId())) {
                movies.add(movieRepository.findByIdAndDateOffIsNull(movieDto.getId()));
            }
        }

        return movies;
    }
}
