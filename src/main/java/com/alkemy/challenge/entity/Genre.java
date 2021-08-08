package com.alkemy.challenge.entity;


import lombok.Data;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
public class Genre {

    @Id
    private String id;
    @Column(unique = true)
    private String name;
    private String image;
    private LocalDate dateOff;

    @OneToMany(mappedBy = "genre")
    private Set<Movie> movies;
}
