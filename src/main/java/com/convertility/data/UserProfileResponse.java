package com.convertility.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfileResponse {
    private String id;
    private String localizedFirstName;
    private String localizedLastName;
    private ProfilePicture profilePicture;

    public String getImage() {
        //todo: validate
        return this.getProfilePicture().getDisplayImage().getElements().get(0).getIdentifiers().get(0).getIdentifier();
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ProfilePicture {
        @JsonProperty("displayImage~")
        private DisplayImage displayImage;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class DisplayImage {
            private List<Elements> elements;

            @Data
            @JsonIgnoreProperties(ignoreUnknown = true)

            public static class Elements {
                private List<Identifiers> identifiers;

                @Data
                @JsonIgnoreProperties(ignoreUnknown = true)

                public static class Identifiers {
                    private String identifier;
                }
            }
        }
    }
}
