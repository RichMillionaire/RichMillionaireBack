package com.richmillionaire.richmillionaire.utils;

import java.util.Set;
import java.util.stream.Collectors;

import com.richmillionaire.richmillionaire.models.Role;
import com.richmillionaire.richmillionaire.models.User;

public class SecurityUtils {

    public static User getCurrentUser() {
        // ici tu récupères ton utilisateur depuis le JWT ou la session
        // exemple si tu utilises Spring Security :
        // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // return (User) auth.getPrincipal();
        throw new RuntimeException("Implémenter la récupération du user courant depuis JWT !");
    }

    public static Set<String> getRoles(User user) {
        return user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }
}
