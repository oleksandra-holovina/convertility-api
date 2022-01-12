package com.convertility.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;

import java.io.IOException;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserContactResponse {
    private List<ResponseElements> elements;

    @Data
    public static class ResponseElements {
        private String handle;
        private ContactType type;
        @JsonProperty("handle~")
        @JsonDeserialize(using = HandleDeserializer.class)
        private Handle handleTilda;
    }

    public enum ContactType {
        @JsonProperty("EMAIL") EMAIL,
        @JsonProperty("PHONE") PHONE
    }

    public interface Handle {}

    @Data
    @Builder
    public static class EmailHandle implements Handle {
        private String emailAddress;
    }

    @Data
    @Builder
    public static class PhoneHandle implements Handle {
        private String phoneNumber;
    }

    private static class HandleDeserializer extends JsonDeserializer<Handle> {

        @Override
        public Handle deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonNode tree = p.readValueAsTree();
            if (tree.has("emailAddress")) {
                return EmailHandle.builder()
                        .emailAddress(tree.findValue("emailAddress").asText())
                        .build();
            }
            if (tree.has("phoneNumber")) {
                return PhoneHandle.builder()
                        .phoneNumber(tree.findValue("phoneNumber").asText())
                        .build();
            }
            return null;
        }
    }
}
