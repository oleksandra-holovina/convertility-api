package com.convertility.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfileResponse {
    private String id;
    private String localizedFirstName;
    private String localizedLastName;
    private ProfilePicture profilePicture;

    @Data
    public static class ProfilePicture {
        private String displayImage;
    }
}
