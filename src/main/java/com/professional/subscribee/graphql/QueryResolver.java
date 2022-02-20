package com.professional.subscribee.graphql;

import com.professional.subscribee.jwt.JwtTokenProvider;
import com.professional.subscribee.model.User;
import com.professional.subscribee.repository.UserRepo;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class QueryResolver implements GraphQLQueryResolver {
    private final UserRepo userRepo;
    private final JwtTokenProvider jwtTokenProvider;

    public List<User> getUsers() {
        return userRepo.findAll();
    }

    public User getUser(Long id) {
        return userRepo.getById(id);
    }

    public User getUserFromAccessToken(String token) {
        return userRepo.findByPhone(jwtTokenProvider.getPhoneFromJwt(token));
    }
}
