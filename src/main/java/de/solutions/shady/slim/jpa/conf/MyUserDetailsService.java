package de.solutions.shady.slim.jpa.conf;

import de.solutions.shady.slim.jpa.domain.User;
import de.solutions.shady.slim.jpa.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Slf4j
//@Component
//@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("loadUserByUserName: {}", username);

        Optional<User> user = userRepository.findByUsernameIs((username));
        log.debug("user optional from database: {}", user);

        if (user.isPresent()) {
            return org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder()
                    .username(user.get().getUsername())
                    .password(user.get().getPasswordEncoded()) //TODO: !!!
                    .roles("USER")
                    .build();
        } else {
            throw new UsernameNotFoundException("username " + username + " not found");
        }
    }
}
