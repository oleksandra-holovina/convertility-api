package com.convertility.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

class UserContactResponseTest {

    @Test
    void shouldDeserializeCorrectly() throws JsonProcessingException {
        String response = "{\"elements\":[{\"handle\":\"urn:li:emailAddress:1249243557\",\"type\":\"EMAIL\",\"handle~\":{\"emailAddress\":\"alexandra.l.golovina@gmail.com\"}}]}";
//        String response = "{\"elements\":[{\"handle\":\"urn:li:emailAddress:1249243557\",\"type\":\"PHONE\",\"handle~\":{\"phoneNumber\":\"111111\"}}]}";

        ObjectMapper objectMapper = new ObjectMapper();

        UserContactResponse userContactResponse = objectMapper.readValue(response, UserContactResponse.class);
        System.out.println(userContactResponse);

        //todo: assertions
    }
}
