package com.professional.subscribee.graphql;

import com.professional.subscribee.jwt.JwtTokenProvider;
import com.professional.subscribee.model.User;
import com.professional.subscribee.repository.UserRepo;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class QueryResolver implements GraphQLQueryResolver {
    private final UserRepo userRepo;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public List<User> getUsers() {
        return userRepo.findAll();
    }

    public User getUser(Long id) {
        return userRepo.getById(id);
    }

    public String login(String phone, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(phone, password));
        User user = userRepo.findByPhone(phone);
        if (user == null) {
            throw new UsernameNotFoundException("User with phone: " + phone + " not found");
        }
        return jwtTokenProvider.createToken(phone, user.getRole());
    }

    public User getUserFromAccessToken(String token) {
        return userRepo.findByPhone(jwtTokenProvider.getPhoneFromJwt(token));
    }
}
