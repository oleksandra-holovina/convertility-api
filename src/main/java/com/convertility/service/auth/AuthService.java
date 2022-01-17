package com.convertility.service.auth;

import com.convertility.data.*;
import com.convertility.exception.AuthResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger LOG = LoggerFactory.getLogger(AuthService.class.getName());

    private final UserService userService;
    private final LinkedInUserService linkedInUserService;
    private final JwtService jwtService;

    public AuthService(UserService userService, LinkedInUserService linkedInUserService, JwtService jwtService) {
        this.linkedInUserService = linkedInUserService;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    public void setAuthInSecurityContext(String authorization) {
        jwtService.extractToken(authorization)
                .ifPresent(bearerToken -> {
                    String id = jwtService.getIdFromToken(bearerToken);

                    if (userService.userExists(id, bearerToken)) {
                        TokenBasedAuthentication authentication = new TokenBasedAuthentication(bearerToken);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                });
    }

    public JwtDetails createUserOrUpdateToken(String code) {
        AuthResponse authResponse = linkedInUserService.fetchAccessToken(code);
        String accessToken = authResponse.getAccessToken();

        UserProfileResponse userProfile = linkedInUserService.fetchUserProfile(accessToken);
        if (userProfile != null) {
            long expiresAt = System.currentTimeMillis() + authResponse.getExpiresIn() * 1000L;
            JwtDetails jwtDetails = jwtService.generateToken(userProfile.getId());

            UserContactResponse userContract = linkedInUserService.fetchUserContact(accessToken);
            userService.createUserOrUpdateAccessToken(jwtDetails, accessToken, expiresAt, userProfile, userContract);
            return jwtDetails;
        } else {
            LOG.error("No data from LinkedIn userProfile");
            throw new AuthResponseException();
        }
    }

    public void reAuthenticate(String refreshToken) {
        userService.
    }

}
