package com.professional.subscribee.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSubscriptions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Subscription subscription;
    private int cupsQty;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    @Builder.Default
    @Column(columnDefinition = "boolean default false")
    private boolean confirmed = false;
}
