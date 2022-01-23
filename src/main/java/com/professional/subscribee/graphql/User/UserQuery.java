package com.professional.subscribee.graphql.User;

import com.professional.subscribee.model.User;
import com.professional.subscribee.service.UserService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class UserQuery implements GraphQLQueryResolver {
    private final UserService userService;

    public List<User> getUsers() {
        return userService.findAllUsers();
    }

    public User getUser(Long id){
        return userService.getUser(id);
    }

}
