package com.alkemy.challenge.dto;

import com.alkemy.challenge.entity.Movie;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class GenreDto {

    private String id;
    @Column(unique = true)
    private String name;
    private String image;
    private LocalDate dateOff;
    @Valid
    @Size(min = 1)
    private Set<Movie> movies;

}
