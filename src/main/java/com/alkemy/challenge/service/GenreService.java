package com.alkemy.challenge.service;

import com.alkemy.challenge.dto.GenreDto;
import com.alkemy.challenge.entity.Genre;
import com.alkemy.challenge.exception.ExceptionSpring;
import com.alkemy.challenge.repository.GenreRepository;
import com.alkemy.challenge.converter.GenreConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private GenreConverter genreConverter;

    public String generateId() {
        String id;
        do {
            id = UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0, 15);
        } while (genreRepository.existsById(id));
        return id;
    }

    public GenreDto create(GenreDto genreDto) throws ExceptionSpring {
        try {
            String id = generateId();
            genreDto.setId(id);
            Genre genre = genreConverter.convertToEntity(genreDto, new Genre());
            genreRepository.save(genre);
            return genreConverter.convertToDto(new GenreDto(), genre);

        } catch (ExceptionSpring e) {
            throw e;
        } catch (Exception e) {
            throw new ExceptionSpring("Error in server");
        }

    }

    public GenreDto update(GenreDto genreDto) throws ExceptionSpring {
        try {
            Genre genre = genreRepository.findByIdAndDateOffIsNull(genreDto.getId());

            if (genre == null) {
                throw new ExceptionSpring("Genre not found", HttpStatus.NOT_FOUND);
            }
            genre = genreConverter.convertToEntity(genreDto, genre);
            genreRepository.save(genre);
            return genreConverter.convertToDto(genreDto, genre);
        } catch (ExceptionSpring e) {
            throw e;
        } catch (Exception e) {
            throw new ExceptionSpring("Error in server");
        }

    }


    public Set<GenreDto> getGenres() throws ExceptionSpring {

        try {
            Set<GenreDto> genreDtos = new HashSet<>();
            for (Genre genre : genreRepository.findAllByDateOffIsNull()) {
                genreDtos.add(genreConverter.convertToDto(new GenreDto(), genre));
            }
            return genreDtos;
        } catch (Exception e) {
            throw new ExceptionSpring("Error in server");
        }
    }

    public GenreDto getGenre(String id) throws ExceptionSpring {

        try {
            Genre genre = genreRepository.findByIdAndDateOffIsNull(id);

            if (genre == null) {
                throw new ExceptionSpring("There is no genre in database", HttpStatus.NOT_FOUND);
            }
            return genreConverter.convertToDto(new GenreDto(), genre);
        } catch (ExceptionSpring e) {
            throw e;
        } catch (Exception e) {
            throw new ExceptionSpring("Error in server");
        }
    }

    public void deleteGenre(String id) throws ExceptionSpring {

        try {
            Genre genre = genreRepository.findByIdAndDateOffIsNull(id);

            if (genre == null) {
                throw new ExceptionSpring("There is no genre in database", HttpStatus.NOT_FOUND);
            }

            genre.setDateOff(LocalDate.now());
            genreRepository.save(genre);
        } catch (ExceptionSpring e) {
            throw e;
        } catch (Exception e) {
            throw new ExceptionSpring("Error in server");
        }
    }


}
