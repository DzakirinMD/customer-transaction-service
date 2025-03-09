package net.dzakirin.customer_transaction_service.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final String LOCAL_ADMIN = "admin";
    private static final String  LOCAL_PASSWORD= "admin";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (LOCAL_ADMIN.equals(username)) {
            return User.withUsername(username)
                    .password(getPasswordEncoder().encode(LOCAL_PASSWORD))
                    .authorities(Collections.emptyList())
                    .build();
        }
        throw new UsernameNotFoundException("User not found");
    }

    private PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
