package com.professional.subscribee.jwt;

import com.professional.subscribee.model.User;

public final class JwtUserFactory {
    public JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getPhone(),
                user.getPassword(),
                user.getAuthorities()
        );
    }
}
