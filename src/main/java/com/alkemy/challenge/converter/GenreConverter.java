package com.alkemy.challenge.converter;


import com.alkemy.challenge.dto.GenreDto;
import com.alkemy.challenge.entity.Genre;
import com.alkemy.challenge.exception.ExceptionSpring;
import org.springframework.stereotype.Component;


@Component
public class GenreConverter {


    public GenreDto convertToDto(GenreDto genreDto, Genre genre) throws ExceptionSpring{

        if (genre.getImage() != null) {
            genreDto.setImage(genre.getImage());
        }
        genreDto.setName(genre.getName());

        return genreDto;
    }

    public Genre convertToEntity(GenreDto genreDto, Genre genre) throws ExceptionSpring {

        if (genreDto.getImage() != null) {
            genre.setImage(genreDto.getImage());
        }
        if (genre.getId() == null) {
            genre.setId(genreDto.getId());
        }
        genre.setName(genreDto.getName());

        return genre;
    }

}
