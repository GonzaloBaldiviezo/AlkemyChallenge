package com.alkemy.challenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class MovieDto {

    private String id;
    @NotNull
    private String title;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate creation;
    @NotNull
    @Positive
    private int rating;
    @NotNull
    private String image;
    //@NotNull
    private List<CharacterDto> characters;
    @NotNull
    private String genreId;
}
