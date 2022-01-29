package com.professional.subscribee.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "usr")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Username should not be empty")
    @Column(unique = true)
    private String username;
    @NotBlank(message = "Password should not be empty")
    private String password;
    private String phone;
    private String email;
    private final OffsetDateTime createdDateTime = OffsetDateTime.now();
    @Column(columnDefinition = "boolean default false")
    private boolean confirmed = false;

    public User(String username, String password, String phone, String email) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
    }
}
