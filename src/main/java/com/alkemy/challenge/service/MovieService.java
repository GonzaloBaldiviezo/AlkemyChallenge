package com.alkemy.challenge.service;


import com.alkemy.challenge.converter.MovieConverter;
import com.alkemy.challenge.dto.GenreDto;
import com.alkemy.challenge.dto.MovieDto;
import com.alkemy.challenge.entity.Character;
import com.alkemy.challenge.entity.Genre;
import com.alkemy.challenge.entity.Movie;
import com.alkemy.challenge.exception.ExceptionSpring;
import com.alkemy.challenge.repository.CharacterRepository;
import com.alkemy.challenge.repository.GenreRepository;
import com.alkemy.challenge.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.*;


@Service
@Transactional
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    CharacterRepository characterRepository;

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    MovieConverter movieConverter;

    public String generateId() {
        String id;
        do {
            id = UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0, 15);
        } while (movieRepository.existsById(id));
        return id;
    }

    public MovieDto createMovie(MovieDto movieDto) throws ExceptionSpring {

        try {

            String id = generateId();
            movieDto.setId(id);

            Movie movie = movieConverter.convertToEntity(movieDto);

//            for (String id : listIdCharacters) {
//                Character character = characterRepository.findByIdAndDateOffIsNull(id);
//
//                if (character == null) {
//                    throw new ExceptionSpring("Movie not found");
//                }
//
//                movie.getCharacters().add(character);
//            }
//            Genre genre = genreRepository.findByIdAndDateOffIsNull(movieDto.getGenreId());
//            if (genre != null) {
//                genre.getMovies().add(movie);
//                genreRepository.save(genre);
//            }
//            movie.setGenre(genre);
            movieRepository.save(movie);
            return movieDto;
        } catch (ExceptionSpring e) {
            throw e;
        } catch (Exception e) {
            throw new ExceptionSpring("Error in server");
        }
    }

    public Movie updateMovies(MovieDto movieDto, String idGenre, MultipartFile image, Set<String> listIdCharacters) throws ExceptionSpring {

        try {
            Movie movie = movieRepository.findByTitleAndDateOffIsNull(movieDto.getTitle());

            if (movie == null) {
                throw new ExceptionSpring("Movie not found");
            }

            movie.setTitle(movieDto.getTitle());
            movie.setCreation(movieDto.getCreation());
            movie.setRating(movieDto.getRating());
            movie.setImage(movieDto.getImage());


            for (String id : listIdCharacters) {
                Character character = characterRepository.findByIdAndDateOffIsNull(id);

                if (character == null) {
                    throw new ExceptionSpring("Character not found");
                }

                movie.getCharacters().add(character);
            }
            Genre genre = genreRepository.findByIdAndDateOffIsNull(idGenre);
            if (genre == null) {
                throw new ExceptionSpring("Genre not found");
            }
            movie.setGenre(genre);

            return movie;
        } catch (ExceptionSpring e) {
            throw e;
        } catch (Exception e) {
            throw new ExceptionSpring("Error in server");
        }
    }

    public Set<MovieDto> getMovies() throws ExceptionSpring {
        try {
            Set<Movie> movies = movieRepository.findAllByDateOffIsNull();
            System.out.println("hola");
            Set<MovieDto> moviesDto = new HashSet<>();
            if (movies.isEmpty()) {
                throw new ExceptionSpring("There are no movies in the database");
            }

            for (Movie movie : movies) {
                moviesDto.add(movieConverter.convertToDto(movie));
            }

            return moviesDto;

        } catch (ExceptionSpring e) {
            throw e;
        } catch (Exception e) {
            throw new ExceptionSpring("Error in server");
        }
    }


    public Movie getMovie(String id) throws ExceptionSpring {
        try {
            Movie movie = movieRepository.findByIdAndDateOffIsNull(id);

            if (movie == null) {
                throw new ExceptionSpring("Movie in the database");
            }


            return movie;

        } catch (ExceptionSpring e) {
            throw e;
        } catch (Exception e) {
            throw new ExceptionSpring("Error in server");
        }
    }

    public void deleteMovie(String id) throws ExceptionSpring {
        try {
            Movie movie = movieRepository.findByIdAndDateOffIsNull(id);

            if (movie == null) {
                throw new ExceptionSpring("Character not found");
            }

            movie.setDateOff(LocalDate.now());
            movieRepository.save(movie);
        } catch (ExceptionSpring e) {
            throw e;
        } catch (Exception e) {
            throw new ExceptionSpring("Error in server");
        }
    }

    public Movie getMovieByTitle(String title) throws ExceptionSpring {
        try {
            Movie movie = movieRepository.findByTitleAndDateOffIsNull(title);

            if (movie == null) {
                throw new ExceptionSpring("Movie in the database");
            }

            return movie;

        } catch (ExceptionSpring e) {
            throw e;
        } catch (Exception e) {
            throw new ExceptionSpring("Error in server");
        }
    }

    public Set<Movie> getMoviesByGenre(String id) throws ExceptionSpring {
        try {

            Genre genre = genreRepository.findByIdAndDateOffIsNull(id);

            if (genre == null) {
                throw new ExceptionSpring("Genre not found in the database");

            }
            Set<Movie> movies = new HashSet<>();

            for (Movie movie : genre.getMovies()) {
                movies.add(movie);
            }

            return movies;

        } catch (ExceptionSpring e) {
            throw e;
        } catch (Exception e) {
            throw new ExceptionSpring("Error in server");
        }
    }


    public List<Movie> getMoviesOrderByTitle(String order) throws ExceptionSpring {
        try {
            List<Movie> movies = new ArrayList<>();

            if (order.equalsIgnoreCase("asc")) {
                movies.addAll(movieRepository.findAllByOrderByTitleAsc());
            } else if (order.equalsIgnoreCase("desc")) {
                movies.addAll(movieRepository.findAllByOrderByTitleDesc());
            } else {
                throw new ExceptionSpring("Wrong Search");
            }

            return movies;
        } catch (ExceptionSpring e) {
            throw e;
        } catch (Exception e) {
            throw new ExceptionSpring("Error in server");
        }
    }

}
