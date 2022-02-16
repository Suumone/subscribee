package com.professional.subscribee.graphql;


import com.professional.subscribee.model.Cafe;
import com.professional.subscribee.model.User;
import com.professional.subscribee.service.CafeService;
import com.professional.subscribee.service.UserService;
import com.professional.subscribee.service.UserSubscriptionsService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MutationResolver implements GraphQLMutationResolver {
    private final UserService userService;
    private final CafeService cafeService;
    private final UserSubscriptionsService userSubscriptionsService;

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

    public boolean subscribeUser(String accessToken, long subscriptionId){
        return userSubscriptionsService.subscribeUser(accessToken, subscriptionId);
    }
}
