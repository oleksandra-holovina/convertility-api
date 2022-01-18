package com.convertility.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(JobApplication.JobApplicationId.class)
public class JobApplication {
    @Id
    private long listingId;
    @Id
    private String userId;

    @Data
    public static class JobApplicationId implements Serializable {
        private long listingId;
        private String userId;
    }
}
