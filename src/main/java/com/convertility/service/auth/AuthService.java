package com.convertility.service.auth;

import com.convertility.AuthResponseException;
import com.convertility.dao.UserDao;
import com.convertility.data.AuthResponse;
import com.convertility.data.UserContactResponse;
import com.convertility.data.UserProfileResponse;
import com.convertility.entity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UserDao userDao;
    private final LinkedInUserService userService;


    public AuthService(UserDao userDao, LinkedInUserService userService) {
        this.userService = userService;
        this.userDao = userDao;
    }

    public void authenticate(String code) {
        AuthResponse authResponse = userService.fetchAccessToken(code);
        String accessToken = authResponse.getAccessToken();

        userDao.findFirstByAccessToken(accessToken)
                .ifPresentOrElse(u -> {
                    LocalDateTime tokenExpiration = u.getTokenExpiration();
                    if (tokenExpiration.isBefore(LocalDateTime.now())) {
                        throw new AuthResponseException("Token expired");
                    }
                }, () -> saveNewUser(accessToken, authResponse.getExpiresIn()));
    }

    private void saveNewUser(String accessToken, long tokenExpiration) {
        UserProfileResponse userProfile = userService.fetchUserProfile(accessToken);
        UserContactResponse userContract = userService.fetchUserContact(accessToken);

        User.UserBuilder newUser = User.builder()
                .accessToken(accessToken)
                .tokenExpiration(LocalDateTime.now().plusSeconds(tokenExpiration));

        if (userProfile != null) {
            newUser.firstName(userProfile.getLocalizedFirstName())
                    .lastName(userProfile.getLocalizedLastName())
                    .pictureUrl(userProfile.getProfilePicture().getDisplayImage());
        }

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
    }
}
