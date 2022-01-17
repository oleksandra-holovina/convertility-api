package com.convertility.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtDetails {
    private String bearerToken;
    private int bearerTokenExpiration;
    private String refreshToken;
    private int refreshTokenExpiration;
}
