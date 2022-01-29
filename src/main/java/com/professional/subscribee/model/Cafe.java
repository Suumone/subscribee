package com.professional.subscribee.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Cafe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name should not be empty")
    private String name;
    @NotBlank(message = "Password should not be empty")
    private String password;
    private String phone;
    private String email;
    @Column(length = 4000)
    private String description;
    @Column(columnDefinition = "boolean default false")
    private boolean deleted = false;
}
