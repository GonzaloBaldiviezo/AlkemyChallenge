
package com.alkemy.challenge.converter;

import com.alkemy.challenge.dto.GenreDto;
import com.alkemy.challenge.dto.MovieDto;
import com.alkemy.challenge.entity.Genre;
import com.alkemy.challenge.entity.Movie;
import com.alkemy.challenge.exception.ExceptionSpring;
import com.alkemy.challenge.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MovieConverter {

    @Autowired
    GenreConverter genreConverter;

    @Autowired
    GenreRepository genreRepository;

    public MovieDto convertToDto(Movie movie) throws ExceptionSpring {
        MovieDto movieDto = new MovieDto();

        movieDto.setTitle(movie.getTitle());
        movieDto.setCreation(movie.getCreation());
        movieDto.setImage(movie.getImage());
        movieDto.setRating(movie.getRating());
        movieDto.setGenreId(movie.getGenre().getId());

        return movieDto;
    }

    public Movie convertToEntity(MovieDto movieDto) throws ExceptionSpring {
        Movie movie = new Movie();

        movie.setId(movieDto.getId());
        movie.setTitle(movieDto.getTitle());
        movie.setCreation(movieDto.getCreation());
        movie.setImage(movieDto.getImage());
        movie.setRating(movieDto.getRating());
        movie.setGenre(genreRepository.findByIdAndDateOffIsNull(movieDto.getGenreId()));
//        movie.setGenre(genreConverter.convertToEntity(genreConverter.convertToDto(new GenreDto(), genreRepository.findByIdAndDateOffIsNull(movieDto.getGenreId())), new Genre()));
        if (movie.getGenre() == null) {
            throw new ExceptionSpring("Genre not found");
        }


        return movie;
    }
    
}
