package com.convertility.controller;

import com.convertility.service.auth.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getProfileUrl() { //todo: maybe more data
        return userService.getProfileUrl("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmNDB2RzJtZlRnIiwiaWF0IjoxNjQyMzg2NzkwLCJleHAiOjE2NDIzODc2OTB9.0lONhxZRXZSjLiqZdpEz9Qdky_grfau-BrqOIF6NGZ7hjOPIMvhX_WsWLDyMkTPWod_P7hdfwasiboYMp28w7w");
    }
}
