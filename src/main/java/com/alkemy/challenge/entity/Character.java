package com.alkemy.challenge.entity;

import lombok.Data;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Table(name = "personaje")
public class Character {

    @Id
    private String id;
    private String name;
    private int age;
    private double weight;
    private String history;
    private String image;
    private LocalDate dateOff;

    @ManyToMany(mappedBy = "characters")
    private Set<Movie> movies;

}
