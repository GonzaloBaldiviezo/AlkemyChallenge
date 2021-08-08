package com.alkemy.challenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CharacterDto {
    private String id;
    @NotNull
    private String name;
    @NotNull
    @Positive
    private int age;
    @Positive
    private double weight;
    @NotNull
    private String history;
    private String image;
    //@NotEmpty
    //@Size(min = 1)
    private Set<MovieDto> movies;

}
