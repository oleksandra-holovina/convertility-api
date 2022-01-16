package com.convertility.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

class UserProfileResponseTest {

    @Test
    void shouldDeserializeCorrectly() throws JsonProcessingException {
//        String response = " {\"localizedLastName\":\"Holovina\",\"profilePicture\":{\"displayImage\":\"urn:li:digitalmediaAsset:C5603AQFC0txWWS5tkg\"},\"firstName\":{\"localized\":{\"ru_RU\":\"Lexie\"},\"preferredLocale\":{\"country\":\"RU\",\"language\":\"ru\"}},\"lastName\":{\"localized\":{\"ru_RU\":\"Holovina\"},\"preferredLocale\":{\"country\":\"RU\",\"language\":\"ru\"}},\"id\":\"f40vG2mfTg\",\"localizedFirstName\":\"Lexie\"}";

        String response = "{\n" +
                "   \"profilePicture\":{\n" +
                "      \"displayImage\":\"urn:li:digitalmediaAsset:C4D03AQGsitRwG8U8ZQ\",\n" +
                "      \"displayImage~\":{\n" +
                "         \"elements\":[\n" +
                "            {\n" +
                "               \"artifact\":\"urn:li:digitalmediaMediaArtifact:(urn:li:digitalmediaAsset:C4D03AQGsitRwG8U8ZQ,urn:li:digitalmediaMediaArtifactClass:profile-displayphoto-shrink_100_100)\",\n" +
                "               \"authorizationMethod\":\"PUBLIC\",\n" +
                "               \"data\":{\n" +
                "                  \"com.linkedin.digitalmedia.mediaartifact.StillImage\":{\n" +
                "                     \"storageSize\":{\n" +
                "                        \"width\":100,\n" +
                "                        \"height\":100\n" +
                "                     },\n" +
                "                     \"storageAspectRatio\":{\n" +
                "                        \"widthAspect\":1,\n" +
                "                        \"heightAspect\":1,\n" +
                "                        \"formatted\":\"1.00:1.00\"\n" +
                "                     },\n" +
                "                     \"mediaType\":\"image/jpeg\",\n" +
                "                     \"rawCodecSpec\":{\n" +
                "                        \"name\":\"jpeg\",\n" +
                "                        \"type\":\"image\"\n" +
                "                     },\n" +
                "                     \"displaySize\":{\n" +
                "                        \"uom\":\"PX\",\n" +
                "                        \"width\":100,\n" +
                "                        \"height\":100\n" +
                "                     },\n" +
                "                     \"displayAspectRatio\":{\n" +
                "                        \"widthAspect\":1,\n" +
                "                        \"heightAspect\":1,\n" +
                "                        \"formatted\":\"1.00:1.00\"\n" +
                "                     }\n" +
                "                  }\n" +
                "               },\n" +
                "               \"identifiers\":[\n" +
                "                  {\n" +
                "                     \"identifier\":\"https://media.licdn.com/dms/image/C4D03AQGsitRwG8U8ZQ/profile-displayphoto-shrink_100_100/0?e=1526940000&v=alpha&t=12345\",\n" +
                "                     \"file\":\"urn:li:digitalmediaFile:(urn:li:digitalmediaAsset:C4D03AQGsitRwG8U8ZQ,urn:li:digitalmediaMediaArtifactClass:profile-displayphoto-shrink_100_100,0)\",\n" +
                "                     \"index\":0,\n" +
                "                     \"mediaType\":\"image/jpeg\",\n" +
                "                     \"identifierExpiresInSeconds\":1526940000\n" +
                "                  }\n" +
                "               ]\n" +
                "            }\n" +
                "         ],\n" +
                "         \"paging\":{\n" +
                "            \"count\":10,\n" +
                "            \"start\":0,\n" +
                "            \"links\":[\n" +
                "\n" +
                "            ]\n" +
                "         }\n" +
                "      }\n" +
                "   },\n" +
                "   \"id\":\"yrZCpj2ZYQ\"\n" +
                "}";
        ObjectMapper objectMapper = new ObjectMapper();

        UserProfileResponse userProfileResponse = objectMapper.readValue(response, UserProfileResponse.class);
        System.out.println(userProfileResponse);

        //todo: assertions
    }

}
