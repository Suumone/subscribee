package com.professional.subscribee.graphql.User;


import com.professional.subscribee.model.User;
import com.professional.subscribee.service.UserService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMutation implements GraphQLMutationResolver {
    private final UserService userService;

    public User createUser(String username, String password, String phone, String email) {
        return userService.createUser(username, password, phone, email);
    }
}
