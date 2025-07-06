package com.louiskhanh.airbnb_clone_be.user.presentation;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.louiskhanh.airbnb_clone_be.user.application.UserService;
import com.louiskhanh.airbnb_clone_be.user.application.dto.ReadUserDTO;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthResource {
    private final UserService userService;
    private final ClientRegistration registration;
    
    // Cache to store the last sync time for each user
    private final Map<String, Instant> lastSyncTimeMap = new ConcurrentHashMap<>();
    // The minimum time between syncs in minutes (30 minutes to avoid rate limits)
    private static final long MIN_SYNC_INTERVAL_MINUTES = 30;

    public AuthResource(UserService userService, ClientRegistrationRepository registration){
        this.userService = userService;
        this.registration = registration.findByRegistrationId("okta");
    }

    @GetMapping("/get-authenticated-user")
    public ResponseEntity<ReadUserDTO> getAuthenticatedUser(@AuthenticationPrincipal OAuth2User user,
                                                            @RequestParam(required = false, defaultValue = "false") boolean forceResync){
        if(user == null){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            try {
                String userEmail = user.getAttribute("email");
                if (userEmail == null) {
                    userEmail = user.getAttribute("sub");
                }
                
                boolean shouldSync = forceResync || shouldSyncUser(userEmail);
                
                if (shouldSync) {
                    userService.syncWithIdp(user, forceResync);
                    // Update the last sync time
                    lastSyncTimeMap.put(userEmail, Instant.now());
                }
                
                ReadUserDTO connectedUser = userService.getAuthenticatedUserFromSecurityContext();
                return new ResponseEntity<>(connectedUser, HttpStatus.OK);
            } catch (Exception e) {
                // Log the exception
                System.err.println("Error in getAuthenticatedUser: " + e.getMessage());
                e.printStackTrace();
                // Return a user-friendly error
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
    
    private boolean shouldSyncUser(String userEmail) {
        if (userEmail == null) {
            return true;
        }
        
        Instant lastSyncTime = lastSyncTimeMap.get(userEmail);
        if (lastSyncTime == null) {
            return true;
        }
        
        Instant now = Instant.now();
        long minutesSinceLastSync = TimeUnit.SECONDS.toMinutes(now.getEpochSecond() - lastSyncTime.getEpochSecond());
        
        return minutesSinceLastSync >= MIN_SYNC_INTERVAL_MINUTES;
    }
    
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request){
        String issueUri = registration.getProviderDetails().getIssuerUri();
        String originUrl = request.getHeader(HttpHeaders.ORIGIN);
        Object[] params = {issueUri, registration.getClientId(), originUrl};
        String logoutUrl = MessageFormat.format("{0}v2/logout?client_id={1}&returnTo={2}",params);
        request.getSession().invalidate();
        return ResponseEntity.ok().body(Map.of("logoutUrl", logoutUrl));
    }
}
