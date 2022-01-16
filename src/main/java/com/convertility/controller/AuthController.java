package com.convertility.controller;

import com.convertility.data.AuthenticationDetails;
import com.convertility.service.auth.AuthService;
import org.springframework.web.bind.annotation.GetMapping;
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
        AuthenticationDetails user = authService.createUserOrUpdateToken(code);
        setTokenCookie(user.getAccessToken(), user.getExpiresAt(), response);

        response.sendRedirect("http://localhost:3001/"); //todo: fix
    }

    @GetMapping("/users")
    public String getUser(@RequestParam String accessToken) {
        return authService.getProfileUrl(accessToken); //todo: add more data
    }

    private void setTokenCookie(String token, int expiresIn, HttpServletResponse response) {
        Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge(expiresIn);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
