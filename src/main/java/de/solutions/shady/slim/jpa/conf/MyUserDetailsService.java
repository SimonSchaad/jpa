package de.solutions.shady.slim.jpa.conf;

import de.solutions.shady.slim.jpa.domain.User;
import de.solutions.shady.slim.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Component
//@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsernameIs((username));

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
