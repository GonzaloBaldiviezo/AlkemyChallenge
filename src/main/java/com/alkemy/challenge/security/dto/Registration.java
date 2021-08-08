package com.alkemy.challenge.security.dto;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Data
public class Registration {
    @NotBlank
    private String username;
    @Email
    private String email;
    @NotBlank
    private String password;

    Set<String> roles = new HashSet<>();
}
