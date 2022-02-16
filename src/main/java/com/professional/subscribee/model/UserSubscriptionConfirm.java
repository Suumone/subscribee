package com.professional.subscribee.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSubscriptionConfirm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cafe confirmingCafe;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserSubscriptions userSubscriptions;

    private OffsetDateTime startConfirmDate;
    private OffsetDateTime endConfirmDate;
}
