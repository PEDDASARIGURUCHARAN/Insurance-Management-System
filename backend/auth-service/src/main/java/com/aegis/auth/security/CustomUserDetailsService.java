package com.aegis.auth.security;

import com.aegis.auth.entity.User;
import com.aegis.auth.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String loginIdOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByLoginIdOrEmail(loginIdOrEmail, loginIdOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with login ID or email: " + loginIdOrEmail));
        return new CustomUserDetails(user);
    }
}
