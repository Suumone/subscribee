package com.professional.subscribee.repository;

import com.professional.subscribee.model.UserSubscriptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSubscriptionsRepo extends JpaRepository<UserSubscriptions, Long> {
}
