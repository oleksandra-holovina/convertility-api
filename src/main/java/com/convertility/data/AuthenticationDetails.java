package com.convertility.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationDetails {
    private String bearerToken;
    private int expiresAt;
}
