package com.convertility.service.auth;

import com.convertility.dao.UserDao;
import com.convertility.data.JwtDetails;
import com.convertility.data.UserContactResponse;
import com.convertility.data.UserProfileResponse;
import com.convertility.entity.User;
import com.convertility.exception.AuthResponseException;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class.getName());

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public String getProfileUrl(String bearerToken) {
        return userDao.findFirstByBearerToken(bearerToken)
                .map(User::getPictureUrl)
                .orElseThrow(() -> {
                    LOG.error("Token not found");
                    return new AuthResponseException();
                });
    }

    public void createUserOrUpdateAccessToken(JwtDetails jwtDetails, String accessToken, long expiresAt, @NonNull UserProfileResponse userProfile, UserContactResponse userContract) {
        userDao.findById(userProfile.getId())
                .ifPresentOrElse(userFromDb -> {
                    userFromDb.setAccessToken(accessToken);
                    userFromDb.setTokenExpiration(expiresAt);
                    userDao.save(userFromDb);
                }, () -> saveNewUser(jwtDetails, accessToken, expiresAt, userProfile, userContract));

    }

    private void saveNewUser(JwtDetails jwtDetails, String accessToken, long expiresAt, @NonNull UserProfileResponse userProfile, UserContactResponse userContact) {
        User.UserBuilder newUser = User.builder()
                .id(userProfile.getId())
                .accessToken(accessToken)
                .tokenExpiration(expiresAt)
                .bearerToken(jwtDetails.getBearerToken())
                .refreshToken(jwtDetails.getRefreshToken())
                .firstName(userProfile.getLocalizedFirstName())
                .lastName(userProfile.getLocalizedLastName())
                .pictureUrl(userProfile.getImage());

        if (userContact != null) {
            userContact.getElements().forEach(e -> {
                if (e.getHandleTilda() instanceof UserContactResponse.EmailHandle) {
                    newUser.email(((UserContactResponse.EmailHandle) e.getHandleTilda()).getEmailAddress());
                } else if (e.getHandleTilda() instanceof UserContactResponse.PhoneHandle) {
                    newUser.phoneNumber(((UserContactResponse.PhoneHandle) e.getHandleTilda()).getPhoneNumber());
                }
            });
        }

        userDao.save(newUser.build());
    }

    public boolean userExists(String id, String bearerToken) {
        return userDao.existsUserByIdAndBearerToken(id, bearerToken);
    }
}
