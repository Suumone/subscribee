package com.professional.subscribee.jwt;

import com.professional.subscribee.model.User;
import com.professional.subscribee.repository.UserRepo;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Lazy
public class JwtUserDetailsService implements UserDetailsService {
    private final UserRepo userRepo;

    public JwtUserDetailsService(@Lazy UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadUserByPhone(username);
    }

    public UserDetails loadUserByPhone(String phone) throws UsernameNotFoundException {
        User user = userRepo.findByPhone(phone);

        if (user == null) {
            throw new UsernameNotFoundException("Username with phone: " + phone + "not found");
        }

        return JwtUserFactory.create(user);
    }
}
