package com.professional.subscribee.jwt;

import com.professional.subscribee.model.User;
import com.professional.subscribee.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Lazy
public class JwtUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public JwtUserDetailsService(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadUserByPhone(username);
    }

    public UserDetails loadUserByPhone(String phone) throws UsernameNotFoundException {
        User user = userService.findByPhone(phone);

        if (user == null) {
            throw new UsernameNotFoundException("Username with phone: " + phone + "not found");
        }

        return JwtUserFactory.create(user);
    }
}
