package com.alkemy.challenge.repository;

import com.alkemy.challenge.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {

    Movie findByTitleAndDateOffIsNull(String title);

    Movie findByIdAndDateOffIsNull(String id);

    boolean existsByIdAndDateOffIsNull(String id);

    Set<Movie> findAllByDateOffIsNull();

    Set<Movie> findByGenreAndDateOffIsNull(String id);

    Set<Movie> findAllByOrderByTitleAsc();

    Set<Movie> findAllByOrderByTitleDesc();
}
