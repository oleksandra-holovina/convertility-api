package com.convertility.controller;

import com.convertility.data.JwtDetails;
import com.convertility.service.auth.AuthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/auth2")
    public void auth2(@RequestParam String code, HttpServletResponse response) throws IOException {
        JwtDetails jwtDetails = authService.createUserOrUpdateToken(code);
        setTokenCookie("bearerToken", jwtDetails.getBearerToken(), jwtDetails.getBearerTokenExpiration(), response);
        setTokenCookie("refreshToken", jwtDetails.getRefreshToken(), jwtDetails.getRefreshTokenExpiration(), response);

        response.sendRedirect("http://localhost:3001/"); //todo: fix
    }

    @PostMapping("/auth")
    public void reAuthenticate(@RequestParam String refreshToken) {
        authService
    }

    private void setTokenCookie(String tokenName, String token, int expiresIn, HttpServletResponse response) {
        Cookie cookie = new Cookie(tokenName, token);
        cookie.setMaxAge(expiresIn);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
