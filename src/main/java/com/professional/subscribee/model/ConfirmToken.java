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
public class ConfirmToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder.Default
    @Column(updatable = false)
    private OffsetDateTime createdDateTime = OffsetDateTime.now();
}
