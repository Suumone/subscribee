package com.professional.subscribee.service;

import com.professional.subscribee.model.Subscription;
import com.professional.subscribee.model.User;
import com.professional.subscribee.model.UserSubscriptions;
import com.professional.subscribee.repository.SubscriptionRepo;
import com.professional.subscribee.repository.UserRepo;
import com.professional.subscribee.repository.UserSubscriptionsRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.Period;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@AllArgsConstructor
public class UserSubscriptionsService {
    private final UserRepo userRepo;
    private final SubscriptionRepo subscriptionRepo;
    private final UserSubscriptionsRepo userSubscriptionsRepo;

    @Transactional
    public boolean subscribeUser(String userPhone, long subscriptionId) {
        User user = userRepo.findByPhone(userPhone);
        Subscription subscription = subscriptionRepo.findById(subscriptionId).orElse(null);
        if (user == null || subscription == null)
            throw new RuntimeException("User with phone(" + userPhone + ") or subscription(" + subscriptionId + ") not found");

        userSubscriptionsRepo.save(UserSubscriptions.builder()
                .user(user)
                .subscription(subscription)
                .startDate(OffsetDateTime.now())
                .endDate(OffsetDateTime.now().plus(Period.ofDays(subscription.getPeriodDays())).truncatedTo(DAYS))
                .build()
        );

        return true;
    }
}
