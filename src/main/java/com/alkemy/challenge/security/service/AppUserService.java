package com.alkemy.challenge.security.service;

import com.alkemy.challenge.security.entity.AppUser;
import com.alkemy.challenge.security.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class AppUserService {

    @Autowired
    AppUserRepository appUserRepository;

    public Optional<AppUser> getByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    public boolean existsByUsername(String username) {
        return appUserRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return appUserRepository.existsByEmail(email);
    }

    public void saveAppUser(AppUser appUser) {
        appUserRepository.save(appUser);
    }
}
