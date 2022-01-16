package com.convertility.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationDetails {
    private String accessToken;
    private int expiresAt;
}
