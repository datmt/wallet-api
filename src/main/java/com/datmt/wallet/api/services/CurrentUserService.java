package com.datmt.wallet.api.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    public String getCurrentUserId() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated())
            return authentication.getName();

        throw new IllegalArgumentException("User not authenticated");
    }
}
