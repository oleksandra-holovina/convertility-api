package com.convertility.service.auth;

import com.convertility.dao.UserDao;
import com.convertility.data.AuthResponse;
import com.convertility.data.AuthenticationDetails;
import com.convertility.data.UserContactResponse;
import com.convertility.data.UserProfileResponse;
import com.convertility.entity.User;
import com.convertility.exception.AuthResponseException;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger LOG = LoggerFactory.getLogger(AuthService.class.getName());

    private final UserDao userDao;
    private final LinkedInUserService userService;

    public AuthService(UserDao userDao, LinkedInUserService userService) {
        this.userService = userService;
        this.userDao = userDao;
    }

    public String getProfileUrl(String accessToken) {
        return userDao.findFirstByAccessToken(accessToken)
                .map(userFromDb -> {
                            if (userFromDb.getTokenExpiration() < System.currentTimeMillis()) {
                                LOG.error("Token expired");
                                throw new AuthResponseException();
                            }
                            return userFromDb.getPictureUrl();
                        }
                )
                .orElseThrow(() -> {
                    LOG.error("Token not found");
                    return new AuthResponseException();
                });
    }

    public AuthenticationDetails createUserOrUpdateToken(String code) {
        AuthResponse authResponse = userService.fetchAccessToken(code);
        String accessToken = authResponse.getAccessToken();
        long expiresAt = calculateExpiresAt(authResponse.getExpiresIn());

        UserProfileResponse userProfile = userService.fetchUserProfile(accessToken);
        if (userProfile != null) {
            return userDao.findById(userProfile.getId())
                    .map(userFromDb -> {
                        userFromDb.setAccessToken(accessToken);
                        userFromDb.setTokenExpiration(expiresAt);
                        userDao.save(userFromDb);
                        return getAuthenticatedUser(accessToken, authResponse.getExpiresIn(), userProfile.getImage());
                    }).orElseGet(() -> saveNewUser(accessToken, expiresAt, userProfile));
        } else {
            LOG.error("No data from LinkedIn userProfile");
            throw new AuthResponseException();
        }
    }

    private AuthenticationDetails saveNewUser(String accessToken, long expiresAt, @NonNull UserProfileResponse userProfile) {
        String pictureUrl = userProfile.getImage();
        User.UserBuilder newUser = User.builder()
                .id(userProfile.getId())
                .accessToken(accessToken)
                .tokenExpiration(expiresAt)
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
        return getAuthenticatedUser(accessToken, calculateExpiresIn(expiresAt), pictureUrl);
    }

    private long calculateExpiresAt(int expiresIn) {
        return System.currentTimeMillis() + expiresIn * 1000L;
    }


    private int calculateExpiresIn(long expiresAt) {
        return (int) ((expiresAt - System.currentTimeMillis()) / 1000);
    }

    private AuthenticationDetails getAuthenticatedUser(String accessToken, int tokenExpiration, String pictureUrl) {
        return AuthenticationDetails.builder()
                .accessToken(accessToken)
                .expiresAt(tokenExpiration)
                .build();
    }
}
