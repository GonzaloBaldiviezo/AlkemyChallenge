package com.alkemy.challenge.security.service;

import com.alkemy.challenge.security.entity.AppUser;
import com.alkemy.challenge.security.entity.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserServiceImpl implements UserDetailsService {

    @Autowired
    AppUserService appUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserService.getByUsername(username).get();
        return UserDetailsImpl.build(appUser);
    }
}
