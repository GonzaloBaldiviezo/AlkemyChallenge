package com.alkemy.challenge.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
public class Movie {

    @Id
    private String id;
    private String title;
    private LocalDate creation;
    private int rating;
    private String image;
    private LocalDate dateOff;

    @ManyToMany
    private Set<Character> characters;

    @ManyToOne
    private Genre genre;
}
