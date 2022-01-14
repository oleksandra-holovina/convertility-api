package com.convertility.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticatedUser {
    private String accessToken;
    private long expiresAt;
    private String profilePicture;
}
