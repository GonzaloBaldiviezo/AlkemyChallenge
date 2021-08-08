package com.alkemy.challenge.repository;

import com.alkemy.challenge.dto.CharacterDto;
import com.alkemy.challenge.entity.Character;
import com.alkemy.challenge.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CharacterRepository extends JpaRepository<Character, String> {

    Set<Character> findByDateOffIsNull();

    Character findByIdAndDateOffIsNull(String id);

    Set<Character> findByNameAndDateOffIsNull(String name);

    Set<Character> findByAgeAndDateOffIsNull(int age);

    //Set<Character> findByIdMovieAndDateOffIsNull(Long idMovie);
}
