package ua.novaarchitecture.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.novaarchitecture.entity.AdminUser;
import ua.novaarchitecture.repository.AdminUserRepository;

import java.util.Collections;

/**
 * Custom UserDetailsService for loading admin users.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminUserRepository adminUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminUser adminUser = adminUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        if (!adminUser.getActive()) {
            throw new UsernameNotFoundException("User is inactive: " + username);
        }

        return new User(
                adminUser.getUsername(),
                adminUser.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + adminUser.getRole()))
        );
    }
}
