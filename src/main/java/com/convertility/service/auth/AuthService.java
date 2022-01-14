package com.convertility.service.auth;

import com.convertility.exception.AuthResponseException;
import com.convertility.dao.UserDao;
import com.convertility.data.AuthResponse;
import com.convertility.data.AuthenticatedUser;
import com.convertility.data.UserContactResponse;
import com.convertility.data.UserProfileResponse;
import com.convertility.entity.User;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger LOG = LoggerFactory.getLogger(AuthService.class.getName());

    private final UserDao userDao;
    private final LinkedInUserService userService;

    //todo: log correlation id + cache + one output for request

    public AuthService(UserDao userDao, LinkedInUserService userService) {
        this.userService = userService;
        this.userDao = userDao;
    }

    public AuthenticatedUser getUser(String accessToken) {
        return userDao.findFirstByAccessToken(accessToken)
                .map(userFromDb -> {
                            if (userFromDb.getTokenExpiration() < System.currentTimeMillis()) {
                                LOG.error("Token expired");
                                throw new AuthResponseException();
                            }
                            return getAuthenticatedUser(accessToken, userFromDb.getTokenExpiration(), userFromDb.getPictureUrl());
                        }
                )
                .orElseThrow(() -> {
                    LOG.error("Token not found");
                    return new AuthResponseException();
                });
    }

    public AuthenticatedUser createUserOrUpdateToken(String code) {
        AuthResponse authResponse = userService.fetchAccessToken(code);
        String accessToken = authResponse.getAccessToken();
        long tokenExpiration = System.currentTimeMillis() + authResponse.getExpiresIn() * 1000;

        UserProfileResponse userProfile = userService.fetchUserProfile(accessToken);
        if (userProfile != null) {
            return userDao.findById(userProfile.getId())
                    .map(userFromDb -> {
                        userFromDb.setAccessToken(accessToken);
                        userFromDb.setTokenExpiration(tokenExpiration);
                        userDao.save(userFromDb);
                        return getAuthenticatedUser(accessToken, tokenExpiration, userProfile.getProfilePicture().getDisplayImage());
                    }).orElseGet(() -> saveNewUser(accessToken, tokenExpiration, userProfile));
        } else {
            LOG.error("No data from LinkedIn userProfile");
            throw new AuthResponseException();
        }
    }

    private AuthenticatedUser saveNewUser(String accessToken, long tokenExpiration, @NonNull UserProfileResponse userProfile) {
        String pictureUrl = userProfile.getProfilePicture().getDisplayImage();
        User.UserBuilder newUser = User.builder()
                .accessToken(accessToken)
                .tokenExpiration(tokenExpiration)
                .firstName(userProfile.getLocalizedFirstName())
                .lastName(userProfile.getLocalizedLastName())
                .pictureUrl(pictureUrl);

        UserContactResponse userContract = userService.fetchUserContact(accessToken);
        if (userContract != null) {
            userContract.getElements().forEach(e -> {
                if (e.getHandleTilda() instanceof UserContactResponse.EmailHandle) {
                    newUser.email(((UserContactResponse.EmailHandle) e.getHandleTilda()).getEmailAddress());
                } else if (e.getHandleTilda() instanceof UserContactResponse.PhoneHandle) {
                    newUser.phoneNumber(((UserContactResponse.PhoneHandle) e.getHandleTilda()).getPhoneNumber());
                }
            });
        }

        userDao.save(newUser.build());
        return getAuthenticatedUser(accessToken, tokenExpiration, pictureUrl);
    }

    private AuthenticatedUser getAuthenticatedUser(String accessToken, long tokenExpiration, String pictureUrl) {
        return AuthenticatedUser.builder()
                .accessToken(accessToken)
                .expiresAt(tokenExpiration)
                .profilePicture(pictureUrl)
                .build();
    }
}
