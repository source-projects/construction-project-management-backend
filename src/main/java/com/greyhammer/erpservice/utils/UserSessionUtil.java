package com.greyhammer.erpservice.utils;

import net.minidev.json.JSONArray;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashSet;
import java.util.Set;

public class UserSessionUtil {
    public static String getCurrentUsername() {
        return getCurrentAuthentication().getName();
    }

    public static Set<String> getCurrentUserRoles() {
        JwtAuthentication authentication = getCurrentAuthentication();
        JSONArray roleClaims = (JSONArray) authentication.getJwtClaimsSet().getClaim("cognito:groups");
        Set<String> roles = new HashSet<>();
        for (Object role: roleClaims.toArray()) {
            roles.add(role.toString());
        }
        return roles;
    }

    private static JwtAuthentication getCurrentAuthentication() {
        return  (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}
