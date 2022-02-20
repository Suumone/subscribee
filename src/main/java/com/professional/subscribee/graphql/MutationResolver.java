package com.professional.subscribee.graphql;


import com.professional.subscribee.jwt.JwtTokenProvider;
import com.professional.subscribee.model.AccessToken;
import com.professional.subscribee.model.Cafe;
import com.professional.subscribee.model.User;
import com.professional.subscribee.repository.UserRepo;
import com.professional.subscribee.service.CafeService;
import com.professional.subscribee.service.UserService;
import com.professional.subscribee.service.UserSubscriptionsService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class MutationResolver implements GraphQLMutationResolver {
    private final UserService userService;
    private final CafeService cafeService;
    private final UserSubscriptionsService userSubscriptionsService;
    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepo;
    private final JwtTokenProvider jwtTokenProvider;

    public User createUser(String phone, String password) {
        return userService.createUser(phone, password);
    }

    public boolean deleteUser(long id) {
        userService.deleteUser(id);
        return true;
    }

    public Cafe createCafe(String phone, String password, String description) {
        return cafeService.createCafe(phone, password, description);
    }

    public boolean subscribeUser(String accessToken, long subscriptionId) {
        return userSubscriptionsService.subscribeUser(accessToken, subscriptionId);
    }

    public AccessToken login(String phone, String password) {
        log.trace("INCOMING login:{}, {}", phone, password);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(phone, password));
        log.trace("authenticated");
        User user = userRepo.findByPhone(phone);
        if (user == null) {
            throw new UsernameNotFoundException("User with phone: " + phone + " not found");
        }
        log.trace("user found");
        return jwtTokenProvider.createToken(phone, user.getRole());
    }
}
