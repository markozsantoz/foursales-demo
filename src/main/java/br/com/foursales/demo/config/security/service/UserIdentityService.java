package br.com.foursales.demo.config.security.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserIdentityService implements UserDetailsService {

    private static final String PLACEHOLDER = UUID.randomUUID().toString();
    private static final String USERNAME = "test@foursales.com";
    private static final String PASSWORD = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("123456");

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (USERNAME.equals(email))
            return new User(USERNAME, PASSWORD, List.of());
        return new User(PLACEHOLDER, PLACEHOLDER, List.of());
    }
}
