package com.convertility.controller;

import com.convertility.data.AuthenticatedUser;
import com.convertility.service.auth.AuthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/auth2")
    public AuthenticatedUser auth2(@RequestParam String code) {
        return authService.createUserOrUpdateToken(code);
    }

    @GetMapping("/users")
    public AuthenticatedUser getUser(@RequestParam String accessToken) {
        return authService.getUser(accessToken);
    }
}
