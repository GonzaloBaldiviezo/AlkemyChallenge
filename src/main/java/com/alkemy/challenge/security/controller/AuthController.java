package com.alkemy.challenge.security.controller;

import com.alkemy.challenge.security.dto.JwtDto;
import com.alkemy.challenge.security.dto.Login;
import com.alkemy.challenge.security.dto.Registration;
import com.alkemy.challenge.security.entity.AppUser;
import com.alkemy.challenge.security.entity.Role;
import com.alkemy.challenge.security.enums.ERole;
import com.alkemy.challenge.security.jwt.JwtProvider;
import com.alkemy.challenge.security.service.AppUserService;
import com.alkemy.challenge.security.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody Registration registration, BindingResult bindingResult) {
        Map<String, Object> response = new HashMap<>();

        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
            }

            response.put("errors", errors);
            return new ResponseEntity(response, BAD_REQUEST);


        }
        if (appUserService.existsByUsername(registration.getUsername())) {
            return new ResponseEntity("Error: Username is already taken!", BAD_REQUEST);
        }

        if (appUserService.existsByEmail(registration.getEmail())) {
            return new ResponseEntity("Error: Email is already in use!", BAD_REQUEST);
        }

        AppUser appUser = new AppUser(registration.getUsername(), registration.getEmail(), passwordEncoder.encode(registration.getPassword()));

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getByRole(ERole.ROLE_USER).get());

        registration.getRoles().forEach(rol ->{
            if (rol.equalsIgnoreCase("ADMIN")) {
                roles.add(roleService.getByRole(ERole.ROLE_ADMIN).get());
            }
        });
//        if (registration.getRoles().contains("ADMIN")) {
//            roles.add(roleService.getByRole(ERole.ROLE_ADMIN).get());
//        }
        appUser.setRoles(roles);
        appUserService.saveAppUser(appUser);
        return new ResponseEntity("Resgistered", CREATED );
    }
    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody Login login, BindingResult bindingResult) {
        Map<String, Object> response = new HashMap<>();

        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
            }

            response.put("errors", errors);
            return new ResponseEntity(response, BAD_REQUEST);
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        return new ResponseEntity(jwtDto, HttpStatus.OK);
    }
}
