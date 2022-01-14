package com.convertility.service.auth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {

    // case 1 - user is new
    // case 2 - user exists but token expired, need to create new accessToken + exp date
    // case 3 - token is fine, return
    // case 4 - no token in db (fake token)

    // todo: integration test
}
