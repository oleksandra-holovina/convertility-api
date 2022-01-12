package com.convertility.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

class UserProfileResponseTest {

    @Test
    void shouldDeserializeCorrectly() throws JsonProcessingException {
        String response = " {\"localizedLastName\":\"Holovina\",\"profilePicture\":{\"displayImage\":\"urn:li:digitalmediaAsset:C5603AQFC0txWWS5tkg\"},\"firstName\":{\"localized\":{\"ru_RU\":\"Lexie\"},\"preferredLocale\":{\"country\":\"RU\",\"language\":\"ru\"}},\"lastName\":{\"localized\":{\"ru_RU\":\"Holovina\"},\"preferredLocale\":{\"country\":\"RU\",\"language\":\"ru\"}},\"id\":\"f40vG2mfTg\",\"localizedFirstName\":\"Lexie\"}";

        ObjectMapper objectMapper = new ObjectMapper();

        UserProfileResponse userProfileResponse = objectMapper.readValue(response, UserProfileResponse.class);
        System.out.println(userProfileResponse);

        //todo: assertions
    }

}
