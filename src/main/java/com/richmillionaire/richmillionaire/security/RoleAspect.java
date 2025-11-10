package com.richmillionaire.richmillionaire.security;

import java.util.Set;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.richmillionaire.richmillionaire.models.User;

import jakarta.servlet.http.HttpServletRequest;

@Component
@Aspect
public class RoleAspect {

    @Around("@annotation(hasRole)")
    public Object checkRole(ProceedingJoinPoint joinPoint, HasRole hasRole) throws Throwable {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            throw new RuntimeException("Impossible de récupérer la requête HTTP");
        }

        HttpServletRequest request = attrs.getRequest();
        User user = (User) request.getAttribute("user"); 
        if (user == null) {
            throw new RuntimeException("Utilisateur non authentifié");
        }

        Set<String> roles = user.getRoles().stream()
                .map(r -> r.getName())
                .collect(java.util.stream.Collectors.toSet());

        String requiredRole = hasRole.value();
        if (!roles.contains(requiredRole)) {
            throw new RuntimeException("Désolé, il faut être " + requiredRole + " pour faire cette action ❌");
        }

        return joinPoint.proceed(); 
    }
}
