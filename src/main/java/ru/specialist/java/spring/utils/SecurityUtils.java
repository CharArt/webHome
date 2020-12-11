package ru.specialist.java.spring.utils;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;
import java.util.stream.Collectors;

public class SecurityUtils {

    public static final String ACCESS_DENIED = "Access Denied";

    public static UserDetails getCurrentUserDetails() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof UserDetails)) throw new AccessDeniedException(ACCESS_DENIED);
        return (UserDetails) principal;
    }

    public static boolean hasRole(String role) {
        return getCurrentUserDetails().getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet())
                .contains("ROLE_" + role);
    }

    public static void checkAuthority(String username) {
        if (!Objects.equals(username, getCurrentUserDetails().getUsername())) throw new AccessDeniedException(ACCESS_DENIED);
    }
}