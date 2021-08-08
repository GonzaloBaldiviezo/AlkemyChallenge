package com.alkemy.challenge.repository;


import com.alkemy.challenge.entity.Genre;
import com.alkemy.challenge.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface GenreRepository extends JpaRepository<Genre, String> {

    Genre findByIdAndDateOffIsNull(String id);

    Set<Genre> findAllByDateOffIsNull();

}
