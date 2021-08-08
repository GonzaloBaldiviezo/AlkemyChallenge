package com.alkemy.challenge.service;

import com.alkemy.challenge.dto.CharacterDto;
import com.alkemy.challenge.dto.MovieDto;
import com.alkemy.challenge.entity.Character;
import com.alkemy.challenge.entity.Movie;
import com.alkemy.challenge.exception.ExceptionSpring;
import com.alkemy.challenge.repository.CharacterRepository;
import com.alkemy.challenge.repository.MovieRepository;
import com.alkemy.challenge.converter.CharacterConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class CharacterService {

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CharacterConverter characterConverter;
    
    public String generateId() {
        String id;
        do {
            id = UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0, 15);
        } while (characterRepository.existsById(id));
        return id;
    }

    public CharacterDto createCharacter(CharacterDto characterDto) throws ExceptionSpring {
        try {
            String id = generateId();
            characterDto.setId(id);

            Character character = characterConverter.convertToEntity(new Character(), characterDto);

//            if (!characterDto.getMovies().isEmpty()) {
//                for (MovieDto movieDto : characterDto.getMovies()) {
//                    Movie movie = movieRepository.findByTitleAndDateOffIsNull(movieDto.getTitle());
//
//                    if (movie == null) {
//                        throw new ExceptionSpring("Movie not found", HttpStatus.NOT_FOUND);
//                    }
//
//                    character.getMovies().add(movie);
//                }
//            }

            System.out.println("hola2");
            characterRepository.save(character);
            return characterDto;
        } catch (ExceptionSpring e) {
            throw e;
        } catch (Exception e) {
            throw new ExceptionSpring("Error in server");
        }
    }

    public CharacterDto updateCharacter(CharacterDto characterDto) throws ExceptionSpring {
        try {
            Character character = characterRepository.findByIdAndDateOffIsNull(characterDto.getId());

            if (character == null) {
                throw new ExceptionSpring("Character not found", HttpStatus.NOT_FOUND);
            }

            for (MovieDto movieDto : characterDto.getMovies()) {
                Movie movie = movieRepository.findByTitleAndDateOffIsNull(movieDto.getTitle());

                if (movie == null) {
                    throw new ExceptionSpring("Movie not found", HttpStatus.NOT_FOUND);
                }

                character.getMovies().add(movie);
            }
            characterRepository.save(character);
            return characterDto;
        } catch (ExceptionSpring e) {
            throw e;
        } catch (Exception e) {
            throw new ExceptionSpring("Error in server");
        }
    }

    public Set<CharacterDto> getCharacters() throws ExceptionSpring {
        try {
            Set<CharacterDto> characterDtos = new HashSet<>();

            for (Character character : characterRepository.findByDateOffIsNull()) {
                characterDtos.add(characterConverter.convertToDto(character, new CharacterDto()));
            }

            if (characterDtos.isEmpty()) {
                throw new ExceptionSpring("There are no characters in the database");
            }

            return characterDtos;
        } catch (ExceptionSpring e) {
            throw e;
        } catch (Exception e) {
            throw new ExceptionSpring("Error in server");
        }
    }

    public Set<CharacterDto> getCharactersResumeDto() throws ExceptionSpring {
        try {
            Set<Character> characters = characterRepository.findByDateOffIsNull();
            Set<CharacterDto> characterResumeDtos = new HashSet<>();

            if (characters.isEmpty()) {
                throw new ExceptionSpring("There are no characters in the database");
            }

            for (Character character : characters) {
                CharacterDto characterDto = new CharacterDto();
                characterDto.setName(character.getName());
                characterDto.setImage(character.getImage());

                characterResumeDtos.add(characterDto);
            }

            return characterResumeDtos;
        } catch (ExceptionSpring e) {
            throw e;
        } catch (Exception e) {
            throw new ExceptionSpring("Error in server");
        }
    }


    public CharacterDto getCharacter(String id) throws ExceptionSpring {
        try {
            Character character = characterRepository.findByIdAndDateOffIsNull(id);

            if (character == null) {
                throw new ExceptionSpring("Character not found", HttpStatus.NOT_FOUND);
            }

            return characterConverter.convertToDto(character, new CharacterDto());
        } catch (ExceptionSpring e) {
            throw e;
        } catch (Exception e) {
            throw new ExceptionSpring("Error in server");
        }
    }

    public Set<CharacterDto> getCharacterByName(String name) throws ExceptionSpring {

        try {
            Set<Character> characters = characterRepository.findByNameAndDateOffIsNull(name);

            Set<CharacterDto> characterDtos = new HashSet<>();

            if (characters.isEmpty()) {
                throw new ExceptionSpring("Character not found", HttpStatus.NOT_FOUND);
            }

            for (Character character : characters) {
                characterDtos.add(characterConverter.convertToDto(character, new CharacterDto()));
            }

            return characterDtos;
        } catch (ExceptionSpring e) {
            throw e;
        } catch (Exception e) {
            throw new ExceptionSpring("Error in server");
        }
    }

    public Set<CharacterDto> getCharactersByAge(int age) throws ExceptionSpring {

        try {
            Set<Character> characters = characterRepository.findByAgeAndDateOffIsNull(age);

            Set<CharacterDto> characterDtos = new HashSet<>();

            if (characters.isEmpty()) {
                throw new ExceptionSpring("Character not found", HttpStatus.NOT_FOUND);
            }

            for (Character character : characters) {
                characterDtos.add(characterConverter.convertToDto(character, new CharacterDto()));
            }

            return characterDtos;
        } catch (ExceptionSpring e) {
            throw e;
        } catch (Exception e) {
            throw new ExceptionSpring("Error in server");
        }
    }

    public Set<CharacterDto> getCharactersByMovie(String id) throws ExceptionSpring {

        try {
            Movie movie = movieRepository.findByIdAndDateOffIsNull(id);
            Set<CharacterDto> characters = new HashSet<>();


            if (movie == null) {
                throw new ExceptionSpring("Movie not found");
            }

            for (Character character : movie.getCharacters()) {
                characters.add(characterConverter.convertToDto(character, new CharacterDto()));
            }

            return characters;
        } catch (ExceptionSpring e) {
            throw e;
        } catch (Exception e) {
            throw new ExceptionSpring("Error in server");
        }
    }

    public void deleteCharacter(String id) throws ExceptionSpring {
        try {
            Character character = characterRepository.findByIdAndDateOffIsNull(id);

            if (character == null) {
                throw new ExceptionSpring("Character not found");
            }

            character.setDateOff(LocalDate.now());
            characterRepository.save(character);
        } catch (ExceptionSpring e) {
            throw e;
        } catch (Exception e) {
            throw new ExceptionSpring("Error in server");
        }
    }
}
