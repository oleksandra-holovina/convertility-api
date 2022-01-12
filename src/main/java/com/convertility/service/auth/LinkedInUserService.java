package com.convertility.service.auth;

import com.convertility.AuthResponseException;
import com.convertility.data.AuthResponse;
import com.convertility.data.UserContactResponse;
import com.convertility.data.UserProfileResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class LinkedInUserService {

    private final RestTemplate restClient;

    @Value("${linkedin.redirect-uri}")
    private String redirectUri;

    @Value("${linkedin.client-key}")
    private String clientKey;

    @Value("${linkedin.client-secret}")
    private String clientSecret;

    public LinkedInUserService(RestTemplate restClient) {
        this.restClient = restClient;
    }

    public UserProfileResponse fetchUserProfile(String accessToken) {
        return fetchUserDetails(accessToken, "https://api.linkedin.com/v2/me", UserProfileResponse.class);
    }

    public UserContactResponse fetchUserContact(String accessToken) {
        return fetchUserDetails(accessToken, "https://api.linkedin.com/v2/clientAwareMemberHandles?q=members&projection=(elements*(type,handle~))", UserContactResponse.class);
    }

    private <R> R fetchUserDetails(String bearer, String url, Class<R> responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(bearer);
        HttpEntity<Object> request = new HttpEntity<>(headers);

        return restClient.exchange(
                url,
                HttpMethod.GET,
                request,
                responseType
        ).getBody();
    }

    public AuthResponse fetchAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("code", code);
        map.add("client_id", clientKey);
        map.add("client_secret", clientSecret);
        map.add("redirect_uri", redirectUri);

        HttpEntity<MultiValueMap<String, String>> accessTokenRequest = new HttpEntity<>(map, headers);
        AuthResponse response = restClient.postForObject(
                "https://www.linkedin.com/oauth/v2/accessToken",
                accessTokenRequest,
                AuthResponse.class);

        if (response == null) {
            throw new AuthResponseException("Response is empty");
        }
        return response;
    }
}
