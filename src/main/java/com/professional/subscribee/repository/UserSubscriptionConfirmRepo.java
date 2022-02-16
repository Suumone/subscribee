package com.professional.subscribee.repository;

import com.professional.subscribee.model.UserSubscriptionConfirm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSubscriptionConfirmRepo extends JpaRepository<UserSubscriptionConfirm, Long> {
}
