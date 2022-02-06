package com.professional.subscribee.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.OffsetDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cafe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name should not be empty")
    private String name;
    private String phone;
    private String email;
    @Column(length = 4000)
    private String description;
    @Column(columnDefinition = "boolean default false")
    private boolean deleted = false;
    @Column(updatable = false)
    private OffsetDateTime createdDateTime = OffsetDateTime.now();
}
