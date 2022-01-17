package com.convertility.data;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

@Getter
public class TokenBasedAuthentication extends AbstractAuthenticationToken {
    private String token;

    public TokenBasedAuthentication(String token) {
        super(AuthorityUtils.NO_AUTHORITIES);
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }
}
