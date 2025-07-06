package com.louiskhanh.airbnb_clone_be.infrastructure.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import com.louiskhanh.airbnb_clone_be.user.domain.Authority;
import com.louiskhanh.airbnb_clone_be.user.domain.User;
 
public class SecurityUtils {
    public static final String ROLE_TENANT = "ROLE_TENANT";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_LANDLORD = "ROLE_LANDLORD";
    public static final String CLAIMS_NAMESPACE = "https://www.louiskhanh.com/roles";    

    public static User mapOauth2AttributesToUser(Map<String, Object> attributes){
        User user = new User();
        String sub = String.valueOf(attributes.get("sub"));
        String username = null;
        
        if(attributes.get("preferred_username") != null ){
            username = ((String) attributes.get("preferred_username")).toLowerCase();
        }

        if(attributes.get("given_name") != null ){
            user.setFirstName(((String) attributes.get("given_name")));
        }else if(attributes.get("nickname")!= null){
            user.setFirstName(((String) attributes.get("nickname")));
        }
        if(attributes.get("family_name") != null ){
            user.setLastName(((String) attributes.get("family_name")));
        }
        if(attributes.get("email") != null ){
            user.setEmail(((String) attributes.get("email")));
        } else if(sub.contains("|") && (username != null && username.contains("@"))){
            user.setEmail(username);
        } else {
            user.setEmail(sub);
        }

        if(attributes.get("picture") != null ){
            user.setImageUrl((String) attributes.get("picture"));
        }

        Set<Authority> authorities = new HashSet<>();
        
        if(attributes.get(CLAIMS_NAMESPACE) != null){
            try {
                List<String> authoritiesRaw = (List<String>) attributes.get(CLAIMS_NAMESPACE);
                authorities = authoritiesRaw.stream()
                    .map(role -> {
                        Authority auth = new Authority();
                        // Ensure role starts with ROLE_ prefix
                        String normalizedRole = role.startsWith("ROLE_") ? role : "ROLE_" + role.toUpperCase();
                        auth.setName(normalizedRole);
                        return auth;
                    }).collect(Collectors.toSet());
            } catch (Exception e) {
                System.err.println("Error mapping authorities: " + e.getMessage());
            }
        }
        
        // Add default TENANT role if no roles are specified
        if (authorities.isEmpty()) {
            Authority defaultAuth = new Authority();
            defaultAuth.setName(ROLE_TENANT);
            authorities.add(defaultAuth);
        }
        
        user.setAuthorities(authorities);
        
        // Set a UUID for new users (required field)
        user.setPublicId(UUID.randomUUID());
        
        return user;
    }

    public static List<SimpleGrantedAuthority> extractAuthorityFromClaims(Map<String, Object> claims){
        return mapRolesToGrantedAuthorities(getRolesFromClaims(claims));
    }

    private static Collection<String> getRolesFromClaims(Map<String, Object> claims){
        if (claims == null || !claims.containsKey(CLAIMS_NAMESPACE)) {
            return List.of(); // Empty list if no claims or namespace
        }
        return (List<String>) claims.get(CLAIMS_NAMESPACE);
    }

    private static List<SimpleGrantedAuthority> mapRolesToGrantedAuthorities(Collection<String> roles){
        if (roles == null || roles.isEmpty()) {
            return List.of(); // Return empty list for null or empty roles
        }
        return roles.stream()
            .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role.toUpperCase())
            .map(SimpleGrantedAuthority::new)
            .toList();
    }

    public static boolean hasCurrentUserAnyOfAuthorities(String ...authorities){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (authentication != null && getAuthorities(authentication)
            .anyMatch(authority -> Arrays.asList(authorities).contains(authority)));
    }

    private static Stream<String> getAuthorities(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication
                instanceof JwtAuthenticationToken jwtAuthenticationToken ?
                extractAuthorityFromClaims(jwtAuthenticationToken.getToken().getClaims()) : authentication.getAuthorities();
        return authorities.stream().map(GrantedAuthority::getAuthority);
    }
}
